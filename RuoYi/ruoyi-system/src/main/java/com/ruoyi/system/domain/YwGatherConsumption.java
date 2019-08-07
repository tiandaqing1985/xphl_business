package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 消耗任务完成比例排名
 */
public class YwGatherConsumption {
    /** 业绩消耗排名 */
    @Excel(name="业绩消耗排名",type = Excel.Type.EXPORT)
    private Integer rownum;
    /** 销售经理 */
    @Excel(name="销售经理",type = Excel.Type.EXPORT)
    private String saleManager;
    /** 业绩消耗总任务 */
    @Excel(name="业绩消耗总任务",type = Excel.Type.EXPORT)
    private BigDecimal quotas;
    /** 业绩消耗完成 */
    @Excel(name="业绩消耗完成",type = Excel.Type.EXPORT)
    private BigDecimal summation;
    /** 业绩消耗完成率 */
    @Excel(name="业绩消耗完成率",type = Excel.Type.EXPORT)
    private String rate;
    /** 期间 */
    private String quarter;

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

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

    public BigDecimal getSummation() {
        return summation;
    }

    public void setSummation(BigDecimal summation) {
        this.summation = summation;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
