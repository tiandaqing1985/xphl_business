package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

/**
 * 回款情况
 */
public class ReturnSituation {

    @Excel(name = "回款类型", type = Excel.Type.EXPORT)
    private String returnType;

    @Excel(name = "回款金额", type = Excel.Type.EXPORT)
    private BigDecimal returnAmt;

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
}
