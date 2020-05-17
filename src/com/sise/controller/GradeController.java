package com.sise.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;
import com.sise.pojo.Grade;
import com.sise.pojo.Page;
import com.sise.pojo.User;
import com.sise.service.IGradeService;

//�꼶������

@Controller
@RequestMapping("/grade")
public class GradeController {

	@Autowired
	private IGradeService gradeService;
	
	//��ת���꼶�б�������
		@RequestMapping(value="/list",method=RequestMethod.GET)
		public ModelAndView list(ModelAndView model) {
			model.setViewName("grade/grade_list");
			return model;
		}
		
		
		//��ȡ�꼶�б�
		@RequestMapping(value="/get_list",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> getList(
	@RequestParam(value="name",required=false,defaultValue="")String name,
	              Page page){
			Map<String, Object> ret=new HashMap<String, Object>();
			Map<String, Object> queryMap=new HashMap<String, Object>();
			//ǰ�˷��������а���offset��pageSize�Զ���װ��Page����
			queryMap.put("name", "%"+name+"%");
			queryMap.put("offset",page.getOffset());
			queryMap.put("pageSize", page.getRows());
			ret.put("rows", gradeService.findList(queryMap));
			ret.put("total", gradeService.getTotal(queryMap));
			return ret;
		}
		
		//����꼶
		@RequestMapping(value="/add",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, String> add(Grade grade){
			
			Map<String, String> ret=new HashMap<String, String>();
			
			//�ж��꼶�����Ƿ�Ϊ��
			if (StringUtil.isEmpty(grade.getName())) {
				ret.put("type", "error");
				ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
				return ret;
			}
			//�ж��꼶����Ƿ�ɹ�
			if (gradeService.add(grade)<=0) {
				ret.put("type", "error");
				ret.put("msg", "�꼶���ʧ�ܣ�");
				return ret;
			}
			ret.put("type", "success");
			ret.put("msg", "�꼶��ӳɹ���");
			return ret;
		}
		
		
		//�޸��꼶��Ϣ
				@RequestMapping(value="/edit",method=RequestMethod.POST)
				@ResponseBody
				public Map<String, String> edit(Grade grade){
					Map<String, String> ret=new HashMap<String, String>();
					//�ж��꼶�����Ƿ�Ϊ��
					if (StringUtil.isEmpty(grade.getName())) {	
						ret.put("type", "error");
						ret.put("msg", "�꼶���Ʋ���Ϊ�գ�");
						return ret;
					}
					//�ж��꼶�޸��Ƿ�ɹ�
					if (gradeService.edit(grade)<=0) {
						ret.put("type", "error");
						ret.put("msg", "�꼶��Ϣ�޸�ʧ�ܣ�");
						return ret;
					}
					ret.put("type", "success");
					ret.put("msg", "�꼶��Ϣ�޸ĳɹ���");
					return ret;
				}
		
		
		//ɾ���꼶
				@RequestMapping(value="/delete",method=RequestMethod.POST)
				@ResponseBody
				public Map<String, String> delete(
						@RequestParam(value="ids[]",required=true)Long[] ids){
					
					Map<String, String> ret=new HashMap<String, String>();
					//�ж��Ƿ���ѡ��ɾ�����꼶id
					if (ids==null||ids.length==0) {
						ret.put("type", "error");
						ret.put("msg", "��ѡ��Ҫɾ�������ݣ�");
						return ret;
					}
					//�ж��Ƿ�ɹ�ɾ��ѡ���꼶�������꼶�´��ڰ༶��Ϣ���޷�ɾ��
					try {
						if (gradeService.delete(com.sise.util.StringUtil.joinString(Arrays.asList(ids), ","))<=0) {
							ret.put("type", "error");
							ret.put("msg", "ɾ��ʧ�ܣ�");
							return ret;
						}
						
					} catch (Exception e) {
						ret.put("type", "error");
						ret.put("msg", "���꼶�´��ڰ༶��Ϣ������嶯��");
						return ret;
					}
					ret.put("type", "success");
					ret.put("msg", "ɾ���꼶�ɹ���");
					return ret;
					
				}
					
}
