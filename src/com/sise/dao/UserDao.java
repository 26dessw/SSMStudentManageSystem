package com.sise.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sise.pojo.User;

//�Թ���Ա�û�����crud����
@Repository
public interface UserDao {

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
