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
	
	//��½�ɹ�����ת��system/index.jsp
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView model){
		model.setViewName("system/index");
		return model;
	}
	
	
	//��ת����½����
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.setViewName("system/login");
		return model;
	}
	
	
	
	//ע���˳�
		@RequestMapping(value = "/login_out",method=RequestMethod.GET)
		public String login_out(HttpServletRequest request){
			request.getSession().setAttribute("user", null);
			return "redirect:login";
		}
	
	
	
	
	/**
	 * ��¼�����ύ
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
		
		//�ж��û����Ƿ�Ϊ��
		if(StringUtils.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "�û�������Ϊ��!");
			return ret;
		}
		//�ж������Ƿ�Ϊ��
		if(StringUtils.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "���벻��Ϊ��!");
			return ret;
		}
		//�ж���֤���Ƿ�Ϊ��
		if(StringUtils.isEmpty(vcode)){
			ret.put("type", "error");
			ret.put("msg", "��֤�벻��Ϊ��!");
			return ret;
		}
		//�ж���֤���Ƿ񻹴���
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		if(StringUtils.isEmpty(loginCpacha)){
			ret.put("type", "error");
			ret.put("msg", "��ʱ��δ�������Ự��ʧЧ����ˢ�º�����!");
			return ret;
		}
		//�ж���֤���Ƿ���ȷ
		if(!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "��֤�����!");
			return ret;
		}
		//�����֤��
		request.getSession().setAttribute("loginCpacha", null);
		
		
		//�����ݿ���ȥ�����û�
		
		//����Ա��¼
		if(type == 1){
			User user = userService.findByUserName(username);
		//�жϹ���Ա�û��Ƿ����
			if(user == null){
				ret.put("type", "error");
				ret.put("msg", "�����ڸ��û�!");
				return ret;
			}
			//�ж������Ƿ���ȷ
			if(!password.equals(user.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "�������!");
				return ret;
			}
			//��ǰ�˷��ز�ѯ����user����
			request.getSession().setAttribute("user", user);
		}
		
		
		
		//ѧ����¼
		if(type == 2){
		
			//ѧ��
			Student student=studentService.findByUserName(username);
			//�жϸ�ѧ���Ƿ����
			if(student == null){
				ret.put("type", "error");
				ret.put("msg", "�����ڸ�ѧ��!");
				return ret;
			}
			//�ж������Ƿ���ȷ
			if(!password.equals(student.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "�������!");
				return ret;
			}
			//��ǰ�˷��ز�ѯ����student����
			request.getSession().setAttribute("user", student);
		}
		
		
		//��½�ɹ����������û��Ľ�ɫ����
		request.getSession().setAttribute("userType", type);
		ret.put("type", "success");
		ret.put("msg", "��¼�ɹ�!");
		return ret;
	}
	
	
	
	
	/**
	 * ��ʾ ��֤��
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