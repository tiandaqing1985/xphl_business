package com.ruoyi.system.service;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwTotalGrossGather;

import java.util.List;

/**
 * 服务层 部门汇总
 */
public interface TotalGatherService {
    public List<YwTotalGrossGather> selectTotalGather(YwTotalGrossGather ywTotalGather);

    public List<YwRankGrossMargin> selectRankGrossMarginList(YwRankGrossMargin ywRankGrossMargin);

    public List<YwGatherConsumption> selectRankConsumptionlist(YwGatherConsumption ywGatherConsumption);
}
