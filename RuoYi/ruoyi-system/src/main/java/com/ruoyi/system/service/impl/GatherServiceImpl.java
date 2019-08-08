package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String saleManager = null;
        for (Gather g : gathers) {
            try {
                //某些特定的销售经理固定区域和部门
                saleManager = g.getSalesManager();
                if (saleManager.equals("anqi01")) {
                    g.setArea("北京");
                    g.setDeptName("媒介");
                } else if (saleManager.equals("任总")) {
                    g.setArea("北京");
                } else if (saleManager.equals("刘鹏")) {
                    g.setArea("SEM其他");
                } else if (saleManager.equals("系统中未关联")) {
                    g.setArea("系统中未关联");
                } else if (saleManager.equals("不录入系统")) {
                    g.setArea("不录入系统");
                } else if (saleManager.equals("代理客户") || saleManager.equals("直签客户")) {
                    g.setArea("北京");
                    g.setDeptName("客户");
                }
                //计算平推完成金额
                if (g.getTerm() == null || g.getTerm().equals("")) {
                    xhptAmt = BigDecimal.ZERO;
                } else {
                    xhptAmt = g.getSummation().divide(BigDecimal.valueOf(getTermDayNum(g.getTerm())), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(g.getQuarter())));
                }
                g.setXhptAmt(xhptAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
                //计算时间进度
                double timeSchedule = Double.valueOf(getTermDayNum(g.getTerm())) / getQuarterDayNum(g.getQuarter());
                timeSchedule = timeSchedule * 100;
                g.setTimeSchedule(BigDecimal.valueOf(timeSchedule).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            } catch (Exception e) {
                log.error("计算平推完成金额和时间进度时出现未知异常：", e);
            }
        }
        return gathers;
    }

    /**
     * 处理得到导出的list列表
     *
     * @param list 消耗毛利汇总list
     * @return 消耗毛利汇总集合
     */
    @Override
    public List<Gather> exportList(List<Gather> list) {

        //总计
        Gather totalGather = new Gather();
        totalGather.setDeptName("总计");
        totalGather.setQuotas(BigDecimal.ZERO);
        totalGather.setSummation(BigDecimal.ZERO);
        totalGather.setXhptAmt(BigDecimal.ZERO);
        //只有一条的数据
        List<Gather> singleGathers = new ArrayList<>();
        //需要放一起的只有一条的数据
        LinkedList<Gather> specialGathers = new LinkedList<>();
        String timeSchedule = null;
        Gather sumGather = null;
        //导出的list数据
        List<Gather> exportlist = new ArrayList<>(list.size());
        //每个键值对表示一个人的所有记录
        Map<String, List<Gather>> exportMap = new HashMap<String, List<Gather>>();
        //表示一个人的所有记录
        List<Gather> gathers = null;
        //按照销售经理姓名分组
        for (Gather gat : list) {
            gathers = exportMap.get(gat.getSalesManager());
            if (gathers == null) {
                gathers = new ArrayList<>();
                exportMap.put(gat.getSalesManager(), gathers);
            }
            gathers.add(gat);
            if (timeSchedule == null) {
                if (gat.getTimeSchedule() != null && !gat.getTimeSchedule().equals("0.00%")) {
                    timeSchedule = gat.getTimeSchedule();
                }
            }
            if (gat.getTimeSchedule() == null || gat.getTimeSchedule().equals("0.00%")) {
                gat.setTimeSchedule(timeSchedule);
            }
        }
        //计算每个销售经理的合计，并且构建导出的list
        Set<Map.Entry<String, List<Gather>>> entries = exportMap.entrySet();
        String name = null;
        List<Gather> gatherList = null;
        for (Map.Entry<String, List<Gather>> entry : exportMap.entrySet()) {
            name = entry.getKey();
            gatherList = entry.getValue();
            if (gatherList.size() == 1) {
                Gather gather = gatherList.get(0);
                if (gather.getSalesManager().equals("直签客户") || gather.getSalesManager().equals("代理客户")) {
                    specialGathers.addFirst(gather);
                } else if (gather.getSalesManager().equals("anqi01")) {
                    specialGathers.addLast(gather);
                } else {
                    singleGathers.add(gatherList.get(0));
                }
                continue;
            }
            sumGather = new Gather();
            sumGather.setArea(name);
            sumGather.setDeptName("合计");
            sumGather.setQuotas(BigDecimal.ZERO);
            sumGather.setSummation(BigDecimal.ZERO);
            sumGather.setXhptAmt(BigDecimal.ZERO);
            Collections.sort(gatherList);
            //遍历每个人的记录
            for (Gather g : gatherList) {

                //去除科学计数法
                g.setQuotas(g.getQuotas() != null ? new BigDecimal(g.getQuotas().toPlainString()) : null);
                g.setSummation(g.getSummation() != null ? new BigDecimal(g.getSummation().toPlainString()) : null);
                g.setTimeSchedule(timeSchedule);
                exportlist.add(g);
                //统计每人的总计和所有总计
                if (g.getQuotas() != null) {
                    sumGather.setQuotas(g.getQuotas().add(sumGather.getQuotas()));
                }
                if (g.getSummation() != null) {
                    sumGather.setSummation(g.getSummation().add(sumGather.getSummation()));
                }
                if (g.getXhptAmt() != null) {
                    sumGather.setXhptAmt(g.getXhptAmt().add(sumGather.getXhptAmt()));
                }
            }
            if (sumGather.getQuotas().compareTo(BigDecimal.ZERO) != 0) {
                sumGather.setXhwcRate(sumGather.getSummation().divide(sumGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
            }
            sumGather.setTimeSchedule(timeSchedule);
            //计算总计
            totalGather.setQuotas(totalGather.getQuotas().add(sumGather.getQuotas()));
            totalGather.setSummation(totalGather.getSummation().add(sumGather.getSummation()));
            totalGather.setXhptAmt(totalGather.getXhptAmt().add(sumGather.getXhptAmt()));

            exportlist.add(sumGather);
        }
        //向导出列表中插入特殊处理的记录，同时计算总计
        while (specialGathers.size() > 0) {
            Gather specialGath = specialGathers.removeFirst();
            exportlist.add(specialGath);
            if (specialGath.getQuotas() != null) {
                totalGather.setQuotas(specialGath.getQuotas().add(totalGather.getQuotas()));
            }
            if (specialGath.getSummation() != null) {
                totalGather.setSummation(specialGath.getSummation().add(totalGather.getSummation()));
            }
            if (specialGath.getXhptAmt() != null) {
                totalGather.setXhptAmt(specialGath.getXhptAmt().add(totalGather.getXhptAmt()));
            }
        }
        //将只有一条记录的金额加入总计
        for (Gather singleGather : singleGathers) {
            if (singleGather.getQuotas() != null) {
                totalGather.setQuotas(singleGather.getQuotas().add(totalGather.getQuotas()));
            }
            if (singleGather.getSummation() != null) {
                totalGather.setSummation(singleGather.getSummation().add(totalGather.getSummation()));
            }
            if (singleGather.getXhptAmt() != null) {
                totalGather.setXhptAmt(singleGather.getXhptAmt().add(totalGather.getXhptAmt()));
            }
        }
        exportlist.addAll(singleGathers);
        if (totalGather.getQuotas().compareTo(BigDecimal.ZERO) != 0) {
            totalGather.setXhwcRate(totalGather.getSummation().divide(totalGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
        }
        totalGather.setTimeSchedule(timeSchedule);
        exportlist.add(totalGather);
        return exportlist;
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

        return days + 1;
    }

}
