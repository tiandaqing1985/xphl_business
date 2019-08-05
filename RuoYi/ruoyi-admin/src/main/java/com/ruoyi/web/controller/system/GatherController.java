package com.ruoyi.web.controller.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        List<Gather> list = gatherService.selectGatherList(gather);
        //导出的list数据
        List<Gather> exportlist = new ArrayList<>(list.size());
        //为每个人计算总计
        Gather sumGather = null;
        String name = null;
        for (Gather g : list) {
            exportlist.add(g);
            if (name == null || !name.equals(g.getSalesManager())) {
                //若总计VO存在则插入导出list
                if (sumGather != null) {
                    exportlist.add(sumGather);
                }
                //初始化合计记录的VO
                name = g.getSalesManager();
                sumGather = new Gather();
                sumGather.setArea(name);
                sumGather.setDeptName("合计");
                sumGather.setQuotas(g.getQuotas());
                sumGather.setSummation(g.getSummation());
            } else if (name.equals(g.getSalesManager())) {
                //统计每人的总计
                sumGather.setQuotas(g.getQuotas().add(sumGather.getQuotas()));
                sumGather.setSummation(g.getSummation().add(sumGather.getSummation()));
            }
        }
        exportlist.add(sumGather);

        ExcelUtil<Gather> util = new ExcelUtil<Gather>(Gather.class);
        return util.exportExcel(exportlist, "个人消耗毛利汇总");
    }

}
