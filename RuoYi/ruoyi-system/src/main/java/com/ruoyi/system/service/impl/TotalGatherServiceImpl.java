package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwTotalConsumGather;
import com.ruoyi.system.domain.YwTotalGrossGather;
import com.ruoyi.system.mapper.TotalGatherMapper;
import com.ruoyi.system.service.TotalGatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消耗毛利汇总 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-02
 */
@Service
public class TotalGatherServiceImpl implements TotalGatherService {

    @Autowired
    private TotalGatherMapper totalGatherMapper;

    /**
     * 查询消耗汇总列表
     *
     * @return 消耗毛利汇总集合
     */
    @Override
    public List<YwTotalConsumGather> selectTotalConsumGather(YwTotalConsumGather ywTotalGather) {
        //总计
        YwTotalConsumGather totalSum = new YwTotalConsumGather();
        totalSum.setDeptName("总计");
        totalSum.setSummation(BigDecimal.ZERO);
        totalSum.setQuotas(BigDecimal.ZERO);
        totalSum.setXhptAmt(BigDecimal.ZERO);
        //时间进度
        String timeSchedule = null;
        //统计部门相同的记录的合计
        Map<String, LinkedList<YwTotalConsumGather>> gatherMap = new HashMap<>();
        LinkedList<YwTotalConsumGather> totalConsumGathers = null;
        YwTotalConsumGather sumGather = null;
        List<YwTotalConsumGather> ywTotalConsumGathers = totalGatherMapper.selectTotalGatherConsum(ywTotalGather);

        for (YwTotalConsumGather totalConsumGather : ywTotalConsumGathers) {
            //查询每条记录对应的毛利完成金额
            if (totalConsumGather.getTerm() != null && !totalConsumGather.getTerm().equals("")) {
                //计算时间进度
                double timeSchedul = Double.valueOf(getTermDayNum(totalConsumGather.getTerm())) / getQuarterDayNum(totalConsumGather.getQuarter());
                timeSchedul = timeSchedul * 100;
                timeSchedule = BigDecimal.valueOf(timeSchedul).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
                break;
            }
        }

        for (YwTotalConsumGather totalConsumGather : ywTotalConsumGathers) {
            //查询每条记录对应的毛利完成金额
            totalConsumGather.setTimeSchedule(timeSchedule);
            BigDecimal mlptAmt = null;
            //计算平推完成金额
            if (totalConsumGather.getTerm() == null || totalConsumGather.getTerm().equals("")) {
                mlptAmt = BigDecimal.ZERO;
            } else {
                mlptAmt = totalConsumGather.getSummation().divide(BigDecimal.valueOf(getTermDayNum(totalConsumGather.getTerm())), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(totalConsumGather.getQuarter())));
            }
            totalConsumGather.setXhptAmt(mlptAmt.setScale(2, BigDecimal.ROUND_HALF_UP));

            totalSum.setQuotas(totalConsumGather.getQuotas().add(totalSum.getQuotas()));
            totalSum.setXhptAmt(totalConsumGather.getXhptAmt().add(totalSum.getXhptAmt()));
            totalSum.setSummation(totalConsumGather.getSummation().add(totalSum.getSummation()));

            //计算完成率
            if (totalConsumGather.getQuotas().compareTo(BigDecimal.ZERO) == 0 || totalConsumGather.getSummation().compareTo(BigDecimal.ZERO) == 0) {
                totalConsumGather.setXhwcRate("0.00%");
            } else {
                String xhwcRate = totalConsumGather.getSummation().divide(totalConsumGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
                totalConsumGather.setXhwcRate(xhwcRate);
            }
            //消除科学计数法
            totalConsumGather.setQuotas(new BigDecimal(totalConsumGather.getQuotas().toPlainString()));

            //统计合计
            totalConsumGathers = gatherMap.get(totalConsumGather.getDeptName());
            if (totalConsumGathers == null) {
                totalConsumGathers = new LinkedList<>();
                gatherMap.put(totalConsumGather.getDeptName(), totalConsumGathers);
            } else {
                if (totalConsumGathers.size() == 1) {
                    sumGather = new YwTotalConsumGather();
                    sumGather.setMedia("合计");
                    sumGather.setQuotas(totalConsumGathers.getLast().getQuotas());
                    sumGather.setSummation(totalConsumGathers.getLast().getSummation());
                    sumGather.setXhptAmt(totalConsumGathers.getLast().getXhptAmt());
                    sumGather.setTimeSchedule(timeSchedule);
                    totalConsumGathers.addLast(sumGather);
                }
                sumGather = totalConsumGathers.getLast();
                sumGather.setQuotas(totalConsumGather.getQuotas().add(sumGather.getQuotas()));
                sumGather.setXhptAmt(totalConsumGather.getXhptAmt().add(sumGather.getXhptAmt()));
                sumGather.setSummation(totalConsumGather.getSummation().add(sumGather.getSummation()));
                //计算完成率
                if (sumGather.getQuotas().compareTo(BigDecimal.ZERO) == 0 || sumGather.getSummation().compareTo(BigDecimal.ZERO) == 0) {
                    sumGather.setXhwcRate("0.00%");
                } else {
                    String mlwcRate = sumGather.getSummation().divide(sumGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
                    sumGather.setXhwcRate(mlwcRate);
                }
            }
            totalConsumGathers.addFirst(totalConsumGather);
        }
        //多条的
        List<YwTotalConsumGather> mulityVos = new ArrayList<>();
        //单条的
        List<YwTotalConsumGather> simpleVos = new ArrayList<>();
        Set<Map.Entry<String, LinkedList<YwTotalConsumGather>>> entries = gatherMap.entrySet();
        for (Map.Entry<String, LinkedList<YwTotalConsumGather>> entry : entries) {
            LinkedList<YwTotalConsumGather> value = entry.getValue();
            if (value.size() > 1) {
                for (YwTotalConsumGather gather : value) {
                    mulityVos.add(gather);
                }
            } else if (value.size() == 1) {
                simpleVos.add(value.get(0));
            }
        }
        //总计处理后的
        List<YwTotalConsumGather> list = new ArrayList<>();
        list.addAll(mulityVos);
        list.addAll(simpleVos);
        //合计
        totalSum.setTimeSchedule(timeSchedule);
        //合计计算完成率
        if (totalSum.getQuotas().compareTo(BigDecimal.ZERO) == 0 || totalSum.getSummation().compareTo(BigDecimal.ZERO) == 0) {
            totalSum.setXhwcRate("0.00%");
        } else {
            String mlwcRate = totalSum.getSummation().divide(totalSum.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
            totalSum.setXhwcRate(mlwcRate);
        }
        list.add(totalSum);
        return list;
    }

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    @Override
    public List<YwRankGrossMargin> selectRankGrossMarginList(YwRankGrossMargin yw) {
        List<YwRankGrossMargin> ywRankGrossMargins = totalGatherMapper.selectRankGrossMarginList(yw);
        for (YwRankGrossMargin ywRankGrossMargin : ywRankGrossMargins) {
            ywRankGrossMargin.setGrossMargin(new BigDecimal(ywRankGrossMargin.getGrossMargin().toPlainString()));
            ywRankGrossMargin.setQuotas(new BigDecimal(ywRankGrossMargin.getQuotas().toPlainString()));
        }
        return ywRankGrossMargins;
    }

    /**
     * 查询消耗排名
     *
     * @return 消耗排名
     */
    @Override
    public List<YwGatherConsumption> selectRankConsumptionlist(YwGatherConsumption yw) {
        List<YwGatherConsumption> ywGatherConsumptions = totalGatherMapper.selectRankConsumptionList(yw);
        for (YwGatherConsumption gatherConsumption : ywGatherConsumptions) {
            gatherConsumption.setQuotas(new BigDecimal(gatherConsumption.getQuotas().toPlainString()));
            gatherConsumption.setSummation(new BigDecimal(gatherConsumption.getSummation().toPlainString()));
        }
        return ywGatherConsumptions;
    }

    /**
     * 查询华东华北毛利整体完成率
     *
     * @param ywTotalGrossGather
     * @return
     */
    @Override
    public List<YwTotalGrossGather> selectTotalGrossGather(YwTotalGrossGather ywTotalGrossGather) {
        //合计
        YwTotalGrossGather totalSum = new YwTotalGrossGather();
        totalSum.setDeptName("总计");
        totalSum.setGrossMargin(BigDecimal.ZERO);
        totalSum.setQuotas(BigDecimal.ZERO);
        totalSum.setMlptAmt(BigDecimal.ZERO);
        //时间进度
        String timeSchedule = null;
        //统计部门相同的记录的合计
        Map<String, LinkedList<YwTotalGrossGather>> gatherMap = new HashMap<>();
        LinkedList<YwTotalGrossGather> totalGrossGathers = null;
        YwTotalGrossGather sumGather = null;
        List<YwTotalGrossGather> ywTotalGrossGathers = totalGatherMapper.selectTotalGatherTask(ywTotalGrossGather);

        for (YwTotalGrossGather totalGrossGather : ywTotalGrossGathers) {
            //查询每条记录对应的毛利完成金额
            YwTotalGrossGather gross = totalGatherMapper.selectTotalGatherGrossByVO(totalGrossGather);
            totalGrossGather.setTerm(gross.getTerm());
            if (totalGrossGather.getTerm() != null && !totalGrossGather.getTerm().equals("")) {
                //计算时间进度
                double timeSchedul = Double.valueOf(getTermDayNum(totalGrossGather.getTerm())) / getQuarterDayNum(totalGrossGather.getQuarter());
                timeSchedul = timeSchedul * 100;
                timeSchedule = BigDecimal.valueOf(timeSchedul).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
                break;
            }
        }

        for (YwTotalGrossGather totalGrossGather : ywTotalGrossGathers) {
            //查询每条记录对应的毛利完成金额
            YwTotalGrossGather gross = totalGatherMapper.selectTotalGatherGrossByVO(totalGrossGather);
            totalGrossGather.setGrossMargin(new BigDecimal(gross.getGrossMargin().toPlainString()));
            totalGrossGather.setTerm(gross.getTerm());
            totalGrossGather.setTimeSchedule(timeSchedule);
            BigDecimal mlptAmt = null;
            //计算平推完成金额
            if (totalGrossGather.getTerm() == null || totalGrossGather.getTerm().equals("")) {
                mlptAmt = BigDecimal.ZERO;
            } else {
                mlptAmt = totalGrossGather.getGrossMargin().divide(BigDecimal.valueOf(getTermDayNum(totalGrossGather.getTerm())), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(getQuarterDayNum(totalGrossGather.getQuarter())));
            }
            totalGrossGather.setMlptAmt(mlptAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
            //合计
            totalSum.setQuotas(totalGrossGather.getQuotas().add(totalSum.getQuotas()));
            totalSum.setMlptAmt(totalGrossGather.getMlptAmt().add(totalSum.getMlptAmt()));
            totalSum.setGrossMargin(totalGrossGather.getGrossMargin().add(totalSum.getGrossMargin()));
            //计算完成率
            if (totalGrossGather.getQuotas().compareTo(BigDecimal.ZERO) == 0 || totalGrossGather.getGrossMargin().compareTo(BigDecimal.ZERO) == 0) {
                totalGrossGather.setMlwcRate("0.00%");
            } else {
                String mlwcRate = totalGrossGather.getGrossMargin().divide(totalGrossGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
                totalGrossGather.setMlwcRate(mlwcRate);
            }
            //消除科学计数法
            totalGrossGather.setQuotas(new BigDecimal(totalGrossGather.getQuotas().toPlainString()));

            //统计合计
            totalGrossGathers = gatherMap.get(totalGrossGather.getDeptName());
            if (totalGrossGathers == null) {
                totalGrossGathers = new LinkedList<>();
                gatherMap.put(totalGrossGather.getDeptName(), totalGrossGathers);
            } else {
                if (totalGrossGathers.size() == 1) {
                    sumGather = new YwTotalGrossGather();
                    sumGather.setMedia("合计");
                    sumGather.setQuotas(totalGrossGathers.getLast().getQuotas());
                    sumGather.setGrossMargin(totalGrossGathers.getLast().getGrossMargin());
                    sumGather.setMlptAmt(totalGrossGathers.getLast().getMlptAmt());
                    sumGather.setTimeSchedule(timeSchedule);
                    totalGrossGathers.addLast(sumGather);
                }
                sumGather = totalGrossGathers.getLast();
                sumGather.setQuotas(totalGrossGather.getQuotas().add(sumGather.getQuotas()));
                sumGather.setMlptAmt(totalGrossGather.getMlptAmt().add(sumGather.getMlptAmt()));
                sumGather.setGrossMargin(totalGrossGather.getGrossMargin().add(sumGather.getGrossMargin()));
                //计算完成率
                if (sumGather.getQuotas().compareTo(BigDecimal.ZERO) == 0 || sumGather.getGrossMargin().compareTo(BigDecimal.ZERO) == 0) {
                    sumGather.setMlwcRate("0.00%");
                } else {
                    String mlwcRate = sumGather.getGrossMargin().divide(sumGather.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
                    sumGather.setMlwcRate(mlwcRate);
                }
            }
            totalGrossGathers.addFirst(totalGrossGather);
        }
        //多条的
        List<YwTotalGrossGather> mulityVos = new ArrayList<>();
        //单条的
        List<YwTotalGrossGather> simpleVos = new ArrayList<>();
        Set<Map.Entry<String, LinkedList<YwTotalGrossGather>>> entries = gatherMap.entrySet();
        for (Map.Entry<String, LinkedList<YwTotalGrossGather>> entry : entries) {
            LinkedList<YwTotalGrossGather> value = entry.getValue();
            if (value.size() > 1) {
                for (YwTotalGrossGather gather : value) {
                    mulityVos.add(gather);
                }
            } else if (value.size() == 1) {
                simpleVos.add(value.get(0));
            }
        }
        //总计处理后的
        List<YwTotalGrossGather> list = new ArrayList<>();
        list.addAll(mulityVos);
        list.addAll(simpleVos);
        //合计
        totalSum.setTimeSchedule(timeSchedule);
        //合计计算完成率
        if (totalSum.getQuotas().compareTo(BigDecimal.ZERO) == 0 || totalSum.getGrossMargin().compareTo(BigDecimal.ZERO) == 0) {
            totalSum.setMlwcRate("0.00%");
        } else {
            String mlwcRate = totalSum.getGrossMargin().divide(totalSum.getQuotas(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%";
            totalSum.setMlwcRate(mlwcRate);
        }
        list.add(totalSum);
        return list;
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

    //获取每个季度天数
    public int getQuarterDayNum(String quarter) {
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
}
