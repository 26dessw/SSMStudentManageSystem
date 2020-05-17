package com.sise.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sise.pojo.User;

//对管理员用户进行crud操作
@Repository
public interface UserDao {

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
