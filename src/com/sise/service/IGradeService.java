package com.sise.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sise.pojo.Grade;
import com.sise.pojo.User;

//年级service层接口

@Service
public interface IGradeService {

		public int add(Grade grade);
		public List<Grade> findList(Map<String,Object> queryMap);
		public List<Grade> findAll();
		public int getTotal(Map<String,Object> queryMap);
		public int edit(Grade grade);
		public int delete(String ids);
}
