package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysMenu;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.domain.YwContract;
import com.ruoyi.system.domain.YwTract;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.IYwBusinessService;
import com.ruoyi.system.service.IYwContractService;
import com.ruoyi.system.service.IYwTractService;

/**
 * 首页 业务处理
 * 
 * @author ruoyi
 */
@Controller
public class SysIndexController extends BaseController
{
    @Autowired
    private ISysMenuService menuService;
    
    @Autowired
    private IYwBusinessService ywBusinessService;
    
    @Autowired
    private IYwTractService ywTractService;
    
    @Autowired
    private IYwContractService ywContractService;
    
    

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        SysUser user = ShiroUtils.getSysUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", Global.getCopyrightYear());
        
        
        if(user.getUserId()==1){
        	mmap.put("demoEnabled", true);
        }else{
        	mmap.put("demoEnabled", false);
        }
        
        
        
        
        return "index";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
    	//总有效商机
    	YwBusiness ywBusiness = new YwBusiness();
    	ywBusiness.setBusinessStatus("0");
    	List<YwBusiness> list1 = ywBusinessService.selectYwBusinessList(ywBusiness);
    	mmap.put("bu", list1.size());
    	
    	ywBusiness.setCreateTime(DateUtils.lastMonday());
    	
    	//上周新增有效商机
    	List<YwBusiness> list2 = ywBusinessService.selectYwBusinessList(ywBusiness);
    	mmap.put("bu1", list2.size());
    	
    	//总跟进记录
    	YwTract ywTract = new YwTract();
    	List<YwTract> list3 = ywTractService.selectYwTractList(ywTract);
    	mmap.put("tr", list3.size());
    	
    	ywTract.setCreateTime(DateUtils.lastMonday());
    	List<YwTract> list4 = ywTractService.selectYwTractList(ywTract);
    	mmap.put("tr1", list4.size());
    	
    	//总有效合同
    	YwContract ywContract = new YwContract();
    	ywContract.setStatus("0");//有效合同
    	List<YwContract> list5 = ywContractService.selectYwContractList(ywContract);
    	mmap.put("con", list5.size());
    	
    	ywContract.setCreateTime(DateUtils.lastMonday());
    	List<YwContract> list6 = ywContractService.selectYwContractList(ywContract);
    	mmap.put("con1", list6.size());
    	
    	
    	if(ShiroUtils.getUserId()==103 || ShiroUtils.getUserId()==1){
    		mmap.put("admin", true);
    	}
    	
        mmap.put("version", Global.getVersion());
        return "main";
    }
}
