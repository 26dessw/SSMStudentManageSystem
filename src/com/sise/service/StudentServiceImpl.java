package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sise.dao.ClazzDao;
import com.sise.dao.StudentDao;
import com.sise.pojo.Clazz;
import com.sise.pojo.Grade;
import com.sise.pojo.Student;

@Service
public class StudentServiceImpl implements IStudentService{

	@Autowired
	StudentDao studentDao;
	
	
	
	@Override
	public int add(Student student) {
		// TODO Auto-generated method stub
		return studentDao.add(student);
	}

	@Override
	public int edit(Student student) {
		// TODO Auto-generated method stub
		return studentDao.edit(student);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return studentDao.delete(ids);
	}

	@Override
	public List<Student> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.findList(queryMap);
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return studentDao.findAll();
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return studentDao.getTotal(queryMap);
	}

	public Student findByUserName(String username) {		
		return studentDao.findByUserName(username);
		
	}
	
	

}
