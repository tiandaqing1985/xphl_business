package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwConsumption;
import com.ruoyi.system.domain.ywArrearage.*;
import com.ruoyi.system.mapper.SysDeptMapper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.YwArrearageMapper;
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

    //显示时此数组中的部门值对应记录在下方一起显示
    private static String[] below = new String[]{
            "其他SEM", "精英", "悦维", "SEO", "任一鸣",
            "其他SEM合计", "精英合计", "悦维合计", "SEO合计", "任一鸣合计"
    };

    @Autowired
    private YwArrearageMapper ywArrearageMapper;

    @Autowired
    private SysDeptMapper sysDeptMapper;

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

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        //查询是否重复的条件VO
        YwArrearage selectVO = new YwArrearage();
        for (YwArrearage ywArrearage : ywArrearageList) {
            try {
                //计算实时应收金额
                if (ywArrearage.getNotReceiveAmt() == null) {
                    ywArrearage.setNotReceiveAmt(BigDecimal.ZERO);
                }
                if (ywArrearage.getOverdueAmt() == null) {
                    ywArrearage.setOverdueAmt(BigDecimal.ZERO);
                }
                ywArrearage.setDueAmt(ywArrearage.getNotReceiveAmt().add(ywArrearage.getOverdueAmt()));
                //修改时间格式
                if (ywArrearage.getDueDate() != null && !ywArrearage.getDueDate().equals("")) {
                    try {
                        sdf2.parse(ywArrearage.getDueDate());
                    } catch (Exception e) {
                        ywArrearage.setDueDate(sdf2.format(sdf1.parse(ywArrearage.getDueDate())));
                    }
                }
                if (ywArrearage.getPlanReturnDate() != null && !ywArrearage.getPlanReturnDate().equals("")) {
                    try {
                        sdf2.parse(ywArrearage.getPlanReturnDate());
                    } catch (Exception e) {
                        ywArrearage.setPlanReturnDate(sdf2.format(sdf1.parse(ywArrearage.getPlanReturnDate())));
                    }
                }
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
    public List<SaleManagerArrearageGather> selectGatherSaleManager(YwArrearage ywArrearage, SysUser loginUser) {

        List<SaleManagerArrearageGather> list = new ArrayList<>();
        if (loginUser == null || loginUser.getUserId() == 1 || loginUser.getUserId() == 103) {//管理员和COO看所有
            list = ywArrearageMapper.selectGatherSaleManager(ywArrearage);
        } else {
            //查询数据
            List<SaleManagerArrearageGather> gatherList = ywArrearageMapper.selectGatherSaleManager(ywArrearage);
            //查询当前登陆人是哪个部门的leader
            List<SysDept> sysDepts = sysDeptMapper.selectDeptByLeader(loginUser.getUserName());
            if (sysDepts.size() != 0) {
                Map<String, String> deptMap = new HashMap<>();
                for (SysDept sysDept : sysDepts) {
                    deptMap.put(sysDept.getDeptName(), "");
                }
                //只显示leader是当前登陆人的部门信息
                for (SaleManagerArrearageGather gather : gatherList) {
                    if (deptMap.get(gather.getDeptName()) != null) {
                        list.add(gather);
                    }
                }
            } else {
                return list;
            }
        }

        //根据地区计算合计和总计
        LinkedList<SaleManagerArrearageGather> linkedList = null;
        SaleManagerArrearageGather sum = null;
        SaleManagerArrearageGather total = new SaleManagerArrearageGather();
        SaleManagerArrearageGather gather = null;
        total.setDueAmt(BigDecimal.ZERO);
        total.setArea("总计");
        total.setFirstDueAmt(BigDecimal.ZERO);
        total.setOverdueAmt(BigDecimal.ZERO);
        total.setPlanReturnAmt(BigDecimal.ZERO);
        total.setRealReturnAmt(BigDecimal.ZERO);
        Map<String, LinkedList<SaleManagerArrearageGather>> gatherMap = new HashMap<>();
        for (SaleManagerArrearageGather arrearageGather : list) {

            total.setDueAmt(total.getDueAmt().add(arrearageGather.getDueAmt()));
            total.setFirstDueAmt(total.getFirstDueAmt().add(arrearageGather.getFirstDueAmt()));
            total.setOverdueAmt(total.getOverdueAmt().add(arrearageGather.getOverdueAmt()));
            total.setPlanReturnAmt(total.getPlanReturnAmt().add(arrearageGather.getPlanReturnAmt()));
            total.setRealReturnAmt(total.getRealReturnAmt().add(arrearageGather.getRealReturnAmt()));

            linkedList = gatherMap.get(arrearageGather.getArea());
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                gatherMap.put(arrearageGather.getArea(), linkedList);
                sum = null;
            } else if (linkedList.size() == 1) {
                sum = new SaleManagerArrearageGather();
                linkedList.addLast(sum);
                sum.setArea(arrearageGather.getArea() + "合计");
                sum.setDueAmt(linkedList.getFirst().getDueAmt());
                sum.setFirstDueAmt(linkedList.getFirst().getFirstDueAmt());
                sum.setOverdueAmt(linkedList.getFirst().getOverdueAmt());
                sum.setPlanReturnAmt(linkedList.getFirst().getPlanReturnAmt());
                sum.setRealReturnAmt(linkedList.getFirst().getRealReturnAmt());
            } else {
                sum = linkedList.getLast();
            }
            if (sum != null) {
                sum.setDueAmt(sum.getDueAmt().add(arrearageGather.getDueAmt()));
                sum.setFirstDueAmt(sum.getFirstDueAmt().add(arrearageGather.getFirstDueAmt()));
                sum.setOverdueAmt(sum.getOverdueAmt().add(arrearageGather.getOverdueAmt()));
                sum.setPlanReturnAmt(sum.getPlanReturnAmt().add(arrearageGather.getPlanReturnAmt()));
                sum.setRealReturnAmt(sum.getRealReturnAmt().add(arrearageGather.getRealReturnAmt()));
                if (sum.getFirstDueAmt().compareTo(BigDecimal.ZERO) != 0) {
                    sum.setPlanReturnRate(sum.getPlanReturnAmt().divide(sum.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
                    sum.setRealReturnRate(sum.getRealReturnAmt().divide(sum.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
                } else {
                    sum.setPlanReturnRate("0.00%");
                    sum.setRealReturnRate("0.00%");
                }
            }
            linkedList.addFirst(arrearageGather);
        }

        LinkedList<SaleManagerArrearageGather> singletonLinkedList = new LinkedList<>();

        for (LinkedList<SaleManagerArrearageGather> gatherLinkedList : gatherMap.values()) {
            if (gatherLinkedList.size() == 1) {
                singletonLinkedList.addLast(gatherLinkedList.getFirst());
            } else {
                while (gatherLinkedList.size() > 0) {
                    gather = gatherLinkedList.removeLast();
                    singletonLinkedList.addFirst(gather);
                }
            }
        }
        if (total.getFirstDueAmt().compareTo(BigDecimal.ZERO) != 0) {
            total.setPlanReturnRate(total.getPlanReturnAmt().divide(total.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
            total.setRealReturnRate(total.getRealReturnAmt().divide(total.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
        } else {
            total.setPlanReturnRate("0.00%");
        }
        singletonLinkedList.addLast(total);

        singletonLinkedList = adjustSoftSaleManager(singletonLinkedList);
        return singletonLinkedList;

    }

    /**
     * 其他SEM、精英、悦维、SEO、任一鸣顺序往下调，其他的都往上调
     *
     * @param list 要处理的数据
     * @return
     */
    private LinkedList<SaleManagerArrearageGather> adjustSoftSaleManager(LinkedList<SaleManagerArrearageGather> list) {
        LinkedList<SaleManagerArrearageGather> upLinkedList = new LinkedList<SaleManagerArrearageGather>();
        LinkedList<SaleManagerArrearageGather> downLinkedList = new LinkedList<SaleManagerArrearageGather>();
        SaleManagerArrearageGather gather = null;
        SaleManagerArrearageGather totalGather = null;
        boolean isDown = false;
        //总计的记录
        if (list.size() > 0) {
            totalGather = list.removeLast();
        }
        while (list.size() > 0) {
            gather = list.removeLast();
            isDown = false;
            for (String area : below) {
                if (area.equals(gather.getArea())) {
                    isDown = true;
                    break;
                }
            }
            if (isDown) {
                downLinkedList.addFirst(gather);
            } else {
                upLinkedList.addFirst(gather);
            }
        }
        upLinkedList.addAll(downLinkedList);
        upLinkedList.addLast(totalGather);
        return upLinkedList;
    }

    /**
     * 查询汇总表-按客户
     *
     * @param ywArrearage 查询条件
     * @return
     */
    public List<CustomerArrearageGather> selectGatherCustomer(YwArrearage ywArrearage) {
        List<CustomerArrearageGather> list = ywArrearageMapper.selectGatherCustomer(ywArrearage);
        //根据地区计算合计和总计
        LinkedList<CustomerArrearageGather> linkedList = null;
        CustomerArrearageGather sum = null;
        CustomerArrearageGather total = new CustomerArrearageGather();
        CustomerArrearageGather gather = null;
        total.setDueAmt(BigDecimal.ZERO);
        total.setArea("总计");
        total.setDueAmt(BigDecimal.ZERO);
        total.setFirstDueAmt(BigDecimal.ZERO);
        total.setOverdueAmt(BigDecimal.ZERO);
        total.setPlanReturnAmt(BigDecimal.ZERO);
        total.setRealReturnAmt(BigDecimal.ZERO);
        total.setNotReceiveAmt(BigDecimal.ZERO);
        total.setPlanReturnAmtH(BigDecimal.ZERO);
        total.setPlanReturnAmtL(BigDecimal.ZERO);
        total.setPlanReturnAmtM(BigDecimal.ZERO);
        Map<String, LinkedList<CustomerArrearageGather>> gatherMap = new HashMap<>();
        for (CustomerArrearageGather arrearageGather : list) {

            total.setDueAmt(total.getDueAmt().add(arrearageGather.getDueAmt()));
            total.setFirstDueAmt(total.getFirstDueAmt().add(arrearageGather.getFirstDueAmt()));
            total.setOverdueAmt(total.getOverdueAmt().add(arrearageGather.getOverdueAmt()));
            total.setPlanReturnAmt(total.getPlanReturnAmt().add(arrearageGather.getPlanReturnAmt()));
            total.setRealReturnAmt(total.getRealReturnAmt().add(arrearageGather.getRealReturnAmt()));

            linkedList = gatherMap.get(arrearageGather.getArea());
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                gatherMap.put(arrearageGather.getArea(), linkedList);
                sum = null;
            } else if (linkedList.size() == 1) {
                sum = new CustomerArrearageGather();
                linkedList.addLast(sum);
                sum.setArea(arrearageGather.getArea() + "合计");
                sum.setDueAmt(linkedList.getFirst().getDueAmt());
                sum.setFirstDueAmt(linkedList.getFirst().getFirstDueAmt());
                sum.setOverdueAmt(linkedList.getFirst().getOverdueAmt());
                sum.setPlanReturnAmt(linkedList.getFirst().getPlanReturnAmt());
                sum.setRealReturnAmt(linkedList.getFirst().getRealReturnAmt());
                sum.setNotReceiveAmt(linkedList.getFirst().getNotReceiveAmt());
                sum.setPlanReturnAmtH(linkedList.getFirst().getPlanReturnAmtH());
                sum.setPlanReturnAmtL(linkedList.getFirst().getPlanReturnAmtL());
                sum.setPlanReturnAmtM(linkedList.getFirst().getPlanReturnAmtM());
            } else {
                sum = linkedList.getLast();
            }
            if (sum != null) {
                sum.setDueAmt(arrearageGather.getDueAmt().add(sum.getDueAmt()));
                sum.setFirstDueAmt(arrearageGather.getFirstDueAmt().add(sum.getFirstDueAmt()));
                sum.setOverdueAmt(arrearageGather.getOverdueAmt().add(sum.getOverdueAmt()));
                sum.setPlanReturnAmt(arrearageGather.getPlanReturnAmt().add(sum.getPlanReturnAmt()));
                sum.setRealReturnAmt(arrearageGather.getRealReturnAmt().add(sum.getRealReturnAmt()));
                sum.setNotReceiveAmt(arrearageGather.getNotReceiveAmt().add(sum.getNotReceiveAmt()));
                sum.setPlanReturnAmtH(arrearageGather.getPlanReturnAmtH().add(sum.getPlanReturnAmtH()));
                sum.setPlanReturnAmtL(arrearageGather.getPlanReturnAmtL().add(sum.getPlanReturnAmtL()));
                sum.setPlanReturnAmtM(arrearageGather.getPlanReturnAmtM().add(sum.getPlanReturnAmtM()));
                if (sum.getFirstDueAmt().compareTo(BigDecimal.ZERO) != 0) {
                    sum.setPlanReturnRate(sum.getPlanReturnAmt().divide(sum.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
                    sum.setRealReturnRate(sum.getRealReturnAmt().divide(sum.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
                } else {
                    sum.setPlanReturnRate("0.00%");
                }
            }
            linkedList.addFirst(arrearageGather);
        }

        LinkedList<CustomerArrearageGather> singletonLinkedList = new LinkedList<>();

        for (LinkedList<CustomerArrearageGather> gatherLinkedList : gatherMap.values()) {
            if (gatherLinkedList.size() == 1) {
                singletonLinkedList.addLast(gatherLinkedList.getFirst());
            } else {
                while (gatherLinkedList.size() > 0) {
                    gather = gatherLinkedList.removeLast();
                    singletonLinkedList.addFirst(gather);
                }
            }
        }
        if (total.getFirstDueAmt().compareTo(BigDecimal.ZERO) != 0) {
            total.setPlanReturnRate(total.getPlanReturnAmt().divide(total.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
            total.setRealReturnRate(total.getRealReturnAmt().divide(total.getFirstDueAmt(), 6, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100L)).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
        } else {
            total.setPlanReturnRate("0.00%");
            total.setRealReturnRate("0.00%");
        }
        singletonLinkedList.addLast(total);
        singletonLinkedList = adjustSoftCustomer(singletonLinkedList);
        return singletonLinkedList;
    }

    /**
     * 其他SEM、精英、悦维、SEO、任一鸣顺序往下调，其他的都往上调
     *
     * @param list 要处理的数据
     * @return
     */
    private LinkedList<CustomerArrearageGather> adjustSoftCustomer(LinkedList<CustomerArrearageGather> list) {
        LinkedList<CustomerArrearageGather> upLinkedList = new LinkedList<CustomerArrearageGather>();
        LinkedList<CustomerArrearageGather> downLinkedList = new LinkedList<CustomerArrearageGather>();
        CustomerArrearageGather gather = null;
        CustomerArrearageGather totalGather = null;
        String sumArea = null;
        boolean isDown = false;
        //总计的记录
        if (list.size() > 0) {
            totalGather = list.removeLast();
        }
        while (list.size() > 0) {
            gather = list.removeLast();
            isDown = false;
            for (String area : below) {
                if (area.equals(gather.getArea())) {
                    isDown = true;
                    break;
                }
            }
            if (isDown) {
                downLinkedList.addFirst(gather);
            } else {
                upLinkedList.addFirst(gather);
            }
        }
        upLinkedList.addAll(downLinkedList);
        upLinkedList.addLast(totalGather);
        return upLinkedList;
    }

    /**
     * 查询实际应收账款回款率排名
     *
     * @param ywArrearage 查询条件
     * @return
     */
    public List<ReturnRateRank> selectRealReturnRateRank(YwArrearage ywArrearage) {
        return ywArrearageMapper.selectRealReturnRateRank(ywArrearage);
    }

    /**
     * 查询回款情况
     *
     * @param ywArrearage 查询条件
     * @return
     */
    public List<ReturnSituation> selectReturnSituation(YwArrearage ywArrearage) {
        return ywArrearageMapper.selectReturnSituation(ywArrearage);
    }

}
