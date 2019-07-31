package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwContract;
import java.util.List;

/**
 * 下单 服务层
 * 
 * @author ruoyi
 * @date 2019-06-12
 */
public interface IYwContractService 
{
	/**
     * 查询下单信息
     * 
     * @param id 下单ID
     * @return 下单信息
     */
	public YwContract selectYwContractById(Long id);
	
	/**
     * 查询下单列表
     * 
     * @param ywContract 下单信息
     * @return 下单集合
     */
	public List<YwContract> selectYwContractList(YwContract ywContract);
	
	/**
     * 新增下单
     * 
     * @param ywContract 下单信息
     * @return 结果
     */
	public int insertYwContract(YwContract ywContract);
	
	/**
     * 修改下单
     * 
     * @param ywContract 下单信息
     * @return 结果
     */
	public int updateYwContract(YwContract ywContract);
		
	/**
     * 删除下单信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwContractByIds(String ids);
	
	
}
