package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 各个部门消耗毛利情况汇总
 */
public class YwTotalGrossGather {

    /**
     * 期间
     */
    private String quarter;
    /**
     * 考核期间
     */
    private String term;
    /**
     * 部门
     */
    @Excel(name = "部门", type = Excel.Type.EXPORT)
    private String deptName;
    /**
     * 媒体
     */
    @Excel(name = "购买媒体", type = Excel.Type.EXPORT)
    private String media;
    /**
     * 毛利任务
     */
    @Excel(name = "毛利任务", type = Excel.Type.EXPORT)
    private BigDecimal quotas;
    /**
     * 毛利完成金额
     */
    @Excel(name = "毛利完成金额", type = Excel.Type.EXPORT)
    private BigDecimal grossMargin;
    /**
     * 平推毛利任务完成金额
     */
    @Excel(name = "平推毛利任务完成金额", type = Excel.Type.EXPORT)
    private BigDecimal mlptAmt;
    /**
     * 实际毛利完成老率
     */
    @Excel(name = "实际毛利完成老率", type = Excel.Type.EXPORT)
    private String mlwcRate;
    /**
     * 时间进度
     */
    @Excel(name = "时间进度", type = Excel.Type.EXPORT)
    private String timeSchedule;

    public String getQuarter() {
        return quarter;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    public BigDecimal getMlptAmt() {
        return mlptAmt;
    }

    public void setMlptAmt(BigDecimal mlptAmt) {
        this.mlptAmt = mlptAmt;
    }

    public String getMlwcRate() {
        return mlwcRate;
    }

    public void setMlwcRate(String mlwcRate) {
        this.mlwcRate = mlwcRate;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }
}
