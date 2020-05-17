package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sise.dao.GradeDao;
import com.sise.pojo.Grade;
@Service
public class GradeServiceImpl implements IGradeService{

	
	@Autowired
	private GradeDao gradeDao;
	
	
	@Override
	public int add(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.add(grade);
	}

	@Override
	public List<Grade> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return gradeDao.getTotal(queryMap);
	}

	@Override
	public int edit(Grade grade) {
		// TODO Auto-generated method stub
		return gradeDao.edit(grade);
	}

	@Override
	public int delete(String ids) {
		// TODO Auto-generated method stub
		return gradeDao.delete(ids);
	}

	@Override
	public List<Grade> findAll() {
		// TODO Auto-generated method stub
		return gradeDao.findAll();
	}

}
