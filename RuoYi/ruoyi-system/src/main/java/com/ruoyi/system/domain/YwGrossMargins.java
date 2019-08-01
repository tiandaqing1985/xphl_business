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
	@Excel(name = "销售经理", type = Excel.Type.IMPORT)
	private String salesManager;
	/** 媒体 */
	@Excel(name = "购买媒体", type = Excel.Type.IMPORT)
	private String media;
	/** 广告主 */
	@Excel(name = "广告主", type = Excel.Type.IMPORT)
	private String advertiser;
	/** 签约方 */
	@Excel(name = "签约方", type = Excel.Type.IMPORT)
	private String signatory;
	/** Q2毛利 */
	@Excel(name = "毛利", type = Excel.Type.IMPORT)
	private BigDecimal q2GrossMargin;

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
	public void setQ2GrossMargin(BigDecimal q2GrossMargin) 
	{
		this.q2GrossMargin = q2GrossMargin;
	}

	public BigDecimal getQ2GrossMargin() 
	{
		return q2GrossMargin;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("salesManager", getSalesManager())
            .append("media", getMedia())
            .append("advertiser", getAdvertiser())
            .append("signatory", getSignatory())
            .append("q2GrossMargin", getQ2GrossMargin())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
