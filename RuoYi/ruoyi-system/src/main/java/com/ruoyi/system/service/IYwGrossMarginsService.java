package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwGrossMargins;
import java.util.List;

/**
 * 毛利情况 服务层
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public interface IYwGrossMarginsService 
{
	/**
     * 查询毛利情况信息
     * 
     * @param id 毛利情况ID
     * @return 毛利情况信息
     */
	public YwGrossMargins selectYwGrossMarginsById(Long id);
	
	/**
     * 查询毛利情况列表
     * 
     * @param ywGrossMargins 毛利情况信息
     * @return 毛利情况集合
     */
	public List<YwGrossMargins> selectYwGrossMarginsList(YwGrossMargins ywGrossMargins);
	
	/**
     * 新增毛利情况
     * 
     * @param ywGrossMargins 毛利情况信息
     * @return 结果
     */
	public int insertYwGrossMargins(YwGrossMargins ywGrossMargins);
	
	/**
     * 修改毛利情况
     * 
     * @param ywGrossMargins 毛利情况信息
     * @return 结果
     */
	public int updateYwGrossMargins(YwGrossMargins ywGrossMargins);
		
	/**
     * 删除毛利情况信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteYwGrossMarginsByIds(String ids);

	/**
	 * 批量插入
	 * @param grossMarginList 没调毛利记录
	 * @param updateSupport 是否支持更新
	 * @param operName 当前登陆用户
	 * @return
	 */
	public String importYwGrossMargins(List<YwGrossMargins> grossMarginList, boolean updateSupport, String operName);
}
