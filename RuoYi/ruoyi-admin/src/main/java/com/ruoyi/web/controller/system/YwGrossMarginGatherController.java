package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.domain.YwGrossMarginGather;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwGrossMargins;
import com.ruoyi.system.service.IGatherService;
import com.ruoyi.system.service.IYwGrossMarginsService;
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
 * 毛利相关汇总
 */
@Controller
@RequestMapping("/system/ywGrossMarginGather")
public class YwGrossMarginGatherController extends BaseController {

    private static String prefix = "/system/ywGrossMarginGather";

    @Autowired
    private IGatherService gatherService;
    @Autowired
    private TotalGatherService totalGatherService;
    @Autowired
    private IYwGrossMarginsService ywGrossMarginsService;

    @RequiresPermissions("system:ywBusiness:view")
    @GetMapping()
    private String ywGrossMarginGather() {

        return prefix + "/ywGrossMarginGather";

    }

    /**
     * 查询毛利汇总列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YwGrossMarginGather gather) {
        startPage();
        List<YwGrossMarginGather> list = gatherService.selectGrossMarginGatherList(gather);
        return getDataTable(list);
    }

    /**
     * 导出毛利汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gather gather) {
        List<Gather> gathers = gatherService.selectGatherList(gather);
        List<Gather> gatherList = gatherService.exportList(gathers);
        GatherExcelUtil<Gather> util = new GatherExcelUtil<Gather>(Gather.class);
        return util.exportGatherExcel(gatherList, "个人毛利汇总");
    }

    /**
     * 查询毛利排名
     */
    @PostMapping("/rankGrossMarginList")
    @ResponseBody
    public TableDataInfo rankGrossMarginList(YwRankGrossMargin ywRankGrossMargin) {
        startPage();
        List<YwRankGrossMargin> list = totalGatherService.selectRankGrossMarginList(ywRankGrossMargin);
        return getDataTable(list);
    }

    /**
     * 导出毛利排名表
     */
    @PostMapping("/exportRankGrossMargin")
    @ResponseBody
    public AjaxResult exportRankGrossMargin(YwRankGrossMargin ywTotalGather) {
        List<YwRankGrossMargin> list = totalGatherService.selectRankGrossMarginList(ywTotalGather);
        ExcelUtil<YwRankGrossMargin> util = new ExcelUtil<YwRankGrossMargin>(YwRankGrossMargin.class);
        return util.exportExcel(list, "毛利排名");
    }

    /**
     * 导入
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "毛利情况", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<YwGrossMargins> util = new ExcelUtil<YwGrossMargins>(YwGrossMargins.class);
        List<YwGrossMargins> grossMarginList = util.importExcel(file.getInputStream(), 0, 1);
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = ywGrossMarginsService.importYwGrossMargins(grossMarginList, updateSupport, operName);
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
        ExcelUtil<YwGrossMargins> util = new ExcelUtil<YwGrossMargins>(YwGrossMargins.class);
        return util.importTemplateExcel("毛利情况");
    }

    //根据考核期间获取季度
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
