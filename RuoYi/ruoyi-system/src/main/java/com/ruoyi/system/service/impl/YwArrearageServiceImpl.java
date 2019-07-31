package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwArrearageMapper;
import com.ruoyi.system.domain.YwArrearage;
import com.ruoyi.system.service.IYwArrearageService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;

/**
 * 商机-欠款 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-30
 */
@Service
public class YwArrearageServiceImpl implements IYwArrearageService 
{
	@Autowired
	private YwArrearageMapper ywArrearageMapper;

	/**
     * 查询商机-欠款信息
     * 
     * @param id 商机-欠款ID
     * @return 商机-欠款信息
     */
    @Override
	public YwArrearage selectYwArrearageById(Long id)
	{
	    return ywArrearageMapper.selectYwArrearageById(id);
	}
	
	/**
     * 查询商机-欠款列表
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 商机-欠款集合
     */
	@Override
	public List<YwArrearage> selectYwArrearageList(YwArrearage ywArrearage)
	{
	    return ywArrearageMapper.selectYwArrearageList(ywArrearage);
	}
	
    /**
     * 新增商机-欠款
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
	@Override
	public int insertYwArrearage(YwArrearage ywArrearage)
	{
	    return ywArrearageMapper.insertYwArrearage(ywArrearage);
	}
	
	/**
     * 修改商机-欠款
     * 
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
	@Override
	public int updateYwArrearage(YwArrearage ywArrearage)
	{
	    return ywArrearageMapper.updateYwArrearage(ywArrearage);
	}

	/**
     * 删除商机-欠款对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteYwArrearageByIds(String ids)
	{
		return ywArrearageMapper.deleteYwArrearageByIds(Convert.toStrArray(ids));
	}

	@Override
	public String importArrearage(List<YwArrearage> ywArrearageList, Boolean isUpdateSupport, String operName) {
		if (StringUtils.isNull(ywArrearageList) || ywArrearageList.size() == 0)
        {
            throw new BusinessException("导入用户数据不能为空！");
        }
        
        try{
        String flag = "";
	        if(null!=ywArrearageList&&ywArrearageList.size()>0){
	            int pointsDataLimit = 10000;//限制条数
	            Integer size = ywArrearageList.size();
	            //判断是否有必要分批
	            if(pointsDataLimit<size){
	                int part = size/pointsDataLimit;//分批数
	                // System.out.println("共有 ： "+size+"条，！"+" 分为 ："+part+"批");
	                //
	                for (int i = 0; i < part; i++) {
	                    //1000条
	                    List<YwArrearage> listPage = ywArrearageList.subList(0, pointsDataLimit);
	                    ywArrearageMapper.batchInsert(listPage);
	                    //剔除
	                    ywArrearageList.subList(0, pointsDataLimit).clear();
	                }
	                if(!ywArrearageList.isEmpty()){
	                    //表示最后剩下的数据
	                	ywArrearageMapper.batchInsert(ywArrearageList);
	                }
	            }else{
	            	ywArrearageMapper.batchInsert(ywArrearageList);
	            }
	        }
	        return "导入成功";
        }catch (Exception e){
        	e.printStackTrace();
        	 return "导入失败";
        }
	}

	@Override
	public List<YwArrearage> selectSumList(YwArrearage ywArrearage) {
		return ywArrearageMapper.selectSumList(ywArrearage);
	}
	
}
