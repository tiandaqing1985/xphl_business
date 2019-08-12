package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.domain.YwConsumption;
import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.service.IGatherService;
import com.ruoyi.system.service.IYwConsumptionService;
import com.ruoyi.system.service.TotalGatherService;
import com.ruoyi.web.controller.tool.GatherExcelUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 消耗相关汇总
 */
@Controller
@RequestMapping("/system/ywConsumeGather")
public class YwConsumeGatherController extends BaseController {

    private static String prefix = "system/ywConsumeGather";

    @Autowired
    private IGatherService gatherService;
    @Autowired
    private TotalGatherService totalGatherService;
    @Autowired
    private IYwConsumptionService ywConsumptionService;

    @RequiresPermissions("system:ywBusiness:view")
    @GetMapping()
    private String ywConsumeGather() {

        return prefix + "/ywConsumeGather";

    }

    /**
     * 查询消耗汇总列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Gather gather) {
        startPage();
        List<Gather> list = gatherService.selectGatherList(gather);
        List<Gather> gatherList = gatherService.exportList(list);
        if (gatherList.size() > 0) {
            gatherList.remove(gatherList.size() - 1);
        }
        return getDataTable(gatherList);
    }

    /**
     * 导出消耗毛利汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gather gather) {
        List<Gather> gathers = gatherService.selectGatherList(gather);
        List<Gather> gatherList = gatherService.exportList(gathers);
        GatherExcelUtil<Gather> util = new GatherExcelUtil<Gather>(Gather.class);
        return util.exportGatherExcel(gatherList, "个人消耗汇总");
    }

    /**
     * 导出消耗排名表
     */
    @PostMapping("/exportRankConsumption")
    @ResponseBody
    public AjaxResult exportRankConsumption(YwGatherConsumption ywTotalGather) {
        List<YwGatherConsumption> list = totalGatherService.selectRankConsumptionlist(ywTotalGather);
        ExcelUtil<YwGatherConsumption> util = new ExcelUtil<YwGatherConsumption>(YwGatherConsumption.class);
        return util.exportExcel(list, "消耗排名");
    }

    /**
     * 查询消耗排名
     */
    @PostMapping("/rankConsumptionlist")
    @ResponseBody
    public TableDataInfo rankConsumptionlist(YwGatherConsumption ywGatherConsumption) {
        startPage();
        List<YwGatherConsumption> list = totalGatherService.selectRankConsumptionlist(ywGatherConsumption);
        return getDataTable(list);
    }

    /**
     * 导入
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "消耗情况", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<YwConsumption> util = new ExcelUtil<YwConsumption>(YwConsumption.class);
        List<YwConsumption> ywConsumptions = util.importExcel(file.getInputStream(), 0, 1);
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = ywConsumptionService.importYwConsumption(ywConsumptions, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导入模板
     *
     * @return
     */
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<YwConsumption> util = new ExcelUtil<YwConsumption>(YwConsumption.class);
        return util.importTemplateExcel("消耗情况");
    }

    private String getQuarter(String term) {
        String quarter = null;
        if (term == null || term.equals("")) {
            return null;
        }
        String[] terms = term.split("-");
        String[] dateStr = terms[0].split("\\.");
        if (dateStr[1].equals("1")) {
            //第一季度
            quarter = "Q1";
        } else if (dateStr[1].equals("4")) {
            //第二季度
            quarter = "Q2";
        } else if (dateStr[1].equals("7")) {
            //第三季度
            quarter = "Q3";
        } else if (dateStr[1].equals("10")) {
            //第四度
            quarter = "Q4";
        }
        quarter = dateStr[0].substring(2) + "年" + quarter;
        return quarter;
    }
}
