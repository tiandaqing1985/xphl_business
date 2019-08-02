package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GatherMapper;
import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.service.IGatherService;

/**
 * 消耗毛利汇总 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class GatherServiceImpl implements IGatherService 
{
	@Autowired
	private GatherMapper gatherMapper;

	/**
	 * 查询消耗汇总列表
	 *
	 * @param gather 消耗毛利汇总信息
	 * @return 消耗毛利汇总集合
	 */
	@Override
	public List<Gather> selectGatherList(Gather gather)
	{
		return gatherMapper.selectGatherList(gather);
	}

}
