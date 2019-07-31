package com.ruoyi.web.controller.system;

import java.util.Date;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IYwBusinessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 商机 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-05-30
 */
@Controller
@RequestMapping("/system/ywBusiness")
public class YwBusinessController extends BaseController
{
    private String prefix = "system/ywBusiness";
	
	@Autowired
	private IYwBusinessService ywBusinessService;
	
	@Autowired
	private ISysDeptService sysDeptService;
	
	@Autowired
	private ISysUserService sysUserService;
	
	
	@RequiresPermissions("system:ywBusiness:view")
	@GetMapping()
	public String ywBusiness()
	{
	    return prefix + "/ywBusiness";
	}
	
	/**
	 * 查询商机列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwBusiness ywBusiness)
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
				 
				ywBusiness.setBusinessUser1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywBusiness.setBusinessUser(ShiroUtils.getSysUser().getUserName());
			}

		}
		ywBusiness.setBusinessStatus("0");
        List<YwBusiness> list = ywBusinessService.selectYwBusinessList(ywBusiness);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商机列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwBusiness ywBusiness)
    {
    	List<YwBusiness> list = ywBusinessService.selectYwBusinessList(ywBusiness);
        ExcelUtil<YwBusiness> util = new ExcelUtil<YwBusiness>(YwBusiness.class);
        return util.exportExcel(list, "ywBusiness");
    }
	
	/**
	 * 新增商机
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商机
	 */
	@Log(title = "商机", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult  addSave(YwBusiness ywBusiness)
	{		
		ywBusiness.setCreateBy(ShiroUtils.getSysUser().getUserName());
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date date = new Date();
			ywBusiness.setCreateTime(date);
			ywBusiness.setUpdateTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String str = ywBusinessService.insertYwBusiness(ywBusiness);
		
		return success(str);
	}

	/**
	 * 修改商机
	 */
	@GetMapping("/edit/{businessId}")
	public String edit(@PathVariable("businessId") Long businessId, ModelMap mmap)
	{
		YwBusiness ywBusiness = ywBusinessService.selectYwBusinessById(businessId);
		mmap.put("ywBusiness", ywBusiness);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商机
	 */
	@Log(title = "商机", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YwBusiness ywBusiness)
	{		
		return toAjax(ywBusinessService.updateYwBusiness(ywBusiness));
	}
	
	/**
	 * 删除商机
	 */
	@Log(title = "商机", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywBusinessService.deleteYwBusinessByIds(ids));
	}
	
	/**
	 * 查询系统是否已经有 签约方，广告主
	 * @param companyName
	 * @param advertiser
	 * @return
	 */
	@PostMapping( "/getCompanyName")
	@ResponseBody
	public String getCompanyName(YwBusiness ywBusiness){
		
		String str = "0";
		
		YwBusiness yb = ywBusinessService.getCompanyName(ywBusiness);
		if(yb!=null){
			str = "1";
		}
		return str;
	}
	
	
	
	@GetMapping("/decide")
	public String ywBusinessDecide()
	{
	    return prefix + "/ywBusinessDecide";
	}
	
	@PostMapping("/decideList")
	@ResponseBody
	public TableDataInfo decideList(YwBusiness ywBusiness)
	{
		startPage();
		ywBusiness.setBusinessStatus("2");
        List<YwBusiness> list = ywBusinessService.selectYwBusinessList(ywBusiness);
		return getDataTable(list);
	}
	
	@PostMapping( "/decideBusinessUser")
	@ResponseBody
	public AjaxResult decideBusinessUser(String ids)
	{		
		return toAjax(ywBusinessService.decideBusinessUser(ids));
	}
	
	
	@GetMapping("/public")
	public String ywBusinessPublic()
	{
	    return prefix + "/ywBusinessPublic";
	}
	
	
	@PostMapping("/publicList")
	@ResponseBody
	public TableDataInfo publicList(YwBusiness ywBusiness)
	{
		startPage();
		ywBusiness.setBusinessStatus("9");
        List<YwBusiness> list = ywBusinessService.selectYwBusinessList(ywBusiness);
		return getDataTable(list);
	}
	
	//无归属商机
	@GetMapping("/ywBusiness1")
	public String ywBusiness1()
	{
	    return prefix + "/ywBusiness1";
	}
	
	//无归属商机查询
	@PostMapping("/list1")
	@ResponseBody
	public TableDataInfo list1(YwBusiness ywBusiness)
	{
		startPage();
		
		SysDept dept = sysDeptService.selectDeptById(ShiroUtils.getSysUser().getDeptId());
		
		if(ShiroUtils.getUserId()==1 || ShiroUtils.getUserId()==103){ //超级管理员 和 任总看所有数据  
		
		
		}else{
			
			if(ShiroUtils.getSysUser().getUserName().equals(dept.getLeader())){  //部门leader看部门所有 销售经理看自己的
				
				List<SysUser> list = sysUserService.selectUserByDpetList(ShiroUtils.getSysUser().getDeptId());
				
				
				String str = list.get(0).getUserName();
				
				for(int i=1;i<list.size();i++){
					
					str += ","+list.get(i).getUserName();
				}
				 
				ywBusiness.setCreateBy1(Convert.toStrArray(str));
			}else{ //销售经理看自己的
				ywBusiness.setCreateBy(ShiroUtils.getSysUser().getUserName());
			}

		}
		ywBusiness.setBusinessStatus("1");
        List<YwBusiness> list = ywBusinessService.selectYwBusinessList(ywBusiness);
		return getDataTable(list);
	}
}
