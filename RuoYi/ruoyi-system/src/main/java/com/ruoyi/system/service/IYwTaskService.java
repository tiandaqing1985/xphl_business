package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwTask;
import java.util.List;

/**
 * 任务 服务层
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public interface IYwTaskService 
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
     * 删除任务信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwTaskByIds(String ids);

	/**
	 * 导入任务
	 * @param ywTasks
	 * @param updateSupport
	 * @param operName
	 * @return
	 */
    public String importYwTask(List<YwTask> ywTasks, boolean updateSupport, String operName);
}
