package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import com.ruoyi.system.domain.YwTotalGather;
import com.ruoyi.system.mapper.TotalGatherMapper;
import com.ruoyi.system.service.TotalGatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消耗毛利汇总 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class TotalGatherServiceImpl implements TotalGatherService
{
	@Autowired
	private TotalGatherMapper totalGatherMapper;

	/**
	 * 查询消耗汇总列表
	 *
	 * @return 消耗毛利汇总集合
	 */
	@Override
	public List<YwTotalGather> selectTotalGather(YwTotalGather ywTotalGather)
	{
		return totalGatherMapper.selectTotalGather(ywTotalGather);
	}

	/**
     * 查询毛利排名
	 * @return 毛利排名
     */
	@Override
	public List<YwGatherGrossMargin> selectRankGrossMarginList() {
		return totalGatherMapper.selectRankGrossMarginList();
	}

	/**
     * 查询消耗排名
	 * @return 消耗排名
	 */
	@Override
	public List<YwGatherConsumption> selectRankConsumptionlist() {
		return totalGatherMapper.selectRankConsumptionList();
	}
}
