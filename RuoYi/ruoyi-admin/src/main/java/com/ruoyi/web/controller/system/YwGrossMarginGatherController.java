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
import com.ruoyi.system.service.IYwGrossMarginsService;
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
    @Autowired
    private IYwTaskService ywTaskService;

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
        if (gather.getQuarter() == null || gather.getQuarter().equals("")) {
            gather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwGrossMarginGather> list = gatherService.selectGrossMarginGatherList(gather);
        return getDataTable(list);
    }

    /**
     * 导出毛利汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwGrossMarginGather gather) {
        if (gather.getQuarter() == null || gather.getQuarter().equals("")) {
            gather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwGrossMarginGather> gathers = gatherService.selectGrossMarginGatherList(gather);
        GatherExcelUtil<YwGrossMarginGather> util = new GatherExcelUtil<YwGrossMarginGather>(YwGrossMarginGather.class);
        return util.exportGatherExcel(gathers, "个人毛利汇总");
    }

    /**
     * 查询毛利排名
     */
    @PostMapping("/rankGrossMarginList")
    @ResponseBody
    public TableDataInfo rankGrossMarginList(YwRankGrossMargin ywRankGrossMargin) {
        startPage();
        if (ywRankGrossMargin.getQuarter() == null || ywRankGrossMargin.getQuarter().equals("")) {
            ywRankGrossMargin.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwRankGrossMargin> list = totalGatherService.selectRankGrossMarginList(ywRankGrossMargin);
        return getDataTable(list);
    }

    /**
     * 查询毛利部门整体汇总
     */
    @PostMapping("/exportTotalGrossMargin")
    @ResponseBody
    public AjaxResult exportTotalGrossMargin(YwTotalGrossGather ywTotalGrossGather) {
        startPage();
        //默认查询当前季度的数据
        if (ywTotalGrossGather.getQuarter() == null || ywTotalGrossGather.getQuarter().equals("")) {
            ywTotalGrossGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwTotalGrossGather> list = totalGatherService.selectTotalGrossGather(ywTotalGrossGather);
        GatherExcelUtil<YwTotalGrossGather> util = new GatherExcelUtil<>(YwTotalGrossGather.class);
        return util.exportGatherExcel(list,"华东华北毛利整体完成率");
    }

    /**
     * 查询毛利部门整体汇总
     */
    @PostMapping("/listTotalGrossMargin")
    @ResponseBody
    public TableDataInfo listTotalGrossMargin(YwTotalGrossGather ywTotalGrossGather) {
        startPage();
        //默认查询当前季度的数据
        if (ywTotalGrossGather.getQuarter() == null || ywTotalGrossGather.getQuarter().equals("")) {
            ywTotalGrossGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
        List<YwTotalGrossGather> list = totalGatherService.selectTotalGrossGather(ywTotalGrossGather);
        return getDataTable(list);
    }

    /**
     * 导出毛利排名表
     */
    @PostMapping("/exportRankGrossMargin")
    @ResponseBody
    public AjaxResult exportRankGrossMargin(YwRankGrossMargin ywTotalGather) {
        if (ywTotalGather.getQuarter() == null || ywTotalGather.getQuarter().equals("")) {
            ywTotalGather.setQuarter(QuarterUtil.getQuarterByDate(DateUtils.getNowDate()));
        }
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
        String message = ywGrossMarginsService.importYwGrossMargins(grossMarginList, false, operName);
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
