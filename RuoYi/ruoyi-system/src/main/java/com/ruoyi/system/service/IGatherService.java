package com.ruoyi.system.service;

import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.domain.YwGrossMarginGather;
import com.ruoyi.system.domain.YwRankGrossMargin;

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

	/**
	 *	处理得到导出的list列表
	 *
	 * @param gathers 消耗毛利汇总list
	 * @return 消耗毛利汇总集合
	 */
	public List<Gather> exportList(List<Gather> gathers);

	/**
	 *	查询毛利汇总列表
	 *
	 * @param gather 毛利汇总查询条件
	 * @return 消耗毛利集合
	 */
    public List<YwGrossMarginGather> selectGrossMarginGatherList(YwGrossMarginGather gather);
}
