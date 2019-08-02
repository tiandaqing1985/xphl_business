package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 消耗毛利汇总表 Gather
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public class Gather extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;
	/** 类型 */
	private String type;
	/** 期间 */
	private String quarter;
	/** 区域 */
	private String area;
	/** 部门 */
	private String deptName;
	/** 销售经理 */
	private String salesManager;
	/** 购买媒体 */
	private String media;
	/** 季度任务 */
	private BigDecimal quotas;
	/** 完成消耗金额 */
	private BigDecimal summation;
	/** 消耗平推完成金额 */
	private BigDecimal xhptAmt;
	/** 时间进度 */
	private String timeSchedule;
	/** 完成率 */
	private String xhwcRate;

	public void setId(Long id) 
	{
		this.id = id;
	}

	public Long getId() 
	{
		return id;
	}
	public void setType(String type) 
	{
		this.type = type;
	}

	public String getType() 
	{
		return type;
	}
	public void setQuarter(String quarter) 
	{
		this.quarter = quarter;
	}

	public String getQuarter() 
	{
		return quarter;
	}
	public void setArea(String area) 
	{
		this.area = area;
	}

	public String getArea() 
	{
		return area;
	}
	public void setDeptName(String deptName) 
	{
		this.deptName = deptName;
	}

	public String getDeptName() 
	{
		return deptName;
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
	public void setQuotas(BigDecimal quotas) 
	{
		this.quotas = quotas;
	}

	public BigDecimal getQuotas() 
	{
		return quotas;
	}
	public void setSummation(BigDecimal summation) 
	{
		this.summation = summation;
	}

	public BigDecimal getSummation() 
	{
		return summation;
	}
	public void setXhptAmt(BigDecimal xhptAmt) 
	{
		this.xhptAmt = xhptAmt;
	}

	public BigDecimal getXhptAmt() 
	{
		return xhptAmt;
	}
	public void setTimeSchedule(String timeSchedule) 
	{
		this.timeSchedule = timeSchedule;
	}

	public String getTimeSchedule() 
	{
		return timeSchedule;
	}
	public void setXhwcRate(String xhwcRate) 
	{
		this.xhwcRate = xhwcRate;
	}

	public String getXhwcRate() 
	{
		return xhwcRate;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("quarter", getQuarter())
            .append("area", getArea())
            .append("deptName", getDeptName())
            .append("salesManager", getSalesManager())
            .append("media", getMedia())
            .append("quotas", getQuotas())
            .append("summation", getSummation())
            .append("xhptAmt", getXhptAmt())
            .append("timeSchedule", getTimeSchedule())
            .append("xhwcRate", getXhwcRate())
            .toString();
    }
}
