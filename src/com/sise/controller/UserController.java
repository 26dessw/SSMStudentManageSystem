package com.sise.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sise.pojo.Page;
import com.sise.pojo.User;
import com.sise.service.IUserService;

//管理员操作控制器

@Controller
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	IUserService userService;
	
	
	//跳转到管理员主界面
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model) {
		model.setViewName("user/user_list");
		return model;
	}
	
	//添加管理员用户
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret=new HashMap<String, String>();
		
		//判断添加表单传来数据是否正确
		if (user==null) {
			ret.put("type", "error");
			ret.put("msg", "表单数据绑定错误");
			return ret;
		}
		//判断用户名是否为空
		if (StringUtils.isEmpty(user.getUsername())) {
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空！");
			return ret;
		}
		//判断密码是否为空
		if (StringUtils.isEmpty(user.getPassword())) {
			ret.put("type", "error");
			ret.put("msg", "密码不能为空！");
			return ret;
		}
		
		//判断添加的用户是否存在
		User existUser=userService.findByUserName(user.getUsername());
		if (existUser!=null) {
			ret.put("type", "error");
			ret.put("msg", "该用户名已存在！");
			return ret;
		}
				
		//判断添加用户是否成功
		if (userService.add(user)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加用户失败！");
			return ret;
		}
		//添加成功
		ret.put("type", "success");
		ret.put("msg", "添加用户成功！");
		return ret;
	}
	
	
	
	//修改用户信息
		@RequestMapping(value="/edit",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> edit(User user){
			Map<String, String> ret=new HashMap<String, String>();			
			//判断添加表单传来数据是否正确
			if (user==null) {
				ret.put("type", "error");
				ret.put("msg", "表单数据绑定错误");
				return ret;
			}
			//判断用户名是否为空
			if (StringUtils.isEmpty(user.getUsername())) {
				ret.put("type", "error");
				ret.put("msg", "用户名不能为空！");
				return ret;
			}
			//判断密码是否为空
			if (StringUtils.isEmpty(user.getPassword())) {
				ret.put("type", "error");
				ret.put("msg", "密码不能为空！");
				return ret;
			}
	
			//判断修改后的用户信息是否存在
		User existUser=userService.findByUserName(user.getUsername());
		if (existUser!=null) {			
			  //判断修改后的用户信息是否存在
			  if (user.getId()!=existUser.getId()) {
					ret.put("type", "error");
					ret.put("msg", "该用户名已存在！");
					return ret;
				}				
		}
					
		
			//判断修改用户是否成功
			if (userService.edit(user)<=0) {
				ret.put("type", "error");
				ret.put("msg", "修改用户失败！");
				return ret;
			}
			//修改用户成功
			ret.put("type", "success");
			ret.put("msg", "修改用户成功！");
			return ret;
		}
	
		
		
		
	//查询所有管理员用户
	@RequestMapping(value="/get_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
@RequestParam(value="username",required=false,defaultValue="")String username,
              Page page){
		Map<String, Object> ret=new HashMap<String, Object>();
		Map<String, Object> queryMap=new HashMap<String, Object>();
		//前端返回请求中包含offset，pageSize自动封装成Page对象
		queryMap.put("username", "%"+username+"%");
		queryMap.put("offset",page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", userService.findList(queryMap));
		ret.put("total", userService.getTotal(queryMap));
		return ret;
	}
	
	
	
	
	
	
	
	//删除管理员用户
			@RequestMapping(value="/delete",method=RequestMethod.POST)
			@ResponseBody
			public Map<String, String> delete(
		@RequestParam(value="ids[]",required=true)Long[] ids){
				Map<String, String> ret=new HashMap<String, String>();
				//没选择数据时
				if (ids==null) {
					ret.put("type", "error");
					ret.put("msg", "请选择要删除的数据");
					return ret;
				}
				
				String idsString="";
				for(Long id:ids) {
					idsString+=id+",";
				}
	idsString=idsString.substring(0,idsString.length()-1);
	//进行批量删除
	if (userService.delete(idsString)<=0) {
		ret.put("type", "error");
		ret.put("msg", "删除失败");
		return ret;
	}
	            //删除成功
	            ret.put("type", "success");
	            ret.put("msg", "删除成功");
				return ret;
			}
	
	
	
}
