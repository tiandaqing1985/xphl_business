package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 毛利个人汇总
 */
public class YwGrossMarginGather {

    @Excel(name = "区域",type = Excel.Type.EXPORT)
    private String area;

    @Excel(name = "部门",type = Excel.Type.EXPORT)
    private String deptName;

    @Excel(name = "销售经理", type = Excel.Type.EXPORT)
    private String saleManager;//销售经理

    @Excel(name = "购买媒体",type = Excel.Type.EXPORT)
    private String media;

    @Excel(name = "毛利季度任务", type = Excel.Type.EXPORT)
    private BigDecimal quotas;//毛利任务

    @Excel(name = "毛利完成金额", type = Excel.Type.EXPORT)
    private BigDecimal grossMargin;//毛利任务

    @Excel(name = "平推毛利完成金额", type = Excel.Type.EXPORT)
    private BigDecimal mlptAmt;//毛利任务

    @Excel(name = "时间进度", type = Excel.Type.EXPORT)
    private String timeSchedule;//毛利任务

    @Excel(name = "目前毛利完成率", type = Excel.Type.EXPORT)
    private String mlwcRate;//毛利任务

    private String term;//期间

    private String quarter;//期间

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
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

    public BigDecimal getMlptAmt() {
        return mlptAmt;
    }

    public void setMlptAmt(BigDecimal mlptAmt) {
        this.mlptAmt = mlptAmt;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public String getMlwcRate() {
        return mlwcRate;
    }

    public void setMlwcRate(String mlwcRate) {
        this.mlwcRate = mlwcRate;
    }
}
