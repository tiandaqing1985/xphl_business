package com.ruoyi.system.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.domain.YwContract;
import com.ruoyi.system.domain.YwTract;
import com.ruoyi.system.mapper.YwBusinessMapper;
import com.ruoyi.system.mapper.YwContractMapper;
import com.ruoyi.system.mapper.YwTractMapper;


@Component("businessTask")
public class BusinessTask {
	
	@Autowired
	private YwBusinessMapper ywBusinessMapper;
	
	@Autowired
	private YwContractMapper ywContractMapper;
	
	
	@Autowired
	private YwTractMapper ywTractMapper;
	
	//未下单商机
	@Transactional
    public void businessTask1()
    {
    	YwBusiness ywBusiness = new YwBusiness();
    	ywBusiness.setBusinessStatus("0");
    	ywBusiness.setOrderStatus("0");  //未下单
    	//查询所有 有归属的商机
    	List<YwBusiness> list = ywBusinessMapper.selectYwBusinessList(ywBusiness);
    	
    	if(list!=null && list.size()>0){
    		
        	for(int i=0;i<list.size();i++){
        		
        		YwBusiness yb = list.get(i);
        		//查询该商机 的下单信息
        		//List<YwContract> ywContract = ywContractMapper.getYwContractByBusinessId(yb.getBusinessId());
        		
        		//if(ywContract==null || ywContract.size() == 0){//未下单
        			
        			List<YwTract> ywTract = ywTractMapper.getYwTractByBusinessId(yb.getBusinessId());
        			
        			if(ywTract==null || ywTract.size() == 0){//未跟进
        					
        				int  days = differentDaysByMillisecond(new Date(),yb.getCreateTime());
        				
        				if(days>30){//1个月未跟进
        					
        					yb.setBusinessUser("");
        					yb.setBusinessStatus("9");
        					
        					ywBusinessMapper.updateYwBusiness(yb);
        					
        					System.out.println(yb.getBusinessId()+"--------------------------------1个月未跟进，商机划入公共库");
        				}
        			}else{ //有跟进
        				
        				YwTract yt = ywTract.get(ywTract.size()-1);
        				
        				int  days = differentDaysByMillisecond(new Date(),yt.getTraceTime());
        				
        				if(days>30){//1个月未跟进
        					
        					yb.setBusinessUser("");
        					yb.setBusinessStatus("9");
        					
        					ywBusinessMapper.updateYwBusiness(yb);
        					
        					System.out.println(yb.getBusinessId()+"--------------------------------1个月未跟进 ，商机划入公共库");
        				}else{
        					
        					int  days1 = differentDaysByMillisecond(new Date(),yb.getCreateTime());
        					
        					if(days1>183){ // 有跟进  6个月未下单
            					yb.setBusinessUser("");
            					yb.setBusinessStatus("9");
            					
            					ywBusinessMapper.updateYwBusiness(yb);
        						System.out.println(yb.getBusinessId()+"--------------------------------6个月未下单 ，商机划入公共库");
        					}
        					
        				}
        				
        			}
        			
        		//}
        		
        	}
    	}
    	
    }

   //已下单商机
    @Transactional
    public void businessTask2()
    {
    	YwBusiness ywBusiness = new YwBusiness();
    	ywBusiness.setBusinessStatus("0"); //有归属人
    	ywBusiness.setOrderStatus("1");  //已下单
    	//按广告主、签约方 分组查询 获取 最新的 商机list
    	List<YwBusiness> list = ywBusinessMapper.selectYwBusinessGroupList(ywBusiness);
    	
    	
    	if(list!=null && list.size()>0){
    		
    		for(int i=0;i<list.size();i++){
    			
    			YwBusiness yb = list.get(i);
    			
    			List<YwContract> ywContract = ywContractMapper.getYwContractByBusinessId(yb.getBusinessId());
    			
    			if(ywContract==null || ywContract.size()==0){
    				
    				int days = differentDaysByMillisecond(new Date(),yb.getCreateTime());
    				
    				if(days>92){ //三个月没有新下单
    					YwBusiness yb1 = new YwBusiness();
    					
    					BeanUtils.copyBeanProp(yb, yb1);
    					
    					yb1.setCreateBy("公共");
    					yb1.setCreateTime(new Date());
    					yb1.setUpdateBy("公共");
    					yb1.setUpdateTime(new Date());
    					yb1.setBusinessUser("");
    					
    					yb1.setBusinessStatus("9");//商机状态属于 公共库
    					yb1.setOrderStatus("0"); //下单状态 未下单
    					
    					List<YwBusiness> yb2 = ywBusinessMapper.selectYwBusinessByMediaList(yb1);
    					String media = "";
    					if(yb2!=null && yb2.size()>0){
    						
    						media = yb2.get(0).getMedia();
    						
    						for(int j=1;j>yb2.size();j++){
    							
    							media += ","+yb2.get(i).getMedia();
    							
    						}
    					}
    					
    					yb1.setMedia(media);
    					
    					ywBusinessMapper.insertYwBusiness(yb1);
    				}
    				
    				
    			}
    			
    			
    		}
    	}
    	
    	
    }
    
    
    //吧到期合同 状态设置成 失效
    @Transactional
    public  void  contractTask1(){
    	YwContract ywContract = new YwContract();
    	YwBusiness ywBusiness = new YwBusiness();
    	
    	//合同到期的 商机状态改为 未下单
    	ywBusinessMapper.updateYwBusinessOrderStatusList(ywBusiness);
    	//到期合同状态改为 失效
    	ywContractMapper.updateYwContractByStatus(ywContract);
    }
    
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
}
