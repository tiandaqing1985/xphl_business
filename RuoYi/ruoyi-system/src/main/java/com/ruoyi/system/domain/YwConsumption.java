package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 消耗情况表 yw_consumption
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
public class YwConsumption extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Long id;
	/** 销售经理 */
	@Excel(name="销售经理",type = Excel.Type.IMPORT)
	private String saleManager;
	/** 广告主名称 */
	@Excel(name="广告主名称",type = Excel.Type.IMPORT)
	private String advertiser;
	/** 购买资源 */
	@Excel(name="购买资源",type = Excel.Type.IMPORT)
	private String media;
	/** 运营收入 */
	@Excel(name="运营收入",type = Excel.Type.IMPORT)
	private BigDecimal operatingIncome;
	/** 补优惠 */
	@Excel(name="补优惠",type = Excel.Type.IMPORT)
	private BigDecimal discounts;
	/** 原生包段 */
	@Excel(name="原生包段",type = Excel.Type.IMPORT)
	private BigDecimal ysbd;
	/** 合计 */
	@Excel(name="合计",type = Excel.Type.IMPORT)
	private BigDecimal summation;

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
	public void setAdvertiser(String advertiser) 
	{
		this.advertiser = advertiser;
	}

	public String getAdvertiser() 
	{
		return advertiser;
	}
	public void setMedia(String media) 
	{
		this.media = media;
	}

	public String getMedia() 
	{
		return media;
	}
	public void setSummation(BigDecimal summation) 
	{
		this.summation = summation;
	}

	public BigDecimal getSummation() 
	{
		return summation;
	}

	public BigDecimal getOperatingIncome() {
		return operatingIncome;
	}

	public void setOperatingIncome(BigDecimal operatingIncome) {
		this.operatingIncome = operatingIncome;
	}

	public BigDecimal getDiscounts() {
		return discounts;
	}

	public void setDiscounts(BigDecimal discounts) {
		this.discounts = discounts;
	}

	public BigDecimal getYsbd() {
		return ysbd;
	}

	public void setYsbd(BigDecimal ysbd) {
		this.ysbd = ysbd;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("saleManager", getSaleManager())
				.append("advertiser", getAdvertiser())
				.append("media", getMedia())
				.append("operatingIncome", getOperatingIncome())
				.append("discounts", getDiscounts())
				.append("ysbd", getYsbd())
				.append("summation", getSummation())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark())
				.toString();
	}
}
