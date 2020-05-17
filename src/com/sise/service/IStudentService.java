package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sise.pojo.Clazz;
import com.sise.pojo.Grade;
import com.sise.pojo.Student;
import com.sise.pojo.User;

//年级service层接口

@Service
public interface IStudentService {

	public Student findByUserName(String username);
	public int add(Student student);
	public int edit(Student student);
	public int delete(String ids);
	public List<Student> findList(Map<String,Object> queryMap);
	public List<Student> findAll();
	public int getTotal(Map<String,Object> queryMap);
		
	
}
