package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwTract;
import java.util.List;

/**
 * 跟进 服务层
 * 
 * @author ruoyi
 * @date 2019-06-11
 */
public interface IYwTractService 
{
	/**
     * 查询跟进信息
     * 
     * @param id 跟进ID
     * @return 跟进信息
     */
	public YwTract selectYwTractById(Long id);
	
	/**
     * 查询跟进列表
     * 
     * @param ywTract 跟进信息
     * @return 跟进集合
     */
	public List<YwTract> selectYwTractList(YwTract ywTract);
	
	/**
     * 新增跟进
     * 
     * @param ywTract 跟进信息
     * @return 结果
     */
	public int insertYwTract(YwTract ywTract);
	
	/**
     * 修改跟进
     * 
     * @param ywTract 跟进信息
     * @return 结果
     */
	public int updateYwTract(YwTract ywTract);
		
	/**
     * 删除跟进信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwTractByIds(String ids);
	
}
