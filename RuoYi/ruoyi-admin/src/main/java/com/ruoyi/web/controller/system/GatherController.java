package com.ruoyi.web.controller.system;

import java.math.BigDecimal;
import java.util.*;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.service.IGatherService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 消耗毛利汇总 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/system/gather")
public class GatherController extends BaseController {
    private String prefix = "system/gather";

    @Autowired
    private IGatherService gatherService;

    @RequiresPermissions("system:gather:view")
    @GetMapping()
    public String gather() {
        return prefix + "/gather";
    }

    /**
     * 查询消耗毛利汇总列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Gather gather) {
        startPage();
        List<Gather> list = gatherService.selectGatherList(gather);
        return getDataTable(list);
    }

    /**
     * 导出消耗毛利汇总列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Gather gather) {
        List<Gather> gathers = gatherService.selectGatherList(gather);
        List<Gather> gatherList = gatherService.exportList(gathers);
        ExcelUtil<Gather> util = new ExcelUtil<Gather>(Gather.class);
        return util.exportExcel(gatherList, "个人消耗毛利汇总");
    }

}
