package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sise.pojo.User;

//����Ա�û�service��ӿ�
@Service
public interface IUserService {

	//��ѯ����Ա�û������ڵ�¼
	public User findByUserName(String username);
	
	//��ӹ���Ա�û�
	public int add(User user);
	
	//��ѯ����Ա�û��б�
	public List<User> findList(Map<String,Object> queryMap);
	
	//��ѯ���ݵ�����  
	public int getTotal(Map<String,Object> queryMap);
	
	//�޸Ĺ���Ա�û���Ϣ
	public int edit(User user);
	
	//����ɾ������Ա�û�
	public int delete(String ids);
}
