package com.ruoyi.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.system.mapper.YwBusinessMapper;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.service.IYwBusinessService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商机 服务层实现
 * 
 * @author ruoyi
 * @date 2019-05-30
 */
@Service
public class YwBusinessServiceImpl implements IYwBusinessService 
{
	@Autowired
	private YwBusinessMapper ywBusinessMapper;

	/**
     * 查询商机信息
     * 
     * @param businessId 商机ID
     * @return 商机信息
     */
    @Override
	public YwBusiness selectYwBusinessById(Long businessId)
	{
	    return ywBusinessMapper.selectYwBusinessById(businessId);
	}
	
	/**
     * 查询商机列表
     * 
     * @param ywBusiness 商机信息
     * @return 商机集合
     */
	@Override
	public List<YwBusiness> selectYwBusinessList(YwBusiness ywBusiness)
	{
	    return ywBusinessMapper.selectYwBusinessList(ywBusiness);
	}
	
    /**
     * 新增商机
     * 
     * @param ywBusiness 商机信息
     * @return 结果
     */
	@Override
	@Transactional
	public String insertYwBusiness(YwBusiness ywBusiness)
	{
		/**
		 * businessStatus 0,businessUser 不为空  为归属状态
		 * businessStatus 1,businessUser 为空   为不属于自己状态
		 * businessStatus 2,businessUser 为空   商机属于判定状态
		 * 
		 * businessStatus 9,businessUser 为空   商机属于公库
		 */
		ywBusiness.setBusinessGrade(getGrade(ywBusiness));
		ywBusiness.setBusinessStatus("0");
		
		YwBusiness yb = ywBusinessMapper.getCompanyName(ywBusiness);
		
		if(yb!=null){
			int day = differentDaysByMillisecond(yb.getCreateTime(),ywBusiness.getCreateTime());
			
			if(day<=7){//间隔小于7天
				
				//商机线索归属分数高的创建人
				if(Integer.parseInt(ywBusiness.getBusinessGrade()) >= Integer.parseInt(yb.getBusinessGrade())){
					ywBusiness.setBusinessUser(ywBusiness.getCreateBy());
					ywBusinessMapper.insertYwBusiness(ywBusiness);
					
					yb.setBusinessUser("");
					yb.setBusinessStatus("1");
					ywBusinessMapper.updateYwBusiness(yb);
					
					// 邮件通知商机线索 归属他人  还没做
					return "商机线索录入成功！";
				}else{
					ywBusiness.setBusinessUser("");
					ywBusiness.setBusinessStatus("1");
					
					ywBusinessMapper.insertYwBusiness(ywBusiness);
					
					return "此次录入的商机线索评分低于系统已有商机线索，很遗憾此商机线索暂时不能分配给你！";
				}
				
			}else{
				if(Integer.parseInt(ywBusiness.getBusinessGrade()) >= Integer.parseInt(yb.getBusinessGrade())){
					yb.setBusinessUser("");
					yb.setBusinessStatus("2");
					ywBusinessMapper.updateYwBusiness(yb);
					
					ywBusiness.setBusinessStatus("2");
					ywBusiness.setBusinessUser("");
					ywBusinessMapper.insertYwBusiness(ywBusiness);
					
					// 邮件通知、  还没做
					
					return "此次录入的商机线索需要人工评判归属！";
				}else{
					
					ywBusiness.setBusinessStatus("1");
					ywBusinessMapper.insertYwBusiness(ywBusiness);
					return "此次录入的商机线索评分低于系统已有商机线索，很遗憾此商机线索暂时不能分配给你！";
				}
			}
			
		}else{
			ywBusiness.setBusinessUser(ywBusiness.getCreateBy());
			ywBusinessMapper.insertYwBusiness(ywBusiness);
			return "商机线索录入成功！";
		}
		
	}
	
	/**
     * 修改商机
     * 
     * @param ywBusiness 商机信息
     * @return 结果
     */
	@Override
	public int updateYwBusiness(YwBusiness ywBusiness)
	{
		ywBusiness.setBusinessGrade(getGrade(ywBusiness));
	    return ywBusinessMapper.updateYwBusiness(ywBusiness);
	}

