package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.system.domain.YwGrossMarginGather;
import com.ruoyi.system.domain.YwTask;
import com.ruoyi.system.domain.ywArrearage.CustomerArrearageGather;
import com.ruoyi.system.mapper.YwGrossMarginsMapper;
import com.ruoyi.system.mapper.YwTaskMapper;
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
     * @param gathersList 消耗毛利汇总list
     * @return 消耗毛利汇总集合
     */
    @Override
    public List<Gather> exportList(List<Gather> gathersList) {

        //任务和完成金额都为0的记录过滤掉
        List<Gather> list = new ArrayList<>();
        for (Gather gather : gathersList) {
            if ((gather.getSummation() == null || gather.getSummation().compareTo(BigDecimal.ZERO) == 0) &&
                    (gather.getQuotas() == null || gather.getQuotas().compareTo(BigDecimal.ZERO) == 0)) {
                continue;
            } else {
                list.add(gather);
            }
        }
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

    /**
     * 查询总后的个人毛利信息
     *
     * @param gather 毛利汇总查询条件
     * @return 汇总后的个人毛利信息
     */
    @Override
    public List<YwGrossMarginGather> selectGrossMarginGatherList(YwGrossMarginGather gather) {
        //是否有媒体是全媒体
        boolean isFullMedia = false;
        String timeSch = null;
        String mlwcRate = null;
        BigDecimal mlptAmt = null;
        List<YwGrossMarginGather> ywGrossMarginGathers = gatherMapper.selectGatherTask(gather);
        //查询任务汇总，得到毛利任务金额
        for (YwGrossMarginGather grossMarginGather : ywGrossMarginGathers) {
            if (grossMarginGather.getMedia().equals("全媒体")) {
                isFullMedia = true;
            }
            YwGrossMarginGather marginGather = gatherMapper.selectGrossMarginGatherGroup(grossMarginGather);
            if (marginGather == null) {
                grossMarginGather.setGrossMargin(BigDecimal.ZERO);
            } else {
                grossMarginGather.setGrossMargin(marginGather.getGrossMargin());
                grossMarginGather.setTerm(marginGather.getTerm());
            }
            //计算完成率
            if (grossMarginGather.getGrossMargin().compareTo(BigDecimal.ZERO) == 0 || grossMarginGather.getQuotas().compareTo(BigDecimal.ZERO) == 0) {
                mlwcRate = "0.00%";
            } else {
                mlwcRate = grossMarginGather.getGrossMargin().divide(grossMarginGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
            }
            grossMarginGather.setMlwcRate(mlwcRate);
            //平推金额
            if (grossMarginGather.getTerm() == null || grossMarginGather.getTerm().equals("")) {
                mlptAmt = BigDecimal.ZERO;
            } else {
                mlptAmt = grossMarginGather.getGrossMargin().divide(BigDecimal.valueOf(getTermDayNum(grossMarginGather.getTerm())), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(grossMarginGather.getQuarter())));
            }
            grossMarginGather.setMlptAmt(mlptAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
            //计算时间进度
            if (grossMarginGather.getTerm() != null && timeSch == null) {
                double timeSchedule = Double.valueOf(getTermDayNum(grossMarginGather.getTerm())) / getQuarterDayNum(grossMarginGather.getQuarter());
                timeSchedule = timeSchedule * 100;
                timeSch = BigDecimal.valueOf(timeSchedule).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
            }
            grossMarginGather.setTimeSchedule(timeSch);
            //消除科学计数法
            grossMarginGather.setQuotas(new BigDecimal(grossMarginGather.getQuotas().toPlainString()));
            grossMarginGather.setGrossMargin(new BigDecimal(grossMarginGather.getGrossMargin().toPlainString()));
        }
        //查询没有任务的毛利合计
        YwGrossMarginGather mediaCon = new YwGrossMarginGather();
        List<YwGrossMarginGather> noTaskGross = new ArrayList<>();
        mediaCon.setQuarter(gather.getQuarter());
        if (isFullMedia) {
            mediaCon.setMedia("全媒体");
            noTaskGross = gatherMapper.selectNoTaskGatherGross(mediaCon);
        } else {
            noTaskGross = gatherMapper.selectNoTaskGatherGross(mediaCon);
        }

        for (YwGrossMarginGather grossMarginGather : noTaskGross) {
            grossMarginGather.setMlwcRate("0.00%");
            //平推金额
            if (grossMarginGather.getTerm() == null || grossMarginGather.getTerm().equals("")) {
                mlptAmt = BigDecimal.ZERO;
            } else {
                mlptAmt = grossMarginGather.getGrossMargin().divide(BigDecimal.valueOf(getTermDayNum(grossMarginGather.getTerm())), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(grossMarginGather.getQuarter())));
            }
            grossMarginGather.setMlptAmt(mlptAmt.setScale(2,BigDecimal.ROUND_HALF_UP));
            grossMarginGather.setTimeSchedule(timeSch);
            //消除科学计数法
            grossMarginGather.setGrossMargin(new BigDecimal(grossMarginGather.getGrossMargin().toPlainString()));
        }
        ywGrossMarginGathers.addAll(noTaskGross);


        //统一时间进度
        for (YwGrossMarginGather grossMarginGather : ywGrossMarginGathers) {
            grossMarginGather.setTimeSchedule(timeSch);
        }

        return computeSumAndTotal(ywGrossMarginGathers);
    }

    private List<YwGrossMarginGather> computeSumAndTotal(List<YwGrossMarginGather> list) {
        //根据地区计算合计和总计
        LinkedList<YwGrossMarginGather> linkedList = null;
        YwGrossMarginGather sum = null;
        YwGrossMarginGather total = new YwGrossMarginGather();
        YwGrossMarginGather gather = null;
        total.setSaleManager("总计");
        total.setQuotas(BigDecimal.ZERO);
        total.setGrossMargin(BigDecimal.ZERO);
        total.setMlptAmt(BigDecimal.ZERO);
        Map<String, LinkedList<YwGrossMarginGather>> gatherMap = new HashMap<>();
        for (YwGrossMarginGather arrearageGather : list) {
            if (arrearageGather.getQuotas() == null) {
                arrearageGather.setQuotas(BigDecimal.ZERO);
            }
            total.setQuotas(total.getQuotas().add(arrearageGather.getQuotas()));
            if (arrearageGather.getGrossMargin() == null) {
                arrearageGather.setGrossMargin(BigDecimal.ZERO);
            }
            total.setGrossMargin(total.getGrossMargin().add(arrearageGather.getGrossMargin()));
            if (arrearageGather.getMlptAmt() == null) {
                arrearageGather.setMlptAmt(BigDecimal.ZERO);
            }
            total.setMlptAmt(total.getMlptAmt().add(arrearageGather.getMlptAmt()));

            linkedList = gatherMap.get(arrearageGather.getDeptName());
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                gatherMap.put(arrearageGather.getDeptName(), linkedList);
                sum = new YwGrossMarginGather();
                linkedList.addLast(sum);
                sum.setSaleManager("合计");
                sum.setQuotas(BigDecimal.ZERO);
                sum.setGrossMargin(BigDecimal.ZERO);
                sum.setMlptAmt(BigDecimal.ZERO);
            } else {
                sum = linkedList.getLast();
            }
            sum.setQuotas(sum.getQuotas().add(arrearageGather.getQuotas()));
            sum.setGrossMargin(sum.getGrossMargin().add(arrearageGather.getGrossMargin()));
            sum.setMlptAmt(sum.getMlptAmt().add(arrearageGather.getMlptAmt()));
            if (sum.getQuotas().compareTo(BigDecimal.ZERO) != 0) {
                sum.setMlwcRate(sum.getGrossMargin().divide(sum.getQuotas(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
            } else {
                sum.setMlwcRate("0.00%");
            }
            linkedList.addFirst(arrearageGather);
        }

        LinkedList<YwGrossMarginGather> singletonLinkedList = new LinkedList<>();
        Set<Map.Entry<String, LinkedList<YwGrossMarginGather>>> entries = gatherMap.entrySet();
        String deptName = null;
        LinkedList<YwGrossMarginGather> gatherLinkedList = null;

        for (Map.Entry<String, LinkedList<YwGrossMarginGather>> entry : gatherMap.entrySet()) {
            deptName = entry.getKey();
            gatherLinkedList = entry.getValue();
            if (gatherLinkedList.size() == 2) {
                singletonLinkedList.addLast(gatherLinkedList.getFirst());
            } else {
                while (gatherLinkedList.size() > 0) {
                    gather = gatherLinkedList.removeLast();
                    if (deptName == null && gather.getSaleManager().equals("合计")) {
                        continue;
                    }
                    singletonLinkedList.addFirst(gather);
                }
            }
        }

        if (total.getQuotas().compareTo(BigDecimal.ZERO) != 0) {
            total.setMlwcRate(total.getGrossMargin().divide(total.getQuotas(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
        } else {
            total.setMlwcRate("0.00%");
        }
        singletonLinkedList.addLast(total);
        return singletonLinkedList;
    }

}
