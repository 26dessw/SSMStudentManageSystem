package com.sise.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.sise.pojo.Grade;
import com.sise.pojo.Page;
import com.sise.pojo.User;
import com.sise.service.IGradeService;

//年级控制器

@Controller
@RequestMapping("/grade")
public class GradeController {

	@Autowired
	private IGradeService gradeService;
	
	//跳转到年级列表主界面
		@RequestMapping(value="/list",method=RequestMethod.GET)
		public ModelAndView list(ModelAndView model) {
			model.setViewName("grade/grade_list");
			return model;
		}
		
		
		//获取年级列表
		@RequestMapping(value="/get_list",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> getList(
	@RequestParam(value="name",required=false,defaultValue="")String name,
	              Page page){
			Map<String, Object> ret=new HashMap<String, Object>();
			Map<String, Object> queryMap=new HashMap<String, Object>();
			//前端返回请求中包含offset，pageSize自动封装成Page对象
			queryMap.put("name", "%"+name+"%");
			queryMap.put("offset",page.getOffset());
			queryMap.put("pageSize", page.getRows());
			ret.put("rows", gradeService.findList(queryMap));
			ret.put("total", gradeService.getTotal(queryMap));
			return ret;
		}
		
		//添加年级
		@RequestMapping(value="/add",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> add(Grade grade){
			
			Map<String, String> ret=new HashMap<String, String>();
			
			//判断年级名称是否为空
			if (StringUtil.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "年级名称不能为空！");
				return ret;
			}
			//判断年级添加是否成功
			if (gradeService.add(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "年级添加失败！");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "年级添加成功！");
			return ret;
		}
		
		
		//修改年级信息
				@RequestMapping(value="/edit",method=RequestMethod.POST)
				@ResponseBody
				public Map<String, String> edit(Grade grade){
					Map<String, String> ret=new HashMap<String, String>();
					//判断年级名称是否为空
					if (StringUtil.isEmpty(grade.getName())) {	
						ret.put("type", "error");
						ret.put("msg", "年级名称不能为空！");
						return ret;
					}
					//判断年级修改是否成功
					if (gradeService.edit(grade)<=0) {
						ret.put("type", "error");
						ret.put("msg", "年级信息修改失败！");
						return ret;
					}
					ret.put("type", "success");
					ret.put("msg", "年级信息修改成功！");
					return ret;
				}
		
		
		//删除年级
				@RequestMapping(value="/delete",method=RequestMethod.POST)
				@ResponseBody
				public Map<String, String> delete(
						@RequestParam(value="ids[]",required=true)Long[] ids){
					
					Map<String, String> ret=new HashMap<String, String>();
					//判断是否有选择删除的年级id
					if (ids==null||ids.length==0) {
						ret.put("type", "error");
						ret.put("msg", "请选择要删除的数据！");
						return ret;
					}
					//判断是否成功删除选择年级，若该年级下存在班级信息则无法删除
					try {
						if (gradeService.delete(com.sise.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
							ret.put("type", "error");
							ret.put("msg", "删除失败！");
							return ret;
						}
						
					} catch (Exception e) {
						ret.put("type", "error");
						ret.put("msg", "该年级下存在班级信息，请勿冲动！");
						return ret;
					}
					ret.put("type", "success");
					ret.put("msg", "删除年级成功！");
					return ret;
					
				}
					
}
