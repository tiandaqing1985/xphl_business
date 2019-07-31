package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.system.mapper.YwBusinessMapper;
import com.ruoyi.system.mapper.YwContractMapper;
import com.ruoyi.system.domain.YwBusiness;
import com.ruoyi.system.domain.YwContract;
import com.ruoyi.system.service.IYwContractService;
import com.ruoyi.common.core.text.Convert;

/**
 * 下单 服务层实现
 * 
 * @author ruoyi
 * @date 2019-06-12
 */
@Service
public class YwContractServiceImpl implements IYwContractService 
{
	@Autowired
	private YwContractMapper ywContractMapper;
	
	@Autowired
	private YwBusinessMapper ywBusinessMapper;

	/**
     * 查询下单信息
     * 
     * @param id 下单ID
     * @return 下单信息
     */
    @Override
	public YwContract selectYwContractById(Long id)
	{
	    return ywContractMapper.selectYwContractById(id);
	}
	
	/**
     * 查询下单列表
     * 
     * @param ywContract 下单信息
     * @return 下单集合
     */
	@Override
	public List<YwContract> selectYwContractList(YwContract ywContract)
	{
	    return ywContractMapper.selectYwContractList(ywContract);
	}
	
    /**
     * 新增下单
     * 
     * @param ywContract 下单信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertYwContract(YwContract ywContract)
	{
		
		YwBusiness yb = ywBusinessMapper.selectYwBusinessById(ywContract.getBusinessId());
		
		yb.setMedia(ywContract.getMedia());
		yb.setOrderStatus("1");
		ywBusinessMapper.updateYwBusiness(yb);
		
		
	    return ywContractMapper.insertYwContract(ywContract);
	}
	
	/**
     * 修改下单
     * 
     * @param ywContract 下单信息
     * @return 结果
     */
	@Override
	public int updateYwContract(YwContract ywContract)
	{
	    return ywContractMapper.updateYwContract(ywContract);
	}

	/**
     * 删除下单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteYwContractByIds(String ids)
	{
		return ywContractMapper.deleteYwContractByIds(Convert.toStrArray(ids));
	}
	
}
