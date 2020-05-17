package com.sise.service;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sise.dao.UserDao;
import com.sise.pojo.User;


//����Ա�û�service��ʵ����

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;
	
	//��ѯ����Ա�û������ڵ�¼
	@Override
	public User findByUserName(String username) {	
		return userDao.findByUserName(username);
	}

	//��ӹ���Ա�û�
	@Override
	public int add(User user) {
		return userDao.add(user);
	}

	//��ѯ����Ա�û��б�
	@Override
	public List<User> findList(Map<String,Object> queryMap) {
		return userDao.findList(queryMap);
	}

	//��ѯ���ݵ�����  
	@Override
	public int getTotal(Map<String, Object> queryMap) {
		return userDao.getTotal(queryMap);
	}

	//�޸Ĺ���Ա�û���Ϣ
	@Override
	public int edit(User user) {	
		return userDao.edit(user);
	}

	//����ɾ������Ա�û�
	@Override
	public int delete(String ids) {
		return userDao.delete(ids);
	}
}
