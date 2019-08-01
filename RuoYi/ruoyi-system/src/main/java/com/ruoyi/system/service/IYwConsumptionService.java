package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwConsumption;
import java.util.List;

/**
 * 消耗情况 服务层
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public interface IYwConsumptionService 
{
	/**
     * 查询消耗情况信息
     * 
     * @param id 消耗情况ID
     * @return 消耗情况信息
     */
	public YwConsumption selectYwConsumptionById(Long id);
	
	/**
     * 查询消耗情况列表
     * 
     * @param ywConsumption 消耗情况信息
     * @return 消耗情况集合
     */
	public List<YwConsumption> selectYwConsumptionList(YwConsumption ywConsumption);
	
	/**
     * 新增消耗情况
     * 
     * @param ywConsumption 消耗情况信息
     * @return 结果
     */
	public int insertYwConsumption(YwConsumption ywConsumption);
	
	/**
     * 修改消耗情况
     * 
     * @param ywConsumption 消耗情况信息
     * @return 结果
     */
	public int updateYwConsumption(YwConsumption ywConsumption);
		
	/**
     * 删除消耗情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwConsumptionByIds(String ids);

	/**
	 * 导入消耗情况
	 * @param ywConsumptions
	 * @param updateSupport
	 * @param operName
	 * @return
	 */
    public String importYwConsumption(List<YwConsumption> ywConsumptions, boolean updateSupport, String operName);
}
