package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.system.mapper.YwBusinessMapper;
import com.ruoyi.system.mapper.YwTractMapper;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.domain.YwTract;
import com.ruoyi.system.service.IYwTractService;
import com.ruoyi.common.core.text.Convert;

/**
 * 跟进 服务层实现
 * 
 * @author ruoyi
 * @date 2019-06-11
 */
@Service
public class YwTractServiceImpl implements IYwTractService 
{
	@Autowired
	private YwTractMapper ywTractMapper;

	@Autowired
	private YwBusinessMapper ywBusinessMapper;
	/**
     * 查询跟进信息
     * 
     * @param id 跟进ID
     * @return 跟进信息
     */
    @Override
	public YwTract selectYwTractById(Long id)
	{
	    return ywTractMapper.selectYwTractById(id);
	}
	
	/**
     * 查询跟进列表
     * 
     * @param ywTract 跟进信息
     * @return 跟进集合
     */
	@Override
	public List<YwTract> selectYwTractList(YwTract ywTract)
	{
	    return ywTractMapper.selectYwTractList(ywTract);
	}
	
    /**
     * 新增跟进
     * 
     * @param ywTract 跟进信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertYwTract(YwTract ywTract)
	{
		YwBusiness yb = ywBusinessMapper.selectYwBusinessById(ywTract.getBusinessId());
		Integer grade =0;
		if(yb.getBusinessGrade()!=null){
			grade = Integer.parseInt(yb.getBusinessGrade())+10;
		}else{
			 grade = 10;
		}
		
		
		
		yb.setBusinessGrade(grade+"");
		
		ywBusinessMapper.updateYwBusiness(yb);
		
	    return ywTractMapper.insertYwTract(ywTract);
	}
	
	/**
     * 修改跟进
     * 
     * @param ywTract 跟进信息
     * @return 结果
     */
	@Override
	public int updateYwTract(YwTract ywTract)
	{
	    return ywTractMapper.updateYwTract(ywTract);
	}

	/**
     * 删除跟进对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteYwTractByIds(String ids)
	{
		return ywTractMapper.deleteYwTractByIds(Convert.toStrArray(ids));
	}
	
}
