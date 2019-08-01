package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.YwGrossMargins;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwTaskMapper;
import com.ruoyi.system.domain.YwTask;
import com.ruoyi.system.service.IYwTaskService;
import com.ruoyi.common.core.text.Convert;

/**
 * 任务 服务层实现
 *
 * @author ruoyi
 * @date 2019-08-01
 */
@Service
public class YwTaskServiceImpl implements IYwTaskService {

    private static final Logger log = LoggerFactory.getLogger(YwTaskServiceImpl.class);

    @Autowired
    private YwTaskMapper ywTaskMapper;

    /**
     * 查询任务信息
     *
     * @param id 任务ID
     * @return 任务信息
     */
    @Override
    public YwTask selectYwTaskById(Long id) {
        return ywTaskMapper.selectYwTaskById(id);
    }

    /**
     * 查询任务列表
     *
     * @param ywTask 任务信息
     * @return 任务集合
     */
    @Override
    public List<YwTask> selectYwTaskList(YwTask ywTask) {
        return ywTaskMapper.selectYwTaskList(ywTask);
    }

    /**
     * 新增任务
     *
     * @param ywTask 任务信息
     * @return 结果
     */
    @Override
    public int insertYwTask(YwTask ywTask) {
        return ywTaskMapper.insertYwTask(ywTask);
    }

    /**
     * 修改任务
     *
     * @param ywTask 任务信息
     * @return 结果
     */
    @Override
    public int updateYwTask(YwTask ywTask) {
        return ywTaskMapper.updateYwTask(ywTask);
    }

    /**
     * 删除任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYwTaskByIds(String ids) {
        return ywTaskMapper.deleteYwTaskByIds(Convert.toStrArray(ids));
    }

    /**
     * 导入任务
     *
     * @param ywTasks
     * @param updateSupport
     * @param operName
     * @return
     */
    @Override
    public String importYwTask(List<YwTask> ywTasks, boolean updateSupport, String operName) {

        if (StringUtils.isNull(ywTasks) || ywTasks.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (YwTask ywTask : ywTasks) {
            try {
                ywTask.setCreateBy(operName);
                ywTask.setCreateTime(DateUtils.getNowDate());
                this.insertYwTask(ywTask);
                successNum++;
                successMsg.append("<br/>" + successNum + "、销售经理 " + ywTask.getSaleManager() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、销售经理 " + ywTask.getSaleManager() + " 导入失败：";
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
