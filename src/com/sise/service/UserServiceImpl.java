package com.sise.service;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sise.dao.UserDao;
import com.sise.pojo.User;


//管理员用户service层实现类

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;
	
	//查询管理员用户，用于登录
	@Override
	public User findByUserName(String username) {	
		return userDao.findByUserName(username);
	}

	//添加管理员用户
	@Override
	public int add(User user) {
		return userDao.add(user);
	}

	//查询管理员用户列表
	@Override
	public List<User> findList(Map<String,Object> queryMap) {
		return userDao.findList(queryMap);
	}

	//查询数据的条数  
	@Override
	public int getTotal(Map<String, Object> queryMap) {
		return userDao.getTotal(queryMap);
	}

	//修改管理员用户信息
	@Override
	public int edit(User user) {	
		return userDao.edit(user);
	}

	//批量删除管理员用户
	@Override
	public int delete(String ids) {
		return userDao.delete(ids);
	}
}
