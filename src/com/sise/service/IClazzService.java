package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sise.pojo.Clazz;
import com.sise.pojo.Grade;
import com.sise.pojo.User;

//年级service层接口

@Service
public interface IClazzService {

	public int add(Clazz clazz);
	public int edit(Clazz clazz);
	public int delete(String ids);
	public List<Clazz> findList(Map<String,Object> queryMap);
	public List<Clazz> findAll();
	public int getTotal(Map<String,Object> queryMap);
		
	
}
