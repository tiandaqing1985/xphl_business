package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import com.ruoyi.system.domain.YwTotalGather;
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
    public List<YwTotalGather> selectTotalGather(YwTotalGather ywTotalGather) {

        List<YwTotalGather> ywTotalGathers = totalGatherMapper.selectTotalGather(ywTotalGather);
        //消除科学计数法
        for (YwTotalGather totalGather : ywTotalGathers) {
            totalGather.setQuotasConsumption(new BigDecimal(totalGather.getQuotasConsumption().toPlainString()));
            totalGather.setQuotasGrossMargin(new BigDecimal(totalGather.getQuotasGrossMargin().toPlainString()));
            totalGather.setTotalGrossMargin(new BigDecimal(totalGather.getTotalGrossMargin().toPlainString()));
            totalGather.setTotalSummation(new BigDecimal(totalGather.getTotalSummation().toPlainString()));
        }

        return ywTotalGathers;
    }

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    @Override
    public List<YwGatherGrossMargin> selectRankGrossMarginList(YwGatherGrossMargin yw) {
        List<YwGatherGrossMargin> ywGatherGrossMargins = totalGatherMapper.selectRankGrossMarginList(yw);
        for (YwGatherGrossMargin ywGatherGrossMargin : ywGatherGrossMargins) {
            ywGatherGrossMargin.setGrossMargin(new BigDecimal(ywGatherGrossMargin.getGrossMargin().toPlainString()));
            ywGatherGrossMargin.setQuotas(new BigDecimal(ywGatherGrossMargin.getQuotas().toPlainString()));
        }
        return ywGatherGrossMargins;
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
