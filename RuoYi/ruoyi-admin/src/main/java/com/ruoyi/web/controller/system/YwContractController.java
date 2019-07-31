package com.ruoyi.web.controller.system;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
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
import com.ruoyi.system.domain.YwContract;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IYwContractService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 下单 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-12
 */
@Controller
@RequestMapping("/system/ywContract")
public class YwContractController extends BaseController
{
    private String prefix = "system/ywContract";
	
	@Autowired
	private IYwContractService ywContractService;
	
	@Autowired
	private ISysDeptService sysDeptService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	@RequiresPermissions("system:ywContract:view")
	@GetMapping()
	public String ywContract()
	{
	    return prefix + "/ywContract";
	}
	
	/**
	 * 查询下单列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwContract ywContract)
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
				 
				ywContract.setCreateBy1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywContract.setCreateBy(ShiroUtils.getSysUser().getUserName());
			}

		}
		
        List<YwContract> list = ywContractService.selectYwContractList(ywContract);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出下单列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwContract ywContract)
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
				 
				ywContract.setCreateBy1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywContract.setCreateBy(ShiroUtils.getSysUser().getUserName());
			}

		}
    	
    	List<YwContract> list = ywContractService.selectYwContractList(ywContract);
        ExcelUtil<YwContract> util = new ExcelUtil<YwContract>(YwContract.class);
        return util.exportExcel(list, "ywContract");
    }
	
	/**
	 * 新增下单
	 */
	@GetMapping("/add/{businessId}")
	public String add(@PathVariable("businessId") Long businessId, Model m)
	{
		m.addAttribute("businessId", businessId);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存下单
	 */
	@Log(title = "下单", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YwContract ywContract)
	{		
		ywContract.setCreateBy(ShiroUtils.getSysUser().getUserName());
		ywContract.setCreateTime(new Date());
		ywContract.setStatus("0"); //合同状态0  有效，1 失效
		return toAjax(ywContractService.insertYwContract(ywContract));
	}

	/**
	 * 修改下单
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		YwContract ywContract = ywContractService.selectYwContractById(id);
		mmap.put("ywContract", ywContract);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存下单
	 */
	@Log(title = "下单", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YwContract ywContract)
	{		
		return toAjax(ywContractService.updateYwContract(ywContract));
	}
	
	/**
	 * 删除下单
	 */
	@Log(title = "下单", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywContractService.deleteYwContractByIds(ids));
	}
	
}
