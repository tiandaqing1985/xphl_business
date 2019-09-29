package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.IGatherService;
import com.ruoyi.system.service.IYwConsumptionService;
import com.ruoyi.system.service.IYwTaskService;
import com.ruoyi.system.service.TotalGatherService;
import com.ruoyi.web.controller.tool.GatherExcelUtil;
import com.ruoyi.web.controller.tool.QuarterUtil;
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
    private IGatherService iGatherService;
    @Autowired
    private TotalGatherService totalGatherService;
    @Autowired
    private IYwConsumptionService ywConsumptionService;
    @Autowired
    private IYwTaskService ywTaskService;

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
        if (gather.getQuarter() == null || gather.getQuarter().equals("")) {
            gather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<Gather> list = iGatherService.selectGatherList(gather);
        List<Gather> gatherList = iGatherService.exportList(list);
        return getDataTable(gatherList);
    }

    /**
     * 导出消耗汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gather gather) {
        if (gather.getQuarter() == null || gather.getQuarter().equals("")) {
            gather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<Gather> gathers = iGatherService.selectGatherList(gather);
        List<Gather> gatherList = iGatherService.exportList(gathers);
        GatherExcelUtil<Gather> util = new GatherExcelUtil<Gather>(Gather.class);
        return util.exportGatherExcel(gatherList, "个人消耗汇总");
    }

    /**
     * 查询部门整体汇总
     */
    @PostMapping("/exportTotalConsume")
    @ResponseBody
    public AjaxResult exportTotalConsume(YwTotalConsumGather ywTotalConsumGather) {
        startPage();
        //默认查询当前季度的数据
        if (ywTotalConsumGather.getQuarter() == null || ywTotalConsumGather.getQuarter().equals("")) {
            ywTotalConsumGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwTotalConsumGather> list = totalGatherService.selectTotalConsumGather(ywTotalConsumGather);
        GatherExcelUtil<YwTotalConsumGather> util = new GatherExcelUtil<YwTotalConsumGather>(YwTotalConsumGather.class);
        return util.exportGatherExcel(list, "华东华北消耗整体完成率");
    }

    /**
     * 查询毛利部门整体汇总
     */
    @PostMapping("/listTotalConsume")
    @ResponseBody
    public TableDataInfo listTotalConsume(YwTotalConsumGather ywTotalConsumGather) {
        startPage();
        //默认查询当前季度的数据
        if (ywTotalConsumGather.getQuarter() == null || ywTotalConsumGather.getQuarter().equals("")) {
            ywTotalConsumGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwTotalConsumGather> list = totalGatherService.selectTotalConsumGather(ywTotalConsumGather);
        return getDataTable(list);
    }

    /**
     * 导出消耗排名表
     */
    @PostMapping("/exportRankConsumption")
    @ResponseBody
    public AjaxResult exportRankConsumption(YwGatherConsumption ywTotalGather) {
        if (ywTotalGather.getQuarter() == null || ywTotalGather.getQuarter().equals("")) {
            ywTotalGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
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
        if (ywGatherConsumption.getQuarter() == null || ywGatherConsumption.getQuarter().equals("")) {
            ywGatherConsumption.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
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
        String message = ywConsumptionService.importYwConsumption(ywConsumptions, false, operName);
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

    /**
     * 导入
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "任务", businessType = BusinessType.IMPORT)
    @PostMapping("/importTaskData")
    @ResponseBody
    public AjaxResult importTaskData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<YwTask> util = new ExcelUtil<YwTask>(YwTask.class);
        List<YwTask> ywTasks = util.importExcel(file.getInputStream(),0,1);
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = ywTaskService.importYwTask(ywTasks, true, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导入任务模板
     * @return
     */
    @GetMapping("/importTaskTemplate")
    @ResponseBody
    public AjaxResult importTaskTemplate()
    {
        ExcelUtil<YwTask> util = new ExcelUtil<YwTask>(YwTask.class);
        return util.importTemplateExcel("任务");
    }
}
