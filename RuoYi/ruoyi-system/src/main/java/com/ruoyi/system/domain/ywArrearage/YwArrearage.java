package com.ruoyi.system.domain.ywArrearage;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 商机-欠款表 yw_arrearage
 *
 * @author ruoyi
 * @date 2019-08-14
 */
public class YwArrearage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;
    /**
     * 合同类型
     */
    @Excel(name = "合同类型", type = Excel.Type.IMPORT)
    private String contractType;
    /**
     * 合同编号
     */
    @Excel(name = "合同编号", type = Excel.Type.IMPORT)
    private String contractNo;
    /**
     * 排期编号
     */
    @Excel(name = "排期编号", type = Excel.Type.IMPORT)
    private String scheduleNo;
    /**
     * 款项编号
     */
    @Excel(name = "款项编号", type = Excel.Type.IMPORT)
    private String fundNo;
    /**
     * 我方签约公司
     */
    @Excel(name = "我方签约公司", type = Excel.Type.IMPORT)
    private String contractCompany;
    /**
     * 签约方
     */
    @Excel(name = "签约方", type = Excel.Type.IMPORT)
    private String signatory;
    /**
     * 广告主
     */
    @Excel(name = "广告主", type = Excel.Type.IMPORT)
    private String advertiser;
    /**
     * 购买媒体
     */
    @Excel(name = "购买媒体", type = Excel.Type.IMPORT)
    private String media;
    /**
     * 区域
     */
    @Excel(name = "区域", type = Excel.Type.IMPORT)
    private String area;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", type = Excel.Type.IMPORT)
    private String deptName;
    /**
     * 销售经理
     */
    @Excel(name = "销售经理", type = Excel.Type.IMPORT)
    private String saleManager;
    /**
     * 销售助理
     */
    @Excel(name = "销售助理", type = Excel.Type.IMPORT)
    private String saleAssistant;
    /**
     * 应收款日期
     */
    @Excel(name = "应收款日期", type = Excel.Type.IMPORT)
    private String dueDate;
    /**
     * 月初应收账款金额
     */
    @Excel(name = "月初应收账款金额", type = Excel.Type.IMPORT)
    private BigDecimal firstDueAmt;
    /**
     * 实时应收金额
     */
    @Excel(name = "实时应收金额", type = Excel.Type.IMPORT)
    private BigDecimal dueAmt;
    /**
     * 未到账期未收款
     */
    @Excel(name = "未到账期未收款", type = Excel.Type.IMPORT)
    private BigDecimal notReceiveAmt;
    /**
     * 实时客户已逾期款
     */
    @Excel(name = "实时客户已逾期款", type = Excel.Type.IMPORT)
    private BigDecimal overdueAmt;
    /**
     * 逾期天数
     */
    @Excel(name = "逾期天数", type = Excel.Type.IMPORT)
    private String overdueDayNum;
    /**
     * 预计回款时间
     */
    @Excel(name = "预计回款时间", type = Excel.Type.IMPORT)
    private String planReturnDate;
    /**
     * 预计回款金额
     */
    @Excel(name = "预计回款金额", type = Excel.Type.IMPORT)
    private BigDecimal planReturnAmt;
    /**
     * 回款可能性
     */
    @Excel(name = "回款可能性", type = Excel.Type.IMPORT)
    private String returnProb;
    /**
     * 实际回款金额
     */
    @Excel(name = "实际回款金额", type = Excel.Type.IMPORT)
    private BigDecimal realReturnAmt;
    /**
     * 回款类型
     */
    @Excel(name = "回款类型", type = Excel.Type.IMPORT)
    private String returnType;

    //查询条件 应收款日期,
    private String dueDateStart;
    private String dueDateEnd;

    public String getDueDateStart() {
        return dueDateStart;
    }

    public void setDueDateStart(String dueDateStart) {
        this.dueDateStart = dueDateStart;
    }

    public String getDueDateEnd() {
        return dueDateEnd;
    }

    public void setDueDateEnd(String dueDateEnd) {
        this.dueDateEnd = dueDateEnd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setFundNo(String fundNo) {
        this.fundNo = fundNo;
    }

    public String getFundNo() {
        return fundNo;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setSignatory(String signatory) {
        this.signatory = signatory;
    }

    public String getSignatory() {
        return signatory;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setSaleManager(String saleManager) {
        this.saleManager = saleManager;
    }

    public String getSaleManager() {
        return saleManager;
    }

    public void setSaleAssistant(String saleAssistant) {
        this.saleAssistant = saleAssistant;
    }

    public String getSaleAssistant() {
        return saleAssistant;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setFirstDueAmt(BigDecimal firstDueAmt) {
        this.firstDueAmt = firstDueAmt;
    }

    public BigDecimal getFirstDueAmt() {
        return firstDueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setNotReceiveAmt(BigDecimal notReceiveAmt) {
        this.notReceiveAmt = notReceiveAmt;
    }

    public BigDecimal getNotReceiveAmt() {
        return notReceiveAmt;
    }

    public void setOverdueAmt(BigDecimal overdueAmt) {
        this.overdueAmt = overdueAmt;
    }

    public BigDecimal getOverdueAmt() {
        return overdueAmt;
    }

    public void setOverdueDayNum(String overdueDayNum) {
        this.overdueDayNum = overdueDayNum;
    }

    public String getOverdueDayNum() {
        return overdueDayNum;
    }

    public void setPlanReturnDate(String planReturnDate) {
        this.planReturnDate = planReturnDate;
    }

    public String getPlanReturnDate() {
        return planReturnDate;
    }

    public void setPlanReturnAmt(BigDecimal planReturnAmt) {
        this.planReturnAmt = planReturnAmt;
    }

    public BigDecimal getPlanReturnAmt() {
        return planReturnAmt;
    }

    public void setReturnProb(String returnProb) {
        this.returnProb = returnProb;
    }

    public String getReturnProb() {
        return returnProb;
    }

    public void setRealReturnAmt(BigDecimal realReturnAmt) {
        this.realReturnAmt = realReturnAmt;
    }

    public BigDecimal getRealReturnAmt() {
        return realReturnAmt;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("contractType", getContractType())
                .append("contractNo", getContractNo())
                .append("scheduleNo", getScheduleNo())
                .append("fundNo", getFundNo())
                .append("contractCompany", getContractCompany())
                .append("signatory", getSignatory())
                .append("advertiser", getAdvertiser())
                .append("media", getMedia())
                .append("area", getArea())
                .append("deptName", getDeptName())
                .append("saleManager", getSaleManager())
                .append("saleAssistant", getSaleAssistant())
                .append("dueDate", getDueDate())
                .append("firstDueAmt", getFirstDueAmt())
                .append("dueAmt", getDueAmt())
                .append("notReceiveAmt", getNotReceiveAmt())
                .append("overdueAmt", getOverdueAmt())
                .append("overdueDayNum", getOverdueDayNum())
                .append("planReturnDate", getPlanReturnDate())
                .append("planReturnAmt", getPlanReturnAmt())
                .append("returnProb", getReturnProb())
                .append("realReturnAmt", getRealReturnAmt())
                .append("returnType", getReturnType())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
