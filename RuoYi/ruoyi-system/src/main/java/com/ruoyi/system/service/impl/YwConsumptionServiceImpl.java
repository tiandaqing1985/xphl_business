package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.YwTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwConsumptionMapper;
import com.ruoyi.system.domain.YwConsumption;
import com.ruoyi.system.service.IYwConsumptionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 消耗情况 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-01
 */
@Service
public class YwConsumptionServiceImpl implements IYwConsumptionService {
    private static final Logger log = LoggerFactory.getLogger(YwConsumptionServiceImpl.class);

    @Autowired
    private YwConsumptionMapper ywConsumptionMapper;

    /**
     * 查询消耗情况信息
     *
     * @param id 消耗情况ID
     * @return 消耗情况信息
     */
    @Override
    public YwConsumption selectYwConsumptionById(Long id) {
        return ywConsumptionMapper.selectYwConsumptionById(id);
    }

    /**
     * 查询消耗情况列表
     *
     * @param ywConsumption 消耗情况信息
     * @return 消耗情况集合
     */
    @Override
    public List<YwConsumption> selectYwConsumptionList(YwConsumption ywConsumption) {
        return ywConsumptionMapper.selectYwConsumptionList(ywConsumption);
    }

    /**
     * 新增消耗情况
     *
     * @param ywConsumption 消耗情况信息
     * @return 结果
     */
    @Override
    public int insertYwConsumption(YwConsumption ywConsumption) {
        return ywConsumptionMapper.insertYwConsumption(ywConsumption);
    }

    /**
     * 修改消耗情况
     *
     * @param ywConsumption 消耗情况信息
     * @return 结果
     */
    @Override
    public int updateYwConsumption(YwConsumption ywConsumption) {
        return ywConsumptionMapper.updateYwConsumption(ywConsumption);
    }

    /**
     * 删除消耗情况对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYwConsumptionByIds(String ids) {
        return ywConsumptionMapper.deleteYwConsumptionByIds(Convert.toStrArray(ids));
    }

    /**
     * @param ywConsumptions
     * @param updateSupport
     * @param operName
     * @return
     */
    @Override
    public String importYwConsumption(List<YwConsumption> ywConsumptions, boolean updateSupport, String operName) {

        if (StringUtils.isNull(ywConsumptions) || ywConsumptions.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        YwConsumption consumption = new YwConsumption();
        for (YwConsumption ywConsumption : ywConsumptions) {
            try {
                if (updateSupport) {
                    consumption.setSaleManager(ywConsumption.getSaleManager());
                    consumption.setAdvertiser(ywConsumption.getAdvertiser());
                    consumption.setMedia(ywConsumption.getMedia());
                    List<YwConsumption> ywConsumptionList = selectYwConsumptionList(consumption);
                    if (ywConsumptionList.size() != 0) {
                        YwConsumption updateYwConsumption = ywConsumptionList.get(0);
                        updateYwConsumption.setOperatingIncome(ywConsumption.getOperatingIncome());
                        updateYwConsumption.setDiscounts(ywConsumption.getDiscounts());
                        updateYwConsumption.setYsbd(ywConsumption.getYsbd());
                        updateYwConsumption.setSummation(ywConsumption.getSummation());
                        updateYwConsumption(updateYwConsumption);
                        successMsg.append("<br/>" + successNum + "、销售经理 " + ywConsumption.getSaleManager() + " 更新成功");
                        continue;
                    }
                }
                ywConsumption.setCreateBy(operName);
                ywConsumption.setCreateTime(DateUtils.getNowDate());
                this.insertYwConsumption(ywConsumption);
                successNum++;
                successMsg.append("<br/>" + successNum + "、销售经理 " + ywConsumption.getSaleManager() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、销售经理 " + ywConsumption.getSaleManager() + " 导入失败：";
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
}
