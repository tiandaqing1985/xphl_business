package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.ywArrearage.*;

import java.util.List;

/**
 * 商机-欠款 数据层
 * 
 * @author ruoyi
 * @date 2019-08-14
 */
public interface YwArrearageMapper 
{
	/**
     * 查询商机-欠款信息
     * 
     * @param id 商机-欠款ID
     * @return 商机-欠款信息
     */
	public YwArrearage selectYwArrearageById(Long id);
	
	/**
     * 查询商机-欠款列表
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 商机-欠款集合
     */
	public List<YwArrearage> selectYwArrearageList(YwArrearage ywArrearage);
	
	/**
     * 新增商机-欠款
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
	public int insertYwArrearage(YwArrearage ywArrearage);
	
	/**
     * 修改商机-欠款
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
	public int updateYwArrearage(YwArrearage ywArrearage);
	
	/**
     * 删除商机-欠款
     * 
     * @param id 商机-欠款ID
     * @return 结果
     */
	public int deleteYwArrearageById(Long id);
	
	/**
     * 批量删除商机-欠款
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwArrearageByIds(String[] ids);

	/**
	 * 查询汇总表-按销售经理
	 *
	 * @param ywArrearage 查询条件
	 * @return
	 */
    public List<SaleManagerArrearageGather> selectGatherSaleManager(YwArrearage ywArrearage);

	/**
	 * 查询汇总表-按客户
	 *
	 * @param ywArrearage 查询条件
	 * @return
	 */
    public List<CustomerArrearageGather> selectGatherCustomer(YwArrearage ywArrearage);


	/**
	 * 查询实际应收账款回款率排名
	 *
	 * @param ywArrearage 查询条件
	 * @return
	 */
    public List<ReturnRateRank> selectRealReturnRateRank(YwArrearage ywArrearage);


	/**
	 * 查询回款情况
	 *
	 * @param ywArrearage 查询条件
	 * @return
	 */
    public List<ReturnSituation> selectReturnSituation(YwArrearage ywArrearage);

}