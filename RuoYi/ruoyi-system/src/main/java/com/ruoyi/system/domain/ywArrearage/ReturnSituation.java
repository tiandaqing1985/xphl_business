package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 回款情况
 */
public class ReturnSituation {

    @Excel(name="回款类型",type = Excel.Type.EXPORT)
    private String returnType;

    @Excel(name="回款金额",type = Excel.Type.IMPORT)
    private BigDecimal returnAmt;

    @Excel(name="网银回款金额",type = Excel.Type.IMPORT)
    private BigDecimal returnAmtByEBank;

    @Excel(name="保证金转执行款金额",type = Excel.Type.IMPORT)
    private BigDecimal bzj2zxkAmt;

    @Excel(name="客户返点抵扣回款金额",type = Excel.Type.IMPORT)
    private BigDecimal rebateReturnAmt;

    @Excel(name="客户退款抵扣回款金额",type = Excel.Type.IMPORT)
    private BigDecimal refundReturnAmt;

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public BigDecimal getReturnAmtByEBank() {
        return returnAmtByEBank;
    }

    public void setReturnAmtByEBank(BigDecimal returnAmtByEBank) {
        this.returnAmtByEBank = returnAmtByEBank;
    }

    public BigDecimal getBzj2zxkAmt() {
        return bzj2zxkAmt;
    }

    public void setBzj2zxkAmt(BigDecimal bzj2zxkAmt) {
        this.bzj2zxkAmt = bzj2zxkAmt;
    }

    public BigDecimal getRebateReturnAmt() {
        return rebateReturnAmt;
    }

    public void setRebateReturnAmt(BigDecimal rebateReturnAmt) {
        this.rebateReturnAmt = rebateReturnAmt;
    }

    public BigDecimal getRefundReturnAmt() {
        return refundReturnAmt;
    }

    public void setRefundReturnAmt(BigDecimal refundReturnAmt) {
        this.refundReturnAmt = refundReturnAmt;
    }
}
