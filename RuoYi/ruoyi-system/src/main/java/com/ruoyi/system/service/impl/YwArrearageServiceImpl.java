package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.YwConsumption;
import com.ruoyi.system.domain.ywArrearage.SaleManagerArrearageGather;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwArrearageMapper;
import com.ruoyi.system.domain.ywArrearage.YwArrearage;
import com.ruoyi.system.service.IYwArrearageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商机-欠款 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-14
 */
@Service
public class YwArrearageServiceImpl implements IYwArrearageService {

    private Logger log = LoggerFactory.getLogger(YwArrearageServiceImpl.class);

    @Autowired
    private YwArrearageMapper ywArrearageMapper;

    /**
     * 查询商机-欠款信息
     *
     * @param id 商机-欠款ID
     * @return 商机-欠款信息
     */
    @Override
    public YwArrearage selectYwArrearageById(Long id) {
        return ywArrearageMapper.selectYwArrearageById(id);
    }

    /**
     * 查询商机-欠款列表
     *
     * @param ywArrearage 商机-欠款信息
     * @return 商机-欠款集合
     */
    @Override
    public List<YwArrearage> selectYwArrearageList(YwArrearage ywArrearage) {
        return ywArrearageMapper.selectYwArrearageList(ywArrearage);
    }

    /**
     * 新增商机-欠款
     *
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
    @Override
    public int insertYwArrearage(YwArrearage ywArrearage) {
        return ywArrearageMapper.insertYwArrearage(ywArrearage);
    }

    /**
     * 修改商机-欠款
     *
     * @param ywArrearage 商机-欠款信息
     * @return 结果
     */
    @Override
    public int updateYwArrearage(YwArrearage ywArrearage) {
        return ywArrearageMapper.updateYwArrearage(ywArrearage);
    }

    /**
     * 删除商机-欠款对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYwArrearageByIds(String ids) {
        return ywArrearageMapper.deleteYwArrearageByIds(Convert.toStrArray(ids));
    }

    /**
     * 导入商机-欠款信息
     *
     * @param ywArrearageList 批量文件中的每条记录
     * @return 结果
     */
    public String importYwArrearages(List<YwArrearage> ywArrearageList, boolean updateSupport, String operName) {
        if (StringUtils.isNull(ywArrearageList) || ywArrearageList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        //查询是否重复的条件VO
        YwArrearage selectVO = new YwArrearage();
        for (YwArrearage ywArrearage : ywArrearageList) {
            try {
                //更新信息
                if (updateSupport) {
                    selectVO.setFundNo(ywArrearage.getFundNo());
                    selectVO.setContractNo(ywArrearage.getContractNo());
                    selectVO.setScheduleNo(ywArrearage.getScheduleNo());
                    List<YwArrearage> ywArrearages = ywArrearageMapper.selectYwArrearageList(selectVO);
                    if (ywArrearages.size() > 0) {
                        YwArrearage updateVO = ywArrearages.get(0);
                        ywArrearage.setId(updateVO.getId());
                        ywArrearage.setUpdateBy(operName);
                        ywArrearage.setUpdateTime(DateUtils.getNowDate());
                        ywArrearage.setCreateBy(updateVO.getCreateBy());
                        ywArrearage.setCreateTime(updateVO.getCreateTime());
                        ywArrearage.setRemark(updateVO.getRemark());
                        ywArrearageMapper.updateYwArrearage(ywArrearage);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、销售经理 " + ywArrearage.getSaleManager() + " 更新成功");
                        continue;
                    }
                }
                //插入新信息
                ywArrearage.setCreateBy(operName);
                ywArrearage.setCreateTime(DateUtils.getNowDate());
                this.insertYwArrearage(ywArrearage);
                successNum++;
                successMsg.append("<br/>" + successNum + "、销售经理 " + ywArrearage.getSaleManager() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、销售经理 " + ywArrearage.getSaleManager() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 查询汇总表-按销售经理
     *
     * @param ywArrearage 查询条件
     * @return
     */
    public List<SaleManagerArrearageGather> selectGatherSaleManager(YwArrearage ywArrearage) {

        return ywArrearageMapper.selectGatherSaleManager(ywArrearage);

    }

}
