package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import com.ruoyi.system.domain.YwTotalGather;
import com.ruoyi.system.service.TotalGatherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 整体情况汇总
 */
@Controller
@RequestMapping("/system/totalGather")
public class YwTotalGatherController extends BaseController
{

    private String prefix = "system/totalGather";

    @Autowired
    private TotalGatherService totalGatherService;

    @RequiresPermissions("system:gather:view")
    @GetMapping()
    public String totalGather()
    {
        return prefix + "/totalGather";
    }

    /**
     * 查询部门毛利消耗汇总情况
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo selectTotalGather(YwTotalGather ywTotalGather)
    {
        startPage();
        List<YwTotalGather> list = totalGatherService.selectTotalGather(ywTotalGather);
        return getDataTable(list);
    }

    /**
     * 查询毛利排名
     */
    @PostMapping("/rankGrossMarginList")
    @ResponseBody
    public TableDataInfo rankGrossMarginList()
    {
        startPage();
        List<YwGatherGrossMargin> list = totalGatherService.selectRankGrossMarginList();
        return getDataTable(list);
    }

    /**
     * 查询消耗排名
     */
    @PostMapping("/rankConsumptionlist")
    @ResponseBody
    public TableDataInfo rankConsumptionlist()
    {
        startPage();
        List<YwGatherConsumption> list = totalGatherService.selectRankConsumptionlist();
        return getDataTable(list);
    }


    /**
     * 导出消耗毛利汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwTotalGather ywTotalGather)
    {
        List<YwTotalGather> list = totalGatherService.selectTotalGather(ywTotalGather);
        ExcelUtil<YwTotalGather> util = new ExcelUtil<YwTotalGather>(YwTotalGather.class);
        return util.exportExcel(list, "区域消耗毛利汇总");
    }


    /**
     * 导出消耗排名表
     */
    @PostMapping("/exportRankConsumption")
    @ResponseBody
    public AjaxResult exportRankConsumption(YwTotalGather ywTotalGather)
    {
        List<YwGatherConsumption> list = totalGatherService.selectRankConsumptionlist();
        ExcelUtil<YwGatherConsumption> util = new ExcelUtil<YwGatherConsumption>(YwGatherConsumption.class);
        return util.exportExcel(list, "消耗排名");
    }


    /**
     * 导出毛利排名表
     */
    @PostMapping("/exportRankGrossMargin")
    @ResponseBody
    public AjaxResult exportRankGrossMargin(YwTotalGather ywTotalGather)
    {
        List<YwGatherGrossMargin> list = totalGatherService.selectRankGrossMarginList();
        ExcelUtil<YwGatherGrossMargin> util = new ExcelUtil<YwGatherGrossMargin>(YwGatherGrossMargin.class);
        return util.exportExcel(list, "毛利排名");
    }

}
