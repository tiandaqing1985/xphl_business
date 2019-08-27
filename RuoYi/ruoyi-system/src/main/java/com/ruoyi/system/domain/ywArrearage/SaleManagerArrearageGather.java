package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 以销售经理为维度 欠款的汇总表
 */
public class SaleManagerArrearageGather {

    @Excel(name = "区域", type = Excel.Type.EXPORT)
    private String area;

    @Excel(name = "所属部门", type = Excel.Type.EXPORT)
    private String deptName;

    @Excel(name = "销售经理", type = Excel.Type.EXPORT)
    private String saleManager;

    @Excel(name = "月初应收金额", type = Excel.Type.EXPORT)
    private BigDecimal firstDueAmt;

    @Excel(name = "实时应收金额", type = Excel.Type.EXPORT)
    private BigDecimal dueAmt;

    @Excel(name = "实时客户已逾期款", type = Excel.Type.EXPORT)
    private BigDecimal overdueAmt;

    @Excel(name = "预计回款金额（可能性高）", type = Excel.Type.EXPORT)
    private BigDecimal planReturnAmt;

    @Excel(name = "预计应收账款回款率", type = Excel.Type.EXPORT)
    private String planReturnRate;

    @Excel(name = "实际回款金额", type = Excel.Type.EXPORT)
    private BigDecimal realReturnAmt;

    @Excel(name = "实际应收账款回款率", type = Excel.Type.EXPORT)
    private String realReturnRate;

    private BigDecimal realReturnAmtNot;//不含非当期的实际回款金额

    public BigDecimal getRealReturnAmtNot() {
        return realReturnAmtNot;
    }

    public void setRealReturnAmtNot(BigDecimal realReturnAmtNot) {
        this.realReturnAmtNot = realReturnAmtNot;
    }

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

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public BigDecimal getFirstDueAmt() {
        return firstDueAmt;
    }

    public void setFirstDueAmt(BigDecimal firstDueAmt) {
        this.firstDueAmt = firstDueAmt;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public BigDecimal getPlanReturnAmt() {
        return planReturnAmt;
    }

    public void setPlanReturnAmt(BigDecimal planReturnAmt) {
        this.planReturnAmt = planReturnAmt;
    }

    public String getPlanReturnRate() {
        return planReturnRate;
    }

    public void setPlanReturnRate(String planReturnRate) {
        this.planReturnRate = planReturnRate;
    }

    public BigDecimal getRealReturnAmt() {
        return realReturnAmt;
    }

    public void setRealReturnAmt(BigDecimal realReturnAmt) {
        this.realReturnAmt = realReturnAmt;
    }

    public String getRealReturnRate() {
        return realReturnRate;
    }

    public void setRealReturnRate(String realReturnRate) {
        this.realReturnRate = realReturnRate;
    }
}
