package com.sise.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sise.pojo.Student;
import com.sise.pojo.User;
import com.sise.service.IStudentService;
import com.sise.service.IUserService;





@RequestMapping("/system")
@Controller
public class SystemController {

	@Autowired 
	IUserService userService;
	@Autowired 
	IStudentService studentService;
	
	//登陆成功后跳转到system/index.jsp
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		model.setViewName("system/index");
		return model;
	}
	
	
	//跳转到登陆界面
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.setViewName("system/login");
		return model;
	}
	
	
	
	//注销退出
		@RequestMapping(value = "/login_out",method=RequestMethod.GET)
		public String login_out(HttpServletRequest request){
			request.getSession().setAttribute("user", null);
			return "redirect:login";
		}
	
	
	
	
	/**
	 * 登录表单提交
	 * @return
	 */
	@RequestMapping(value = "/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(
			@RequestParam(value="username",required=true) String username,
			@RequestParam(value="password",required=true) String password,
			@RequestParam(value="vcode",required=true) String vcode,
			@RequestParam(value="type",required=true) int type,
			HttpServletRequest request
			){
		
		
		Map<String, String> ret = new HashMap<String, String>();
		
		//判断用户名是否为空
		if(StringUtils.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空!");
			return ret;
		}
		//判断密码是否为空
		if(StringUtils.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "密码不能为空!");
			return ret;
		}
		//判断验证码是否为空
		if(StringUtils.isEmpty(vcode)){
			ret.put("type", "error");
			ret.put("msg", "验证码不能为空!");
			return ret;
		}
		//判断验证码是否还存在
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		if(StringUtils.isEmpty(loginCpacha)){
			ret.put("type", "error");
			ret.put("msg", "长时间未操作，会话已失效，请刷新后重试!");
			return ret;
		}
		//判断验证码是否正确
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "验证码错误!");
			return ret;
		}
		//清除验证码
		request.getSession().setAttribute("loginCpacha", null);
		
		
		//从数据库中去查找用户
		
		//管理员登录
		if(type == 1){
			User user = userService.findByUserName(username);
		//判断管理员用户是否存在
			if(user == null){
				ret.put("type", "error");
				ret.put("msg", "不存在该用户!");
				return ret;
			}
			//判断密码是否正确
			if(!password.equals(user.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "密码错误!");
				return ret;
			}
			//向前端返回查询到的user对象
			request.getSession().setAttribute("user", user);
		}
		
		
		
		//学生登录
		if(type == 2){
		
			//学生
			Student student=studentService.findByUserName(username);
			//判断该学生是否存在
			if(student == null){
				ret.put("type", "error");
				ret.put("msg", "不存在该学生!");
				return ret;
			}
			//判断密码是否正确
			if(!password.equals(student.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "密码错误!");
				return ret;
			}
			//向前端返回查询到的student对象
			request.getSession().setAttribute("user", student);
		}
		
		
		//登陆成功，并返回用户的角色类型
		request.getSession().setAttribute("userType", type);
		ret.put("type", "success");
		ret.put("msg", "登录成功!");
		return ret;
	}
	
	
	
	
	/**
	 * 显示 验证码
	 * @param request
	 * @param vl
	 * @param w
	 * @param h
	 * @param response
	 */
	@RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
	public void getCpacha(HttpServletRequest request,
			@RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
			@RequestParam(value="w",defaultValue="98",required=false) Integer w,
			@RequestParam(value="h",defaultValue="33",required=false) Integer h,
			HttpServletResponse response){
		com.sise.util.CpachaUtil cpachaUtil = new com.sise.util.CpachaUtil(vl, w, h);
		String generatorVCode = cpachaUtil.generatorVCode();
		request.getSession().setAttribute("loginCpacha", generatorVCode);
		BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
