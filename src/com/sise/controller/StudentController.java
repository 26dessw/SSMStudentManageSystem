package com.sise.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sise.pojo.Clazz;
import com.sise.pojo.Grade;
import com.sise.pojo.Page;
import com.sise.pojo.Student;
import com.sise.service.IClazzService;
import com.sise.service.IGradeService;
import com.sise.service.IStudentService;
import com.sise.util.StringUtil;


/**
 * 学生信息管理
 * @author llq
 *
 */

@RequestMapping("/student")
@Controller
public class StudentController {
	
	@Autowired
	private IStudentService studentService;
	@Autowired
	private IClazzService clazzService;
	
	
	/**
	 * 查询年级并跳转到学生列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("student/student_list");
		//查询所有年级
		List<Clazz> clazzList=clazzService.findAll();
		model.addObject("clazzList",clazzList );
		model.addObject("clazzListJson",JSONArray.fromObject(clazzList));
		return model;
	}
	
	
	
	
	
	
	
	/**
	 * 获取学生列表
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/get_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(value="name",required=false,defaultValue="")String name,
			@RequestParam(value="clazzId",required=false)Long clazzId,
			HttpServletRequest request,
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", "%"+name+"%");
		Object attribute=request.getSession().getAttribute("userType");
		if ("2".equals(attribute.toString())) {
			//说明是学生
			Student loginedStudent=(Student)request.getSession().getAttribute("user");
			queryMap.put("username", "%"+loginedStudent.getUsername()+"%");
		}
	
		if(clazzId != null){
			queryMap.put("clazzId", clazzId);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", studentService.findList(queryMap));
		ret.put("total", studentService.getTotal(queryMap));
		return ret;
	}
	
	
	
	
	
	
	
	/**
	 * 修改学生信息
	 * @param clazz
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Student student){
		Map<String, String> ret = new HashMap<String, String>();
	//判断学生名称是否为空
		if(StringUtils.isEmpty(student.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空！");
			return ret;
		}
		//判断密码是否为空
		if(StringUtils.isEmpty(student.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		//判断年级是否为空
		if(student.getClazzId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级！");
			return ret;
		}
		//判断修改后的学生姓名是否存在
		if (isExist(student.getUsername(), student.getId())) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		//设置学号格式
		student.setSn(StringUtil.generateSn("S", ""));
		
		//判断学生修改信息是否成功
		if(studentService.edit(student) <= 0){
			ret.put("type", "error");
			ret.put("msg", "学生修改失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生修改成功！");
		return ret;
	}
	
	
	/**
	 * 添加学生信息
	 * @param clazz
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Student student){
		Map<String, String> ret = new HashMap<String, String>();
		//判断填写的学生姓名是否为空
		if(StringUtils.isEmpty(student.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "学生姓名不能为空！");
			return ret;
		}
		//判断填写的登陆密码是否为空
		if(StringUtils.isEmpty(student.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		//判断年级是否为空
		if(student.getClazzId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属班级！");
			return ret;
		}
		//判断添加的学生信息是否存在
		if (isExist(student.getUsername(), null)) {
			ret.put("type", "error");
			ret.put("msg", "该姓名已存在！");
			return ret;
		}
		student.setSn(StringUtil.generateSn("S", ""));
		
		//判断学生添加是否成功
		if(studentService.add(student) <= 0){
			ret.put("type", "error");
			ret.put("msg", "学生添加失败！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生添加成功！");
		return ret;
	}
	
	
	
	
	
	
	/**
	 * 删除学生信息
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
		
		//尝试删除选择的学生列表，若该学生下存在其他信息，则无法删除
		try {
			if(studentService.delete(StringUtil.joinString(Arrays.asList(ids), ",")) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败！");
				return ret;
			}
		} catch (Exception e) {			
			ret.put("type", "error");
			ret.put("msg", "该学生下存在其他信息，请勿冲动！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "学生删除成功！");
		return ret;
	}
	
	
	
	
	
	
	//文件上传到服务器文件夹
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo,
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException{
		Map<String, String> ret = new HashMap<String, String>();
	
		//没有选择上传的图片
		if(photo == null){
			//文件没有选择
			ret.put("type", "error");
			ret.put("msg", "请选择文件！");
			return ret;
		}
		if(photo.getSize() > 10485760){
			//文件没有选择
			ret.put("type", "error");
			ret.put("msg", "文件大小超过10M，请上传小于10M的图片！");
			return ret;
		}
		//给上传图片命名并检查图片的格式
		String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1,photo.getOriginalFilename().length());
		if(!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())){
			ret.put("type", "error");
			ret.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg格式的图片！");
			return ret;
		}
		//创建图片上传的文件夹
		String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
		System.out.println(savePath);
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()){
			savePathFile.mkdir();//如果不存在，则创建一个文件夹upload
		}
		
		//把文件转存到这个文件夹下
		String filename = new Date().getTime() + "." + suffix;
		photo.transferTo(new File(savePath + filename ));
		ret.put("type", "success");
		ret.put("msg", "图片上传成功！");
		ret.put("src", request.getServletContext().getContextPath() + "/upload/" + filename);
		return ret;
	}
	
	//判断该学生是否存在
	public boolean isExist(String username,Long id) {
		Student student=studentService.findByUserName(username);
		if (student!=null) {
			if (id==null) {
				return true;
			}
			if (student.getId().longValue()!=id.longValue()) {
				return true;
			}
		}
		
		return false;
	}
	
}
