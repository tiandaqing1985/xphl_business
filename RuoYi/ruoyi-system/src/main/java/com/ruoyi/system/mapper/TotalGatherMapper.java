package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwTotalGrossGather;

import java.util.List;

public interface TotalGatherMapper {

    /**
     * 根据部门汇总所有消耗毛利情况
     * @return
     */
    public List<YwTotalGrossGather> selectTotalGather(YwTotalGrossGather ywTotalGather);
    /**
     * 查询消耗排名
     *
     * @return 消耗排名
     */
    public List<YwGatherConsumption> selectRankConsumptionList(YwGatherConsumption ywGatherConsumption);

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    public List<YwRankGrossMargin> selectRankGrossMarginList(YwRankGrossMargin ywRankGrossMargin);

}
