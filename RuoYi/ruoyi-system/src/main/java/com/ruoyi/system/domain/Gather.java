package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.math.BigDecimal;
import java.text.CollationKey;
import java.text.Collator;

/**
 * 消耗毛利汇总表 Gather
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
public class Gather extends BaseEntity implements Comparable<Gather>
{
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Long id;
	/** 考核期间 */
	private String term;
	/** 类型 */
	@Excel(name="类型",type = Excel.Type.EXPORT)
	private String type;
	/** 期间 */
	@Excel(name="期间",type = Excel.Type.EXPORT)
	private String quarter;
	/** 区域 */
	@Excel(name="区域",type = Excel.Type.EXPORT)
	private String area;
	/** 部门 */
	@Excel(name="部门",type = Excel.Type.EXPORT)
	private String deptName;
	/** 销售经理 */
	@Excel(name="销售经理",type = Excel.Type.EXPORT)
	private String salesManager;
	/** 购买媒体 */
	@Excel(name="购买媒体",type = Excel.Type.EXPORT)
	private String media;
	/** 季度任务 */
	@Excel(name="季度任务",type = Excel.Type.EXPORT)
	private BigDecimal quotas;
	/** 完成金额 */
	@Excel(name="完成金额",type = Excel.Type.EXPORT)
	private BigDecimal summation;
	/** 平推完成金额 */
	@Excel(name="平推完成金额",type = Excel.Type.EXPORT)
	private BigDecimal xhptAmt;
	/** 时间进度 */
	@Excel(name="时间进度",type = Excel.Type.EXPORT)
	private String timeSchedule;
	/** 完成率 */
	@Excel(name="完成率",type = Excel.Type.EXPORT)
	private String xhwcRate;

	@Excel(name="违规扣罚",type = Excel.Type.EXPORT)
	private BigDecimal fine;

	public BigDecimal getFine() {
		return fine;
	}

	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}

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
	private Collator collator = Collator.getInstance();//点击查看中文api详解
	@Override
	public int compareTo(Gather gather) {

		//把字符串转换为一系列比特，它们可以以比特形式与 CollationKeys 相比较
		CollationKey key1=collator.getCollationKey(this.getMedia());//要想不区分大小写进行比较用o1.toString().toLowerCase()
		CollationKey key2=collator.getCollationKey(gather.getMedia());

		return key1.compareTo(key2);
	}
}
