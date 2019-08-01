package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.YwTask;
import java.util.List;	

/**
 * 任务 数据层
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public interface YwTaskMapper 
{
	/**
     * 查询任务信息
     * 
     * @param id 任务ID
     * @return 任务信息
     */
	public YwTask selectYwTaskById(Long id);
	
	/**
     * 查询任务列表
     * 
     * @param ywTask 任务信息
     * @return 任务集合
     */
	public List<YwTask> selectYwTaskList(YwTask ywTask);
	
	/**
     * 新增任务
     * 
     * @param ywTask 任务信息
     * @return 结果
     */
	public int insertYwTask(YwTask ywTask);
	
	/**
     * 修改任务
     * 
     * @param ywTask 任务信息
     * @return 结果
     */
	public int updateYwTask(YwTask ywTask);
	
	/**
     * 删除任务
     * 
     * @param id 任务ID
     * @return 结果
     */
	public int deleteYwTaskById(Long id);
	
	/**
     * 批量删除任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwTaskByIds(String[] ids);
	
}