package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwBusiness;
import java.util.List;

/**
 * 商机 服务层
 * 
 * @author ruoyi
 * @date 2019-05-30
 */
public interface IYwBusinessService 
{
	/**
     * 查询商机信息
     * 
     * @param businessId 商机ID
     * @return 商机信息
     */
	public YwBusiness selectYwBusinessById(Long businessId);
	
	/**
     * 查询商机列表
     * 
     * @param ywBusiness 商机信息
     * @return 商机集合
     */
	public List<YwBusiness> selectYwBusinessList(YwBusiness ywBusiness);
	
	/**
     * 新增商机
     * 
     * @param ywBusiness 商机信息
     * @return 结果
     */
	public String insertYwBusiness(YwBusiness ywBusiness);
	
	/**
     * 修改商机
     * 
     * @param ywBusiness 商机信息
     * @return 结果
     */
	public int updateYwBusiness(YwBusiness ywBusiness);
		
	/**
     * 删除商机信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwBusinessByIds(String ids);
	
	
	public YwBusiness getCompanyName(YwBusiness ywBusiness);
	
	
	public int decideBusinessUser(String ids);
}
