package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwArrearage;
import java.util.List;

/**
 * 商机-欠款 服务层
 * 
 * @author ruoyi
 * @date 2019-07-30
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
	
	
	
	public String importArrearage(List<YwArrearage> ywArrearageList, Boolean isUpdateSupport, String operName);
	
	public List<YwArrearage> selectSumList(YwArrearage ywArrearage);
	
	
}
