package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 跟进表 yw_tract
 * 
 * @author ruoyi
 * @date 2019-06-11
 */
public class YwTract extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** ID */
	private Long id;
	/** 商机ID */
	private Long businessId;
	/** 跟进时间 */
	@Excel(name = "跟进时间", width = 20, dateFormat = "yyyy-MM-dd", type = Type.EXPORT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date traceTime;
	
	/** 公司名称 */
	@Excel(name = "公司名称" , width = 30)
	private String companyName;
	/** 广告主 */
	@Excel(name = "广告主" , width = 30)
	private String advertiser; 
	/** 简称 */
	@Excel(name = "简称" , width = 30)
	private String shortName;
	
	/** 跟进联系人 */
	@Excel(name = "跟进联系人")
	private String tractUser;
	/** 跟进事项 */
	@Excel(name = "跟进事项" , width = 80)
	private String tractMessage;
	/** 跟进结果 */
	@Excel(name = "跟进结果" , width = 80)
	private String tractResult;


	private String[] createBy1;
	
	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setBusinessId(Long businessId) 
	{
		this.businessId = businessId;
	}

	public Long getBusinessId() 
	{
		return businessId;
	}
	public void setTraceTime(Date traceTime) 
	{
		this.traceTime = traceTime;
	}

	public Date getTraceTime() 
	{
		return traceTime;
	}
	public void setTractUser(String tractUser) 
	{
		this.tractUser = tractUser;
	}

	public String getTractUser() 
	{
		return tractUser;
	}
	public void setTractMessage(String tractMessage) 
	{
		this.tractMessage = tractMessage;
	}

	public String getTractMessage() 
	{
		return tractMessage;
	}
	public void setTractResult(String tractResult) 
	{
		this.tractResult = tractResult;
	}

	public String getTractResult() 
	{
		return tractResult;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public String[] getCreateBy1() {
		return createBy1;
	}

	public void setCreateBy1(String[] createBy1) {
		this.createBy1 = createBy1;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessId", getBusinessId())
            .append("traceTime", getTraceTime())
            .append("tractUser", getTractUser())
            .append("tractMessage", getTractMessage())
            .append("tractResult", getTractResult())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("companyName", getCompanyName())
            .append("advertiser", getAdvertiser())
            .toString();
    }
}
