package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwArrearage;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IYwArrearageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 商机-欠款 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-30
 */
@Controller
@RequestMapping("/system/ywArrearage")
public class YwArrearageController extends BaseController
{
    private String prefix = "system/ywArrearage";
	
	@Autowired
	private IYwArrearageService ywArrearageService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	
	@Autowired
	private ISysDeptService sysDeptService;
	
	@RequiresPermissions("system:ywArrearage:view")
	@GetMapping()
	public String ywArrearage()
	{
	    return prefix + "/ywArrearage";
	}
	
	/**
	 * 查询商机-欠款列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwArrearage ywArrearage)
	{
		startPage();
		
		
		List<SysRole> rlist = ShiroUtils.getSysUser().getRoles();
		
		
		
		SysDept dept = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getDeptId());
		
		if(ShiroUtils.getUserId()==1 || ShiroUtils.getUserId()==103){ //超级管理员 和 任总看所有数据  
		
		
		}else{
			
			if(ShiroUtils.getSysUser().getUserName().equals(dept.getLeader())){  //部门leader看部门所有 销售经理看自己的
				
				List<SysUser> list = sysUserService.selectUserByDpetList(ShiroUtils.getSysUser().getDeptId());
				
				
				String str = list.get(0).getUserName();
				
				for(int i=1;i<list.size();i++){
					
					str += ","+list.get(i).getUserName();
				}
				 
				if(ShiroUtils.getSysUser().getDeptId()!=254){ //排除商务支持部的leader
					ywArrearage.setCreateBy1(Convert.toStrArray(str));
				}
				
			}else{ //销售经理看自己的
				
				for(int i=0;i<rlist.size();i++){
					SysRole role = rlist.get(i);
					
					if("销售助理".equals(role.getRoleName())){
						ywArrearage.setCreateBy(ShiroUtils.getSysUser().getUserName());
					}
					
					if("销售经理".equals(role.getRoleName())){
						ywArrearage.setSales(ShiroUtils.getSysUser().getUserName());
					}
					
				}
				
				
			}

		}
		
		

		

		
		
        List<YwArrearage> list = ywArrearageService.selectYwArrearageList(ywArrearage);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商机-欠款列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwArrearage ywArrearage)
    {
    	List<YwArrearage> list = ywArrearageService.selectYwArrearageList(ywArrearage);
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        return util.exportExcel(list, "ywArrearage");
    }
	
	/**
	 * 新增商机-欠款
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商机-欠款
	 */
	@Log(title = "商机-欠款", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YwArrearage ywArrearage)
	{		
		return toAjax(ywArrearageService.insertYwArrearage(ywArrearage));
	}

	/**
	 * 修改商机-欠款
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
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
	public AjaxResult editSave(YwArrearage ywArrearage)
	{		
		return toAjax(ywArrearageService.updateYwArrearage(ywArrearage));
	}
	
	/**
	 * 删除商机-欠款
	 */
	@Log(title = "商机-欠款", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywArrearageService.deleteYwArrearageByIds(ids));
	}
	
	@GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        return util.importTemplateExcel("欠款情况");
    }
    
    
    @Log(title = "欠款情况", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<YwArrearage> util = new ExcelUtil<YwArrearage>(YwArrearage.class);
        List<YwArrearage> jffList = util.importExcel(file.getInputStream(),0,1);
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = ywArrearageService.importArrearage(jffList, false, operName);
        return AjaxResult.success(message);
        
    }
    
    
	//欠款汇总
	@GetMapping("/summarizing")
	public String summarizing()
	{
	    return prefix + "/summarizing";
	}
	
	
	/**
	 * 查询商机-欠款列表
	 */
	@PostMapping("/summarizingList")
	@ResponseBody
	public TableDataInfo summarizingList(YwArrearage ywArrearage)
	{
		startPage();
        List<YwArrearage> list = ywArrearageService.selectSumList(ywArrearage);
		return getDataTable(list);
	}
	
}
