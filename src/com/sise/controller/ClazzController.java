package com.sise.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sise.pojo.Clazz;
import com.sise.pojo.Grade;
import com.sise.pojo.Page;
import com.sise.service.IClazzService;
import com.sise.service.IGradeService;
import com.sise.util.StringUtil;


/**
 * 班级信息管理
 * @author llq
 *
 */

@RequestMapping("/clazz")
@Controller
public class ClazzController {
	
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private IClazzService clazzService;
	
	
	/**
	 * 查询年级并跳转到班级列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("clazz/clazz_list");
		//查询所有年级
		List<Grade> findAll = gradeService.findAll();
		model.addObject("gradeList",findAll );
		model.addObject("gradeListJson",JSONArray.fromObject(findAll));
		return model;
	}
	
	
	
	
	
	
	
	/**
	 * 获取班级列表
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/get_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value="name",required=false,defaultValue="")String name,
			@RequestParam(value="gradeId",required=false)Long gradeId,
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", "%"+name+"%");
		if(gradeId != null){
			queryMap.put("gradeId", gradeId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", clazzService.findList(queryMap));
		ret.put("total", clazzService.getTotal(queryMap));
		return ret;
	}
	
	
	
	
	
	
	
	/**
	 * 修改班级信息
	 * @param clazz
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Clazz clazz){
		Map<String, String> ret = new HashMap<String, String>();
		//判断填写的班级是否为空
		if(StringUtils.isEmpty(clazz.getName())){
			ret.put("type", "error");
			ret.put("msg", "班级名称不能为空！");
			return ret;
		}
		//判断填写的年级是否为空
		if(clazz.getGradeId() == null){
			ret.put("type", "error");
			ret.put("msg", "所属年级不能为空！");
			return ret;
		}
		//判断班级修改是否成功
		if(clazzService.edit(clazz) <= 0){
			ret.put("type", "error");
			ret.put("msg", "班级修改失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级修改成功！");
		return ret;
	}
	
	
	/**
	 * 添加班级信息
	 * @param clazz
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Clazz clazz){
		Map<String, String> ret = new HashMap<String, String>();
		//判断填写的班级是否为空
		if(StringUtils.isEmpty(clazz.getName())){
			ret.put("type", "error");
			ret.put("msg", "班级名称不能为空！");
			return ret;
		}
		//判断年级是否为空
		if(clazz.getGradeId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属年级！");
			return ret;
		}
		//判断班级添加是否成功
		if(clazzService.add(clazz) <= 0){
			ret.put("type", "error");
			ret.put("msg", "班级添加失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级添加成功！");
		return ret;
	}
	
	
	
	
	
	
	/**
	 * 删除班级信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(
			@RequestParam(value="ids[]",required=true) Long[] ids
			){
		Map<String, String> ret = new HashMap<String, String>();
		//没有选择删除项
		if(ids == null || ids.length == 0){
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的数据！");
			return ret;
		}
		
		//尝试删除选择的年级列表，若该年级下存在班级，则无法删除
		try {
			if(clazzService.delete(StringUtil.joinString(Arrays.asList(ids), ",")) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败！");
				return ret;
			}
		} catch (Exception e) {			
			ret.put("type", "error");
			ret.put("msg", "该班级下存在学生信息，请勿冲动！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "班级删除成功！");
		return ret;
	}
	
}
