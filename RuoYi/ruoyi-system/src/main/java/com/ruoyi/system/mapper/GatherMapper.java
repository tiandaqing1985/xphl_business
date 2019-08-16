package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Gather;
import com.ruoyi.system.domain.YwGrossMarginGather;
import com.ruoyi.system.domain.YwTask;

import java.util.List;

/**
 * 消耗毛利汇总 数据层
 *
 * @author ruoyi
 * @date 2019-08-02
 */
public interface GatherMapper {


    /**
     * 查询消耗汇总列表
     *
     * @param gather 消耗汇总信息
     * @return 消耗汇总集合
     */
    public List<Gather> selectGatherList(Gather gather);

    /**
     * 查询毛利汇总列表
     *
     * @param gather 毛利汇总信息
     * @return 毛利汇总集合
     */
    public List<YwGrossMarginGather> selectGrossMarginGatherList(YwGrossMarginGather gather);

    /**
     * 查询任务表合计
     * @param grossMarginGather
     * @return
     */
    public List<YwGrossMarginGather> selectGatherTask(YwGrossMarginGather grossMarginGather);

    /**
     * 查询毛利表合计
     * @param grossMarginGather
     * @return
     */
    public YwGrossMarginGather selectGrossMarginGatherGroup(YwGrossMarginGather grossMarginGather);

    /**
     * 查询毛利表中没有对应任务的合计
     * @param grossMarginGather
     * @return
     */
    public List<YwGrossMarginGather> selectNoTaskGatherGross(YwGrossMarginGather grossMarginGather);
}