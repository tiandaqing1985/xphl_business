package com.ruoyi.system.service;

import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;

import java.util.List;

/**
 * 消耗毛利汇总 服务层
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public interface IGatherService 
{

	/**
	 * 查询毛利消耗汇总列表
	 *
	 * @param gather 消耗毛利汇总信息
	 * @return 消耗毛利汇总集合
	 */
	public List<Gather> selectGatherList(Gather gather);

}
