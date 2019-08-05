package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.domain.YwGrossMargins;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.YwTask;
import com.ruoyi.system.service.IYwTaskService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-01
 */
@Controller
@RequestMapping("/system/ywTask")
public class YwTaskController extends BaseController
{
    private String prefix = "system/ywTask";
	
	@Autowired
	private IYwTaskService ywTaskService;
	
	@RequiresPermissions("system:ywTask:view")
	@GetMapping()
	public String ywTask()
	{
	    return prefix + "/ywTask";
	}
	
	/**
	 * 查询任务列表
	 */
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(YwTask ywTask)
	{
		startPage();
        List<YwTask> list = ywTaskService.selectYwTaskList(ywTask);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出任务列表
	 */
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(YwTask ywTask)
    {
    	List<YwTask> list = ywTaskService.selectYwTaskList(ywTask);
        ExcelUtil<YwTask> util = new ExcelUtil<YwTask>(YwTask.class);
        return util.exportExcel(list, "任务");
    }
	
	/**
	 * 新增任务
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存任务
	 */
	@Log(title = "任务", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(YwTask ywTask)
	{
		ywTask.setCreateTime(DateUtils.getNowDate());
		String operName = ShiroUtils.getSysUser().getLoginName();
		ywTask.setCreateBy(operName);
		return toAjax(ywTaskService.insertYwTask(ywTask));
	}

	/**
	 * 修改任务
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		YwTask ywTask = ywTaskService.selectYwTaskById(id);
		mmap.put("ywTask", ywTask);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存任务
	 */
	@RequiresPermissions("system:ywTask:edit")
	@Log(title = "任务", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(YwTask ywTask)
	{
		ywTask.setUpdateTime(DateUtils.getNowDate());
		String operName = ShiroUtils.getSysUser().getLoginName();
		ywTask.setUpdateBy(operName);
		return toAjax(ywTaskService.updateYwTask(ywTask));
	}
	
	/**
	 * 删除任务
	 */
	@Log(title = "任务", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ywTaskService.deleteYwTaskByIds(ids));
	}

	/**
	 * 导入
	 * @param file
	 * @param updateSupport
	 * @return
	 * @throws Exception
	 */
	@Log(title = "任务", businessType = BusinessType.IMPORT)
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
	{
		ExcelUtil<YwTask> util = new ExcelUtil<YwTask>(YwTask.class);
		List<YwTask> ywTasks = util.importExcel(file.getInputStream(),0,1);
		String operName = ShiroUtils.getSysUser().getLoginName();
		String message = ywTaskService.importYwTask(ywTasks, updateSupport, operName);
		return AjaxResult.success(message);
	}

	/**
	 * 导入模板
	 * @return
	 */
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate()
	{
		ExcelUtil<YwTask> util = new ExcelUtil<YwTask>(YwTask.class);
		return util.importTemplateExcel("任务");
	}
}
