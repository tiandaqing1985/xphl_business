package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 各个部门消耗情况汇总
 */
public class YwTotalConsumGather {

    /** 期间 */
    private String quarter;
    /** 部门 */
    @Excel(name = "部门",type = Excel.Type.EXPORT)
    private String deptName;
    /** 媒体 */
    @Excel(name = "购买媒体",type = Excel.Type.EXPORT)
    private String media;
    /** 实际任务 */
    @Excel(name = "实际任务",type = Excel.Type.EXPORT)
    private BigDecimal quotas;
    /** 消耗完成金额 */
    @Excel(name = "消耗完成金额",type = Excel.Type.EXPORT)
    private BigDecimal summation;
    /** 平推任务完成金额 */
    @Excel(name = "平推任务完成金额",type = Excel.Type.EXPORT)
    private BigDecimal xhptAmt;
    /** 消耗完成率 */
    @Excel(name = "消耗完成率",type = Excel.Type.EXPORT)
    private String xhwcRate;
    /** 时间进度 */
    @Excel(name = "时间进度",type = Excel.Type.EXPORT)
    private String timeSchedule;

    public String getQuarter() {
        return quarter;
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

    public BigDecimal getSummation() {
        return summation;
    }

    public void setSummation(BigDecimal summation) {
        this.summation = summation;
    }

    public BigDecimal getXhptAmt() {
        return xhptAmt;
    }

    public void setXhptAmt(BigDecimal xhptAmt) {
        this.xhptAmt = xhptAmt;
    }

    public String getXhwcRate() {
        return xhwcRate;
    }

    public void setXhwcRate(String xhwcRate) {
        this.xhwcRate = xhwcRate;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }
}
