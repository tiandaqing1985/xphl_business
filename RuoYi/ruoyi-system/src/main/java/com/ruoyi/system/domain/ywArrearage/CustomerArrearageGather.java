package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 以客户为维度 欠款的汇总表
 */
public class CustomerArrearageGather {

    @Excel(name = "区域", type = Excel.Type.EXPORT)
    private String area;

    @Excel(name = "所属部门", type = Excel.Type.EXPORT)
    private String deptName;

    @Excel(name = "销售经理", type = Excel.Type.EXPORT)
    private String saleManager;

    @Excel(name = "签约方", type = Excel.Type.EXPORT)
    private String signatory;

    @Excel(name = "广告主", type = Excel.Type.EXPORT)
    private String advertiser;

    @Excel(name = "月初应收金额", type = Excel.Type.EXPORT)
    private BigDecimal firstDueAmt;

    @Excel(name = "实时应收金额", type = Excel.Type.EXPORT)
    private BigDecimal dueAmt;

    @Excel(name = "未到账期未收款", type = Excel.Type.EXPORT)
    private BigDecimal notReceiveAmt;

    @Excel(name = "实时客户已逾期款", type = Excel.Type.EXPORT)
    private BigDecimal overdueAmt;

    @Excel(name = "预计回款金额（当月）", type = Excel.Type.EXPORT)
    private BigDecimal planReturnAmt;

    @Excel(name = "预计回款金额（可能性为高）", type = Excel.Type.EXPORT)
    private BigDecimal planReturnAmtH;

    @Excel(name = "预计回款金额（可能性为中）", type = Excel.Type.EXPORT)
    private BigDecimal planReturnAmtM;

    @Excel(name = "预计回款金额（可能性为低）", type = Excel.Type.EXPORT)
    private BigDecimal planReturnAmtL;

    @Excel(name = "实际回款金额", type = Excel.Type.EXPORT)
    private BigDecimal realReturnAmt;

    @Excel(name = "预计应收账款回款率", type = Excel.Type.EXPORT)
    private String planReturnRate;

    @Excel(name = "实际应收账款回款率", type = Excel.Type.EXPORT)
    private String realReturnRate;

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

    public String getSignatory() {
        return signatory;
    }

    public void setSignatory(String signatory) {
        this.signatory = signatory;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
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

    public BigDecimal getNotReceiveAmt() {
        return notReceiveAmt;
    }

    public void setNotReceiveAmt(BigDecimal notReceiveAmt) {
        this.notReceiveAmt = notReceiveAmt;
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

    public BigDecimal getPlanReturnAmtH() {
        return planReturnAmtH;
    }

    public void setPlanReturnAmtH(BigDecimal planReturnAmtH) {
        this.planReturnAmtH = planReturnAmtH;
    }

    public BigDecimal getPlanReturnAmtM() {
        return planReturnAmtM;
    }

    public void setPlanReturnAmtM(BigDecimal planReturnAmtM) {
        this.planReturnAmtM = planReturnAmtM;
    }

    public BigDecimal getPlanReturnAmtL() {
        return planReturnAmtL;
    }

    public void setPlanReturnAmtL(BigDecimal planReturnAmtL) {
        this.planReturnAmtL = planReturnAmtL;
    }

    public BigDecimal getRealReturnAmt() {
        return realReturnAmt;
    }

    public void setRealReturnAmt(BigDecimal realReturnAmt) {
        this.realReturnAmt = realReturnAmt;
    }

    public String getPlanReturnRate() {
        return planReturnRate;
    }

    public void setPlanReturnRate(String planReturnRate) {
        this.planReturnRate = planReturnRate;
    }

    public String getRealReturnRate() {
        return realReturnRate;
    }

    public void setRealReturnRate(String realReturnRate) {
        this.realReturnRate = realReturnRate;
    }
}
