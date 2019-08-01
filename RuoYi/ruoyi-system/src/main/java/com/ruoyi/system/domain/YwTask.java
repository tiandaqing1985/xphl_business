package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 任务表 yw_task
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public class YwTask extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Long id;
	/** 销售经理 */
	@Excel(name="销售经理",type = Excel.Type.IMPORT)
	private String saleManager;
	/** 购买媒体 */
	@Excel(name="购买媒体",type = Excel.Type.IMPORT)
	private String media;
	/** 类别 */
	@Excel(name="类别",type = Excel.Type.IMPORT)
	private String type;
	/** 任务 */
	@Excel(name="任务",type = Excel.Type.IMPORT)
	private BigDecimal quotas;
	/** 考核期间 */
	@Excel(name="考核期间",type = Excel.Type.IMPORT)
	private String quarter;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setSaleManager(String saleManager) 
	{
		this.saleManager = saleManager;
	}

	public String getSaleManager() 
	{
		return saleManager;
	}
	public void setMedia(String media) 
	{
		this.media = media;
	}

	public String getMedia() 
	{
		return media;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setQuotas(BigDecimal quotas) 
	{
		this.quotas = quotas;
	}

	public BigDecimal getQuotas() 
	{
		return quotas;
	}
	public void setQuarter(String quarter) 
	{
		this.quarter = quarter;
	}

	public String getQuarter() 
	{
		return quarter;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("saleManager", getSaleManager())
            .append("media", getMedia())
            .append("type", getType())
            .append("quotas", getQuotas())
            .append("quarter", getQuarter())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
