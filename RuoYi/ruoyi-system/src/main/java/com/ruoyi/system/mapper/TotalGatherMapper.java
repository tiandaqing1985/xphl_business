package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.YwGatherConsumption;
import com.ruoyi.system.domain.YwRankGrossMargin;
import com.ruoyi.system.domain.YwTotalConsumGather;
import com.ruoyi.system.domain.YwTotalGrossGather;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TotalGatherMapper {

    /**
     * 以部们和媒体分组查询任务金额
     * @param ywTotalGrossGather
     * @return
     */
    public List<YwTotalGrossGather> selectTotalGatherTask(YwTotalGrossGather ywTotalGrossGather);

    /**
     * 根据条件查询VO
     * @return
     */
    public YwTotalGrossGather selectTotalGatherGrossByVO(YwTotalGrossGather ywTotalGrossGather);

    /**
     * 查询部门消耗
     * @return
     */
    public List<YwTotalConsumGather> selectTotalGatherConsum(YwTotalConsumGather ywTotalConsumGather);

    /**
     * 查询消耗排名
     *
     * @return 消耗排名
     */
    public List<YwGatherConsumption> selectRankConsumptionList(YwGatherConsumption ywGatherConsumption);

    /**
     * 查询毛利排名
     *
     * @return 毛利排名
     */
    public List<YwRankGrossMargin> selectRankGrossMarginList(YwRankGrossMargin ywRankGrossMargin);

}
