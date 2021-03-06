package com.ruoyi.system.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.Md5Utils;
import com.ruoyi.system.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwGrossMarginsMapper;
import com.ruoyi.system.domain.YwGrossMargins;
import com.ruoyi.system.service.IYwGrossMarginsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 毛利情况 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-01
 */
@Service
public class YwGrossMarginsServiceImpl implements IYwGrossMarginsService {

    private static final Logger log = LoggerFactory.getLogger(YwGrossMarginsServiceImpl.class);

    @Autowired
    private YwGrossMarginsMapper ywGrossMarginsMapper;

    /**
     * 查询毛利情况信息
     *
     * @param id 毛利情况ID
     * @return 毛利情况信息
     */
    @Override
    public YwGrossMargins selectYwGrossMarginsById(Long id) {
        return ywGrossMarginsMapper.selectYwGrossMarginsById(id);
    }

    /**
     * 查询毛利情况列表
     *
     * @param ywGrossMargins 毛利情况信息
     * @return 毛利情况集合
     */
    @Override
    public List<YwGrossMargins> selectYwGrossMarginsList(YwGrossMargins ywGrossMargins) {
        return ywGrossMarginsMapper.selectYwGrossMarginsList(ywGrossMargins);
    }

    /**
     * 新增毛利情况
     *
     * @param ywGrossMargins 毛利情况信息
     * @return 结果
     */
    @Override
    public int insertYwGrossMargins(YwGrossMargins ywGrossMargins) {
        return ywGrossMarginsMapper.insertYwGrossMargins(ywGrossMargins);
    }

    /**
     * 修改毛利情况
     *
     * @param ywGrossMargins 毛利情况信息
     * @return 结果
     */
    @Override
    public int updateYwGrossMargins(YwGrossMargins ywGrossMargins) {
        return ywGrossMarginsMapper.updateYwGrossMargins(ywGrossMargins);
    }

    /**
     * 删除毛利情况对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYwGrossMarginsByIds(String ids) {
        return ywGrossMarginsMapper.deleteYwGrossMarginsByIds(Convert.toStrArray(ids));
    }

    /**
     * 批量插入
     *
     * @param grossMarginList 没调毛利记录
     * @param updateSupport   是否支持更新
     * @param operName        当前登陆用户
     * @return
     */
    @Override
    public String importYwGrossMargins(List<YwGrossMargins> grossMarginList, boolean updateSupport, String operName) {

        if (StringUtils.isNull(grossMarginList) || grossMarginList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        if (grossMarginList.size() != 0) {
            ywGrossMarginsMapper.deleteYwGrossMarginsByQuarter(getQuarterByDate(new Date()));
        }
        YwGrossMargins selectYwGrossMargins = new YwGrossMargins();
        for (YwGrossMargins ywGrossMargins : grossMarginList) {
            try {
                ywGrossMargins.setQuarter(getQuarter(ywGrossMargins.getTerm()));
                if (updateSupport) {
                    selectYwGrossMargins.setSalesManager(ywGrossMargins.getSalesManager());
                    selectYwGrossMargins.setSignatory(ywGrossMargins.getSignatory());
                    selectYwGrossMargins.setAdvertiser(ywGrossMargins.getAdvertiser());
                    selectYwGrossMargins.setMedia(ywGrossMargins.getMedia());
                    selectYwGrossMargins.setQuarter(ywGrossMargins.getQuarter());
                    List<YwGrossMargins> ywGrossMarginsList = selectYwGrossMarginsList(selectYwGrossMargins);
                    if (ywGrossMarginsList.size() != 0) {
                        YwGrossMargins updateYwGrossMarginsVO = ywGrossMarginsList.get(0);
                        updateYwGrossMarginsVO.setGrossMargin(ywGrossMargins.getGrossMargin());
                        updateYwGrossMarginsVO.setTerm(ywGrossMargins.getTerm());
                        successMsg.append("<br/>" + successNum + "、销售经理 " + ywGrossMargins.getSalesManager() + " 更新成功");
                        updateYwGrossMargins(updateYwGrossMarginsVO);
                        continue;
                    }
                }
                ywGrossMargins.setCreateBy(operName);
                ywGrossMargins.setCreateTime(DateUtils.getNowDate());
                this.insertYwGrossMargins(ywGrossMargins);
                successNum++;
                successMsg.append("<br/>" + successNum + "、销售经理 " + ywGrossMargins.getSalesManager() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、销售经理 " + ywGrossMargins.getSalesManager() + " 导入失败：";
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

    //根据当前时间获取进度
    public static String getQuarterByDate(Date date) {
        String quarter = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 1 && month < 4) {
            quarter = "Q1";
        } else if (month >= 4 && month < 7) {
            quarter = "Q2";
        } else if (month >= 7 && month < 10) {
            quarter = "Q3";
        } else if (month >= 10 && month <= 12) {
            quarter = "Q4";
        } else {
            return null;
        }
        int year = calendar.get(Calendar.YEAR);
        quarter = String.valueOf(year).substring(2) + "年" + quarter;
        return quarter;
    }

    //根据日期获取季度
    private String getQuarter(String term) {
        String quarter = null;
        if (term == null || term.equals("")) {
            return null;
        }
        String[] terms = term.split("-");
        String[] dateStr = terms[0].split("\\.");
        int month = Integer.valueOf(dateStr[1]);
        if (month >= 1 && month < 4) {
            //第一季度
            quarter = "Q1";
        } else if (month >= 4 && month < 7) {
            //第二季度
            quarter = "Q2";
        } else if (month >= 7 && month < 10) {
            //第三季度
            quarter = "Q3";
        } else if (month >= 10 && month <= 12) {
            //第四度
            quarter = "Q4";
        }
        quarter = dateStr[0].substring(2) + "年" + quarter;
        return quarter;
    }

}
