package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
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
import com.ruoyi.system.domain.YwGrossMargins;
import com.ruoyi.system.service.IYwGrossMarginsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 毛利情况 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
@Controller
@RequestMapping("/system/ywGrossMargins")
public class YwGrossMarginsController extends BaseController
{
    private String prefix = "system/ywGrossMargins";
	
	@Autowired
	private IYwGrossMarginsService ywGrossMarginsService;
	
	@RequiresPermissions("system:ywGrossMargins:view")
	@GetMapping()
	public String ywGrossMargins()
	{
	    return prefix + "/ywGrossMargins";
	}
	
	/**
	 * 查询毛利情况列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwGrossMargins ywGrossMargins)
	{
		startPage();
        List<YwGrossMargins> list = ywGrossMarginsService.selectYwGrossMarginsList(ywGrossMargins);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出毛利情况列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwGrossMargins ywGrossMargins)
    {
    	List<YwGrossMargins> list = ywGrossMarginsService.selectYwGrossMarginsList(ywGrossMargins);
        ExcelUtil<YwGrossMargins> util = new ExcelUtil<YwGrossMargins>(YwGrossMargins.class);
        return util.exportExcel(list, "毛利情况");
    }
	
	/**
	 * 新增毛利情况
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存毛利情况
	 */
	@Log(title = "毛利情况", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YwGrossMargins ywGrossMargins)
	{
		ywGrossMargins.setCreateTime(DateUtils.getNowDate());
		String operName = ShiroUtils.getSysUser().getLoginName();
		ywGrossMargins.setCreateBy(operName);
		ywGrossMargins.setQuarter(getQuarter(ywGrossMargins.getTerm()));
		return toAjax(ywGrossMarginsService.insertYwGrossMargins(ywGrossMargins));
	}

	/**
	 * 修改毛利情况
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		YwGrossMargins ywGrossMargins = ywGrossMarginsService.selectYwGrossMarginsById(id);
		mmap.put("ywGrossMargins", ywGrossMargins);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存毛利情况
	 */
	@Log(title = "毛利情况", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YwGrossMargins ywGrossMargins)
	{
		ywGrossMargins.setUpdateTime(DateUtils.getNowDate());
		String operName = ShiroUtils.getSysUser().getLoginName();
		ywGrossMargins.setUpdateBy(operName);
		return toAjax(ywGrossMarginsService.updateYwGrossMargins(ywGrossMargins));
	}
	
	/**
	 * 删除毛利情况
	 */
	@Log(title = "毛利情况", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywGrossMarginsService.deleteYwGrossMarginsByIds(ids));
	}

	/**
	 * 导入
	 * @param file
	 * @param updateSupport
	 * @return
	 * @throws Exception
	 */
	@Log(title = "毛利情况", businessType = BusinessType.IMPORT)
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
	{
		ExcelUtil<YwGrossMargins> util = new ExcelUtil<YwGrossMargins>(YwGrossMargins.class);
		List<YwGrossMargins> grossMarginList = util.importExcel(file.getInputStream(),0,1);
		String operName = ShiroUtils.getSysUser().getLoginName();
		String message = ywGrossMarginsService.importYwGrossMargins(grossMarginList, updateSupport, operName);
		return AjaxResult.success(message);
	}

	/**
	 * 导入模板
	 * @return
	 */
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate()
	{
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
