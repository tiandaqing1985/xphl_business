package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;

/**
 * 实际应收账款回款率排名
 */
public class ReturnRateRank {

    @Excel(name = "排名", type = Excel.Type.EXPORT)
    private String rank;

    @Excel(name = "销售经理", type = Excel.Type.EXPORT)
    private String saleManager;

    @Excel(name = "实际应收账款回款率", type = Excel.Type.EXPORT)
    private String realReturnRate;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public String getRealReturnRate() {
        return realReturnRate;
    }

    public void setRealReturnRate(String realReturnRate) {
        this.realReturnRate = realReturnRate;
    }
}
