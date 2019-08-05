package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwGatherGrossMargin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.GatherMapper;
import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.service.IGatherService;

/**
 * 消耗毛利汇总 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class GatherServiceImpl implements IGatherService {

    private static final Logger log = LoggerFactory.getLogger(GatherServiceImpl.class);

    @Autowired
    private GatherMapper gatherMapper;

    /**
     * 查询消耗汇总列表
     *
     * @param gather 消耗毛利汇总信息
     * @return 消耗毛利汇总集合
     */
    @Override
    public List<Gather> selectGatherList(Gather gather) {
        List<Gather> gathers = null;
        gathers = gatherMapper.selectGatherList(gather);
        BigDecimal xhptAmt = null;
        for (Gather g : gathers) {
            try {
                //计算平推完成金额
                if (g.getTerm() == null || g.getTerm().equals("")) {
                    xhptAmt = BigDecimal.ZERO;
                } else {
                    xhptAmt = g.getSummation().divide(BigDecimal.valueOf(getTermDayNum(g.getTerm())),2,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(g.getQuarter())));
                }
                g.setXhptAmt(xhptAmt.setScale(2));
                //计算时间进度
                double timeSchedule = Double.valueOf(getTermDayNum(g.getTerm())) / getQuarterDayNum(g.getQuarter());
                timeSchedule = timeSchedule * 100;
                g.setTimeSchedule(BigDecimal.valueOf(timeSchedule).setScale(2,BigDecimal.ROUND_HALF_UP).toString() + "%");
            } catch (Exception e) {
                log.error("计算平推完成金额和时间进度时出现未知异常：", e);
            }
        }
        return gathers;
    }

    //获取每个季度天数
    private int getQuarterDayNum(String quarter) {
        if (quarter == null || quarter.equals("")) {
            return 0;
        }
        String[] str = quarter.split("年");
        if (str[1].equals("Q1")) {
            return 90;
        } else if (str[1].equals("Q2")) {
            return 91;
        } else if (str[1].equals("Q3")) {
            return 92;
        } else if (str[1].equals("Q4")) {
            return 92;
        } else {
            return 0;
        }
    }

    //获取每个考核期间天数
    private int getTermDayNum(String term) {
        if (term == null || term.equals("")) {
            return 0;
        }
        String[] terms = term.split("-");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        int result = 0;

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        try {
            calst.setTime(sdf.parse(terms[0]));
            caled.setTime(sdf.parse(terms[1]));
        } catch (ParseException e) {
            log.error("日期转换失败", e);
            return 0;
        }
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

}
