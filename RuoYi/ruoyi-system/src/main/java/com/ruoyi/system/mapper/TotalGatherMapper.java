package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import com.ruoyi.system.domain.YwTotalGather;

import java.util.List;

public interface TotalGatherMapper {

    /**
     * 根据部门汇总所有消耗毛利情况
     * @return
     */
    public List<YwTotalGather> selectTotalGather();
    /**
     * 查询消耗排名
     *
     * @return 消耗排名
     */
    public List<YwGatherConsumption> selectRankConsumptionList();

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    public List<YwGatherGrossMargin> selectRankGrossMarginList();

}
