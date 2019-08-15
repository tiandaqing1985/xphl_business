package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.ywArrearage.CustomerArrearageGather;
import com.ruoyi.system.domain.ywArrearage.SaleManagerArrearageGather;
import com.ruoyi.system.domain.ywArrearage.YwArrearage;
import java.util.List;

/**
 * 商机-欠款 服务层
 * 
 * @author ruoyi
 * @date 2019-08-14
 */
public interface IYwArrearageService 
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
     * 删除商机-欠款信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwArrearageByIds(String ids);

	/**
	 * 导入商机-欠款信息
	 *
	 * @param ywArrearageList 批量文件中的每条记录
	 * @return 结果
	 */
	public String importYwArrearages(List<YwArrearage> ywArrearageList,boolean updateSupport,String operName);

	/**
	 * 查询汇总表-按销售经理
	 * @param ywArrearage 查询条件
	 * @return
	 */
    public List<SaleManagerArrearageGather> selectGatherSaleManager(YwArrearage ywArrearage, SysUser loginUser);

	/**
	 * 查询汇总表-按客户
	 *
	 * @param ywArrearage 查询条件
	 * @return
	 */
	public List<CustomerArrearageGather> selectGatherCustomer(YwArrearage ywArrearage);

}
