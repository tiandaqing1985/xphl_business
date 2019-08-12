package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 毛利情况表 yw_gross_margins
 *
 * @author ruoyi
 * @date 2019-08-01
 */
public class YwGrossMargins extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	/** 主键id */
	private Long id;
	/** 销售经理 */
	@Excel(name = "销售经理", type = Excel.Type.ALL)
	private String salesManager;
	/** 媒体 */
	@Excel(name = "购买媒体", type = Excel.Type.ALL)
	private String media;
	/** 广告主 */
	@Excel(name = "广告主", type = Excel.Type.ALL)
	private String advertiser;
	/** 签约方 */
	@Excel(name = "客户签约方", type = Excel.Type.ALL)
	private String signatory;
	/** 毛利 */
	@Excel(name = "毛利", type = Excel.Type.ALL)
	private BigDecimal grossMargin;
	/** 考核期间 */
	@Excel(name = "考核期间", type = Excel.Type.ALL)
	private String term;
	/** 期间 */
	private String quarter;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setSalesManager(String salesManager) 
	{
		this.salesManager = salesManager;
	}

	public String getSalesManager() 
	{
		return salesManager;
	}
	public void setMedia(String media) 
	{
		this.media = media;
	}

	public String getMedia() 
	{
		return media;
	}
	public void setAdvertiser(String advertiser) 
	{
		this.advertiser = advertiser;
	}

	public String getAdvertiser() 
	{
		return advertiser;
	}
	public void setSignatory(String signatory) 
	{
		this.signatory = signatory;
	}

	public String getSignatory() 
	{
		return signatory;
	}

    public BigDecimal getGrossMargin() {
        return grossMargin;
    }

    public void setGrossMargin(BigDecimal grossMargin) {
        this.grossMargin = grossMargin;
    }

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("salesManager", getSalesManager())
                .append("media", getMedia())
                .append("advertiser", getAdvertiser())
                .append("signatory", getSignatory())
                .append("grossMargin", getGrossMargin())
				.append("quarter",getQuarter())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
