package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 毛利任务完成比例排名
 */
public class YwGatherGrossMargin {

    /** 毛利排名 */
    @Excel(name = "毛利排名",type = Excel.Type.EXPORT)
    private Integer rownum;
    /** 销售经理 */
    @Excel(name = "销售经理",type = Excel.Type.EXPORT)
    private String saleManager;
    /** 毛利任务 */
    @Excel(name = "毛利任务",type = Excel.Type.EXPORT)
    private BigDecimal quotas;
    /** 毛利实际完成 */
    @Excel(name = "毛利实际完成",type = Excel.Type.EXPORT)
    private BigDecimal grossMargin;
    /** 毛利完成率 */
    @Excel(name = "毛利完成率",type = Excel.Type.EXPORT)
    private String rate;

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public BigDecimal getQuotas() {
        return quotas;
    }

    public void setQuotas(BigDecimal quotas) {
        this.quotas = quotas;
    }

    public BigDecimal getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(BigDecimal grossMargin) {
        this.grossMargin = grossMargin;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
