package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 汇总表--消耗
 */
public class SumConsum {

    /** 部门 */
    @Excel(name="部门",type = Excel.Type.EXPORT)
    private String area;
    /** 销售经理 */
    @Excel(name = "销售经理", type = Excel.Type.EXPORT)
    private String salesManager;
    /** 购买媒体 */
    @Excel(name = "购买媒体", type = Excel.Type.EXPORT)
    private String media;
    /** 2019年Q2消耗任务 */
    @Excel(name="2019年Q2消耗任务",type = Excel.Type.EXPORT)
    private BigDecimal quotas;
    /** 完成消耗金额 */
    @Excel(name="完成消耗金额",type = Excel.Type.EXPORT)
    private BigDecimal summation;
    /** 消耗平推完成金额 */
    @Excel(name="消耗平推完成金额",type = Excel.Type.EXPORT)
    private BigDecimal xhptAmt;
    /** 时间进度 */
    @Excel(name="时间进度",type = Excel.Type.EXPORT)
    private String timeSchedule;
    /** 消耗完成率 */
    @Excel(name="消耗完成率",type = Excel.Type.EXPORT)
    private String xhwcRate;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSalesManager() {
        return salesManager;
    }

    public void setSalesManager(String salesManager) {
        this.salesManager = salesManager;
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

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public String getXhwcRate() {
        return xhwcRate;
    }

    public void setXhwcRate(String xhwcRate) {
        this.xhwcRate = xhwcRate;
    }
}
