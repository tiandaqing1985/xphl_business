package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import com.ruoyi.system.domain.YwTotalGather;

import java.util.List;

/**
 * 服务层 部门汇总
 */
public interface TotalGatherService {
    public List<YwTotalGather> selectTotalGather(YwTotalGather ywTotalGather);

    public List<YwGatherGrossMargin> selectRankGrossMarginList(YwGatherGrossMargin ywGatherGrossMargin);

    public List<YwGatherConsumption> selectRankConsumptionlist(YwGatherConsumption ywGatherConsumption);
}
