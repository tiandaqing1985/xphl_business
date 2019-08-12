package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwTotalConsumGather;
import com.ruoyi.system.domain.YwTotalGrossGather;
import com.ruoyi.system.mapper.TotalGatherMapper;
import com.ruoyi.system.service.TotalGatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 消耗毛利汇总 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class TotalGatherServiceImpl implements TotalGatherService {
    @Autowired
    private TotalGatherMapper totalGatherMapper;

    /**
     * 查询消耗汇总列表
     *
     * @return 消耗毛利汇总集合
     */
    @Override
    public List<YwTotalConsumGather> selectTotalConsumGather(YwTotalConsumGather ywTotalGather) {

        List<YwTotalConsumGather> ywTotalGathers = totalGatherMapper.selectTotalGather(ywTotalGather);
        //消除科学计数法
        for (YwTotalConsumGather totalGather : ywTotalGathers) {
            totalGather.setQuotas(new BigDecimal(totalGather.getQuotas().toPlainString()));
            totalGather.setSummation(new BigDecimal(totalGather.getSummation().toPlainString()));
        }

        return ywTotalGathers;
    }

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    @Override
    public List<YwRankGrossMargin> selectRankGrossMarginList(YwRankGrossMargin yw) {
        List<YwRankGrossMargin> ywRankGrossMargins = totalGatherMapper.selectRankGrossMarginList(yw);
        for (YwRankGrossMargin ywRankGrossMargin : ywRankGrossMargins) {
            ywRankGrossMargin.setGrossMargin(new BigDecimal(ywRankGrossMargin.getGrossMargin().toPlainString()));
            ywRankGrossMargin.setQuotas(new BigDecimal(ywRankGrossMargin.getQuotas().toPlainString()));
        }
        return ywRankGrossMargins;
    }

    /**
     * 查询消耗排名
     *
     * @return 消耗排名
     */
    @Override
    public List<YwGatherConsumption> selectRankConsumptionlist(YwGatherConsumption yw) {
        List<YwGatherConsumption> ywGatherConsumptions = totalGatherMapper.selectRankConsumptionList(yw);
        for (YwGatherConsumption gatherConsumption : ywGatherConsumptions) {
            gatherConsumption.setQuotas(new BigDecimal(gatherConsumption.getQuotas().toPlainString()));
            gatherConsumption.setSummation(new BigDecimal(gatherConsumption.getSummation().toPlainString()));
        }
        return ywGatherConsumptions;
    }
}
