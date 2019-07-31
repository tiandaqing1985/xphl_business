package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 商机-欠款表 yw_arrearage
 * 
 * @author ruoyi
 * @date 2019-07-30
 */
public class YwArrearage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Long id;
	/** 客户名称 */
	@Excel(name = "客户名称")
	private String clientName;
	/** 销售经理 */
	@Excel(name = "销售经理")
	private String sales;
	/** 合同类型 */
	@Excel(name = "合同类型")
	private String contractType;
	/** 欠款金额 */
	@Excel(name = "欠款金额")
	private Double money;
	/** 约定回款日期 */
	@Excel(name = "约定回款日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date appointedTime;
	/** 客户逾期 */
	@Excel(name = "客户逾期")
	private Integer overdueDay;
	/** 媒体逾期 */
	@Excel(name = "媒体逾期")
	private Integer overdueMedia;
	
	@Excel(name = "销售助理")
	private String createBy;
	
    /** 备注 */
	@Excel(name = "备注")
    private String remark;
	
	private String deptname;
	
	private String [] createBy1;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setClientName(String clientName) 
	{
		this.clientName = clientName;
	}

	public String getClientName() 
	{
		return clientName;
	}
	public void setSales(String sales) 
	{
		this.sales = sales;
	}

	public String getSales() 
	{
		return sales;
	}
	public void setContractType(String contractType) 
	{
		this.contractType = contractType;
	}

	public String getContractType() 
	{
		return contractType;
	}
	public void setMoney(Double money) 
	{
		this.money = money;
	}

	public Double getMoney() 
	{
		return money;
	}
	public void setAppointedTime(Date appointedTime) 
	{
		this.appointedTime = appointedTime;
	}

	public Date getAppointedTime() 
	{
		return appointedTime;
	}
	public void setOverdueDay(Integer overdueDay) 
	{
		this.overdueDay = overdueDay;
	}

	public Integer getOverdueDay() 
	{
		return overdueDay;
	}
	public void setOverdueMedia(Integer overdueMedia) 
	{
		this.overdueMedia = overdueMedia;
	}

	public Integer getOverdueMedia() 
	{
		return overdueMedia;
	}
	
    public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String[] getCreateBy1() {
		return createBy1;
	}

	public void setCreateBy1(String[] createBy1) {
		this.createBy1 = createBy1;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clientName", getClientName())
            .append("sales", getSales())
            .append("contractType", getContractType())
            .append("money", getMoney())
            .append("appointedTime", getAppointedTime())
            .append("overdueDay", getOverdueDay())
            .append("overdueMedia", getOverdueMedia())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
