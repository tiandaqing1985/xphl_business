package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.YwBusiness;
import java.util.List;	

/**
 * 商机 数据层
 * 
 * @author ruoyi
 * @date 2019-05-30
 */
public interface YwBusinessMapper 
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
	public int insertYwBusiness(YwBusiness ywBusiness);
	
	/**
     * 修改商机
     * 
     * @param ywBusiness 商机信息
     * @return 结果
     */
	public int updateYwBusiness(YwBusiness ywBusiness);
	
	/**
     * 删除商机
     * 
     * @param businessId 商机ID
     * @return 结果
     */
	public int deleteYwBusinessById(Long businessId);
	
	/**
     * 批量删除商机
     * 
     * @param businessIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwBusinessByIds(String[] businessIds);
	
	
	public YwBusiness getCompanyName(YwBusiness ywBusiness);
	
	public List<YwBusiness> selectYwBusinessDecideList(YwBusiness ywBusiness);
	
	public YwBusiness  decideBusinessUser(YwBusiness ywBusiness);
	
	
	public List<YwBusiness> selectYwBusinessGroupList(YwBusiness ywBusiness);
	
	public List<YwBusiness> selectYwBusinessByMediaList(YwBusiness ywBusiness);
	
	public int updateYwBusinessOrderStatusList(YwBusiness ywBusiness);
	
}