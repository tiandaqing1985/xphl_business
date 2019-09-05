package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.ywArrearage.*;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.web.controller.tool.GatherExcelUtil;
import com.ruoyi.web.controller.tool.QuarterUtil;
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
import com.ruoyi.system.service.IYwArrearageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商机-欠款 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-14
 */
@Controller
@RequestMapping("/system/ywArrearage")
public class YwArrearageController extends BaseController {
    private String prefix = "system/ywArrearage";

    @Autowired
    private IYwArrearageService ywArrearageService;

    @RequiresPermissions("system:ywArrearage:view")
    @GetMapping()
    public String ywArrearage() {
        return prefix + "/ywArrearage";
    }

    /**
     * 查询商机-欠款列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YwArrearage ywArrearage) {
        startPage();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<YwArrearage> list = ywArrearageService.selectYwArrearageList(ywArrearage);
        return getDataTable(list);
    }

    /**
     * 查询汇总表-按销售经理
     */
    @PostMapping("/listGatherBySaleManager")
    @ResponseBody
    public TableDataInfo listGatherBySaleManager(YwArrearage ywArrearage) {
        List<SaleManagerArrearageGather> list = new ArrayList<>();
        SysUser loginUser = ShiroUtils.getSysUser();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        list = ywArrearageService.selectGatherSaleManager(ywArrearage, loginUser);
        return getDataTable(list);
    }

    /**
     * 导出汇总表-按销售经理
     */
    @PostMapping("/exportGatherBySaleManager")
    @ResponseBody
    public AjaxResult exportGatherBySaleManager(YwArrearage ywArrearage) {
        List<SaleManagerArrearageGather> list = new ArrayList<>();
        SysUser loginUser = ShiroUtils.getSysUser();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        list = ywArrearageService.selectGatherSaleManager(ywArrearage, loginUser);
        GatherExcelUtil<SaleManagerArrearageGather> excelUtil = new GatherExcelUtil<SaleManagerArrearageGather>(SaleManagerArrearageGather.class);
        return excelUtil.exportGatherExcel(list, "汇总表-按销售经理");
    }

    /**
     * 查询汇总表-按客户
     */
    @PostMapping("/listGatherByCustomer")
    @ResponseBody
    public TableDataInfo listGatherByCustomer(YwArrearage ywArrearage) {
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<CustomerArrearageGather> list = ywArrearageService.selectGatherCustomer(ywArrearage);
        return getDataTable(list);
    }

    /**
     * 导出汇总表-按客户
     */
    @PostMapping("/exportGatherByCustomer")
    @ResponseBody
    public AjaxResult exportGatherByCustomer(YwArrearage ywArrearage) {
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<CustomerArrearageGather> list = ywArrearageService.selectGatherCustomer(ywArrearage);
        GatherExcelUtil<CustomerArrearageGather> excelUtil = new GatherExcelUtil<>(CustomerArrearageGather.class);
        return excelUtil.exportGatherExcel(list, "汇总表-按客户");
    }

    /**
     * 查询实际应收账款回款率排名
     */
    @PostMapping("/listRealReturnRateRank")
    @ResponseBody
    public TableDataInfo listRealReturnRateRank(YwArrearage ywArrearage) {
        startPage();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getLastMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getLastMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<ReturnRateRank> returnRateRanks = ywArrearageService.selectRealReturnRateRank(ywArrearage);
        return getDataTable(returnRateRanks);
    }

    /**
     * 导出实际应收账款回款率排名
     */
    @PostMapping("/exportRealReturnRateRank")
    @ResponseBody
    public AjaxResult exportRealReturnRateRank(YwArrearage ywArrearage) {
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getLastMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getLastMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<ReturnRateRank> returnRateRanks = ywArrearageService.selectRealReturnRateRank(ywArrearage);
        ExcelUtil<ReturnRateRank> excelUtil = new ExcelUtil<>(ReturnRateRank.class);
        return excelUtil.exportExcel(returnRateRanks, "实际应收账款回款率排名");
    }

    /**
     * 查询回款情况
     */
    @PostMapping("/listReturnSituation")
    @ResponseBody
    public TableDataInfo listReturnSituation(YwArrearage ywArrearage) {
        startPage();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<ReturnSituation> returnRateRanks = ywArrearageService.selectReturnSituation(ywArrearage);
        return getDataTable(returnRateRanks);
    }

    /**
     * 导出回款情况
     */
    @PostMapping("/exportReturnSituation")
    @ResponseBody
    public AjaxResult exportReturnSituation(YwArrearage ywArrearage) {
        startPage();
        if(ywArrearage.getDueDateStart()==null||ywArrearage.getDueDateStart().equals("")){
            ywArrearage.setDueDateStart(QuarterUtil.getNowMonthFirstDay());
        }
        if(ywArrearage.getDueDateEnd()==null||ywArrearage.getDueDateEnd().equals("")){
            ywArrearage.setDueDateEnd(QuarterUtil.getNowMonthFinallyDay());
        }
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<ReturnSituation> returnRateRanks = ywArrearageService.selectReturnSituation(ywArrearage);
        ExcelUtil<ReturnSituation> excelUtil = new ExcelUtil<>(ReturnSituation.class);
        return excelUtil.exportExcel(returnRateRanks, "回款情况");
    }

    /**
     * 导出商机-欠款列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwArrearage ywArrearage) {
        if(ywArrearage.getCreateTime()==null){
            ywArrearage.setCreateTime(DateUtils.dateTime("yyyy-MM-dd",QuarterUtil.getNowMonthFirstDay()));
        }
        List<YwArrearage> list = ywArrearageService.selectYwArrearageList(ywArrearage);
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        return util.exportExcel(list, "ywArrearage");
    }

    /**
     * 新增商机-欠款
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商机-欠款
     */
    @Log(title = "商机-欠款", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YwArrearage ywArrearage) {
        return toAjax(ywArrearageService.insertYwArrearage(ywArrearage));
    }

    /**
     * 修改商机-欠款
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        YwArrearage ywArrearage = ywArrearageService.selectYwArrearageById(id);
        mmap.put("ywArrearage", ywArrearage);
        return prefix + "/edit";
    }

    /**
     * 修改保存商机-欠款
     */
    @Log(title = "商机-欠款", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YwArrearage ywArrearage) {
        return toAjax(ywArrearageService.updateYwArrearage(ywArrearage));
    }

    /**
     * 删除商机-欠款
     */
    @Log(title = "商机-欠款", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ywArrearageService.deleteYwArrearageByIds(ids));
    }

    /**
     * 导入
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "商机-欠款导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        List<YwArrearage> ywArrearages = util.importExcel(file.getInputStream(), 0, 1);
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = ywArrearageService.importYwArrearages(ywArrearages, true, operName);
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
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        return util.importTemplateExcel("商机-欠款");
    }

}
