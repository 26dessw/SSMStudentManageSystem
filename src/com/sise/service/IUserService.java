package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sise.pojo.User;

//管理员用户service层接口
@Service
public interface IUserService {

	//查询管理员用户，用于登录
	public User findByUserName(String username);
	
	//添加管理员用户
	public int add(User user);
	
	//查询管理员用户列表
	public List<User> findList(Map<String,Object> queryMap);
	
	//查询数据的条数  
	public int getTotal(Map<String,Object> queryMap);
	
	//修改管理员用户信息
	public int edit(User user);
	
	//批量删除管理员用户
	public int delete(String ids);
}
