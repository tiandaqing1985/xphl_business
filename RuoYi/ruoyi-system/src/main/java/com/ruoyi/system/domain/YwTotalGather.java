package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 各个部门消耗毛利情况汇总
 */
public class YwTotalGather {

    /** 部门 */
    @Excel(name = "部门",type = Excel.Type.EXPORT)
    private String deptName;
    /** 毛利任务 */
    @Excel(name = "毛利任务",type = Excel.Type.EXPORT)
    private BigDecimal quotasGrossMargin;
    /** 消耗任务 */
    @Excel(name = "消耗任务",type = Excel.Type.EXPORT)
    private BigDecimal quotasConsumption;
    /** 消耗完成金额 */
    @Excel(name = "消耗完成金额",type = Excel.Type.EXPORT)
    private BigDecimal totalSummation;
    /** 毛利完成金额 */
    @Excel(name = "毛利完成金额",type = Excel.Type.EXPORT)
    private BigDecimal totalGrossMargin;
    /** 消耗完成率 */
    @Excel(name = "消耗完成率",type = Excel.Type.EXPORT)
    private String consumptionRate;
    /** 毛利完成率 */
    @Excel(name = "毛利完成率",type = Excel.Type.EXPORT)
    private String grossMarginRate;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getQuotasConsumption() {
        return quotasConsumption;
    }

    public void setQuotasConsumption(BigDecimal quotasConsumption) {
        this.quotasConsumption = quotasConsumption;
    }

    public BigDecimal getQuotasGrossMargin() {
        return quotasGrossMargin;
    }

    public void setQuotasGrossMargin(BigDecimal quotasGrossMargin) {
        this.quotasGrossMargin = quotasGrossMargin;
    }

    public BigDecimal getTotalSummation() {
        return totalSummation;
    }

    public void setTotalSummation(BigDecimal totalSummation) {
        this.totalSummation = totalSummation;
    }

    public BigDecimal getTotalGrossMargin() {
        return totalGrossMargin;
    }

    public void setTotalGrossMargin(BigDecimal totalGrossMargin) {
        this.totalGrossMargin = totalGrossMargin;
    }

    public String getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(String consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    public String getGrossMarginRate() {
        return grossMarginRate;
    }

    public void setGrossMarginRate(String grossMarginRate) {
        this.grossMarginRate = grossMarginRate;
    }
}