	/**
     * 删除商机对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteYwBusinessByIds(String ids)
	{
		return ywBusinessMapper.deleteYwBusinessByIds(Convert.toStrArray(ids));
	}

	@Override
	public YwBusiness getCompanyName(YwBusiness ywBusiness) {
		return ywBusinessMapper.getCompanyName(ywBusiness);
	}
	
	

    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

	@Override
	@Transactional
	public int decideBusinessUser(String ids) {
		
		Long businessId = Long.parseLong(ids);
		YwBusiness yb = ywBusinessMapper.selectYwBusinessById(businessId);
		yb.setBusinessStatus("0");
		yb.setBusinessUser(yb.getCreateBy());
		
		ywBusinessMapper.updateYwBusiness(yb);
		
		YwBusiness yb1 = ywBusinessMapper.decideBusinessUser(yb);
		
		yb1.setBusinessStatus("1");
		
		return ywBusinessMapper.updateYwBusiness(yb1);
	}

	public static String getGrade(YwBusiness ywBusiness){
		
		int temp = 20;
		
		int gw = Integer.parseInt(ywBusiness.getPosition1());
		//岗位分数
		if(!"".equals(ywBusiness.getPosition2()) && ywBusiness.getPosition2()!=null){
			
			if(gw<Integer.parseInt(ywBusiness.getPosition2())){
				gw = Integer.parseInt(ywBusiness.getPosition2());
			}
		}
		
		if(!"".equals(ywBusiness.getPosition3()) && ywBusiness.getPosition3()!=null){
			
			if(gw<Integer.parseInt(ywBusiness.getPosition3())){
				gw = Integer.parseInt(ywBusiness.getPosition3());
			}
			
		}
		
		temp += gw;
		
		int zz = Integer.parseInt(ywBusiness.getResponsibility1());
		//职责分数
		if(!"".equals(ywBusiness.getResponsibility2()) && ywBusiness.getResponsibility2()!=null){
			
			if(gw<Integer.parseInt(ywBusiness.getResponsibility2())){
				gw = Integer.parseInt(ywBusiness.getResponsibility2());
			}
		}
		
		if(!"".equals(ywBusiness.getResponsibility3()) && ywBusiness.getResponsibility3()!=null){
			
			if(gw<Integer.parseInt(ywBusiness.getResponsibility3())){
				gw = Integer.parseInt(ywBusiness.getResponsibility3());
			}
			
		}
		
		
		temp += zz;
		
		if(!"".equals(ywBusiness.getLinkname2()) && ywBusiness.getLinkname2()!=null){
			temp += 2;
		}
		
		if(!"".equals(ywBusiness.getLinkname2()) && ywBusiness.getLinkname2()!=null){
			temp += 2;
		}
		
		if(!"".equals(ywBusiness.getCompanyRemark1()) && ywBusiness.getCompanyRemark1()!=null){
			temp += 5;
		}
		if(!"".equals(ywBusiness.getCompanyRemark2()) && ywBusiness.getCompanyRemark2()!=null){
			temp += 5;
		}
		if(!"".equals(ywBusiness.getCompanyRemark3()) && ywBusiness.getCompanyRemark3()!=null){
			temp += 10;
		}
		if(!"".equals(ywBusiness.getCompanyRemark4()) && ywBusiness.getCompanyRemark4()!=null){
			temp += 6;
		}
		if(!"".equals(ywBusiness.getCompanyRemark5()) && ywBusiness.getCompanyRemark5()!=null){
			temp += 7;
		}
		if(!"".equals(ywBusiness.getCompanyRemark6()) && ywBusiness.getCompanyRemark6()!=null){
			temp += 7;
		}
		if(!"".equals(ywBusiness.getCompanyRemark7()) && ywBusiness.getCompanyRemark7()!=null){
			temp += 6;
		}
		if(!"".equals(ywBusiness.getCompanyRemark8()) && ywBusiness.getCompanyRemark8()!=null){
			temp += 7;
		}
		if(!"".equals(ywBusiness.getCompanyRemark9()) && ywBusiness.getCompanyRemark9()!=null){
			temp += 7;
		}
		return (temp+"");
	}
	
}
