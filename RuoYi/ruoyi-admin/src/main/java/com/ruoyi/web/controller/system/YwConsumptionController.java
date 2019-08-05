package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwGrossMargins;
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
import com.ruoyi.system.domain.YwConsumption;
import com.ruoyi.system.service.IYwConsumptionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 消耗情况 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-01
 */
@Controller
@RequestMapping("/system/ywConsumption")
public class YwConsumptionController extends BaseController {
    private String prefix = "system/ywConsumption";

    @Autowired
    private IYwConsumptionService ywConsumptionService;

    @RequiresPermissions("system:ywConsumption:view")
    @GetMapping()
    public String ywConsumption() {
        return prefix + "/ywConsumption";
    }

    /**
     * 查询消耗情况列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(YwConsumption ywConsumption) {
        startPage();
        List<YwConsumption> list = ywConsumptionService.selectYwConsumptionList(ywConsumption);
        return getDataTable(list);
    }


    /**
     * 导出消耗情况列表
     */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwConsumption ywConsumption) {
        List<YwConsumption> list = ywConsumptionService.selectYwConsumptionList(ywConsumption);
        ExcelUtil<YwConsumption> util = new ExcelUtil<YwConsumption>(YwConsumption.class);
        return util.exportExcel(list, "消耗情况");
    }

    /**
     * 新增消耗情况
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存消耗情况
     */
    @Log(title = "消耗情况", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(YwConsumption ywConsumption) {
        String operName = ShiroUtils.getSysUser().getLoginName();
        ywConsumption.setCreateBy(operName);
        ywConsumption.setCreateTime(DateUtils.getNowDate());
        ywConsumption.setQuarter(getQuarter(ywConsumption.getTerm()));
        return toAjax(ywConsumptionService.insertYwConsumption(ywConsumption));
    }

    /**
     * 修改消耗情况
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        YwConsumption ywConsumption = ywConsumptionService.selectYwConsumptionById(id);
        mmap.put("ywConsumption", ywConsumption);
        return prefix + "/edit";
    }

    /**
     * 修改保存消耗情况
     */
    @Log(title = "消耗情况", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(YwConsumption ywConsumption) {
        String operName = ShiroUtils.getSysUser().getLoginName();
        ywConsumption.setUpdateTime(DateUtils.getNowDate());
        ywConsumption.setUpdateBy(operName);
        return toAjax(ywConsumptionService.updateYwConsumption(ywConsumption));
    }

    /**
     * 删除消耗情况
     */
    @Log(title = "消耗情况", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ywConsumptionService.deleteYwConsumptionByIds(ids));
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
