package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwTract;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IYwTractService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 跟进 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-11
 */
@Controller
@RequestMapping("/system/ywTract")
public class YwTractController extends BaseController
{
    private String prefix = "system/ywTract";
	
	@Autowired
	private IYwTractService ywTractService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@Autowired
	private ISysDeptService sysDeptService;
	
	@RequiresPermissions("system:ywTract:view")
	@GetMapping()
	public String ywTract()
	{
	    return prefix + "/ywTract";
	}
	
	/**
	 * 查询跟进列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwTract ywTract)
	{
		startPage();
		
		
		SysDept dept = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getDeptId());
		
		if(ShiroUtils.getUserId()==1 || ShiroUtils.getUserId()==103){ //超级管理员 和 任总看所有数据  
		
		
		}else{
			
			if(ShiroUtils.getSysUser().getUserName().equals(dept.getLeader())){  //部门leader看部门所有 销售经理看自己的
				
				Long deptId = ShiroUtils.getSysUser().getDeptId();
				
				if("王硕".equals(ShiroUtils.getSysUser().getUserName())){
					deptId = 260L;
				}
				
				List<SysUser> list = sysUserService.selectUserByDpetList(deptId);
				
				
				String str = list.get(0).getUserName();
				
				for(int i=1;i<list.size();i++){
					
					str += ","+list.get(i).getUserName();
				}
				 
				ywTract.setCreateBy1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywTract.setCreateBy(ShiroUtils.getSysUser().getUserName());
			}

		}
        List<YwTract> list = ywTractService.selectYwTractList(ywTract);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出跟进列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwTract ywTract)
    {
    	
		SysDept dept = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getDeptId());
		
		if(ShiroUtils.getUserId()==1 || ShiroUtils.getUserId()==103){ //超级管理员 和 任总看所有数据  
		
		
		}else{
			
			if(ShiroUtils.getSysUser().getUserName().equals(dept.getLeader())){  //部门leader看部门所有 销售经理看自己的
				
				Long deptId = ShiroUtils.getSysUser().getDeptId();
				
				if("王硕".equals(ShiroUtils.getSysUser().getUserName())){
					deptId = 260L;
				}
				
				List<SysUser> list = sysUserService.selectUserByDpetList(deptId);
				
				
				String str = list.get(0).getUserName();
				
				for(int i=1;i<list.size();i++){
					
					str += ","+list.get(i).getUserName();
				}
				 
				ywTract.setCreateBy1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywTract.setCreateBy(ShiroUtils.getSysUser().getUserName());
			}

		}
    	
    	
    	List<YwTract> list = ywTractService.selectYwTractList(ywTract);
        ExcelUtil<YwTract> util = new ExcelUtil<YwTract>(YwTract.class);
        return util.exportExcel(list, "ywTract");
    }
	
	/**
	 * 新增跟进
	 */
	@GetMapping("/add/{businessId}")
	public String add(@PathVariable("businessId") Long businessId, Model m)
	{
		m.addAttribute("businessId", businessId);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存跟进
	 */
	@Log(title = "跟进", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YwTract ywTract)
	{		
		ywTract.setCreateBy(ShiroUtils.getSysUser().getUserName());
		ywTract.setCreateTime(new Date());
		return toAjax(ywTractService.insertYwTract(ywTract));
	}

	/**
	 * 修改跟进
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		YwTract ywTract = ywTractService.selectYwTractById(id);
		mmap.put("ywTract", ywTract);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存跟进
	 */
	@Log(title = "跟进", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YwTract ywTract)
	{		
		return toAjax(ywTractService.updateYwTract(ywTract));
	}
	
	/**
	 * 删除跟进
	 */
	@Log(title = "跟进", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywTractService.deleteYwTractByIds(ids));
	}
	
}
