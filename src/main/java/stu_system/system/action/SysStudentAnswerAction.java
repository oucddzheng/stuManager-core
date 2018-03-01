package stu_system.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysStudentAnswerModel;
import stu_system.system.model.SysStudentQuestionnaireRelModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysStudentAnswerService;
import stu_system.system.service.SysStudentQuestionnaireRelService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;

@Controller("sysStudentAnswerAction")
@RequestMapping("/sysStudentAnswerAction")
public class SysStudentAnswerAction extends BaseAction {
	
	@Autowired
	private SysStudentQuestionnaireRelService sysStudentQuestionnaireRelService;
	@Autowired
	private SysStudentAnswerService sysStudentAnswerService;
	@RequestMapping("/saveStuQuestionnaireRelAndAnswer.do")
	public void saveStuQuestionnaireRelAndAnswer(String qestionnaireCode , String questionCodeAndAnswer ,HttpServletRequest request , HttpServletResponse response) throws Exception {
		System.out.println(qestionnaireCode);
		System.out.println(questionCodeAndAnswer);
		if(FormatEmpty.isEmpty(qestionnaireCode)) {
			System.out.println("�ʾ��codeֵΪ�գ����������ݿ������ѧ���Ĵ��");
			return;
		}	
		
		SysStudentQuestionnaireRelModel sysStudentQuestionnaireRelModelParameter = new SysStudentQuestionnaireRelModel();
		sysStudentQuestionnaireRelModelParameter.setCode(UniqueCode.getUUID());
	    HttpSession session = request.getSession();
	    SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
	    if(sysUserModel == null) {
	    	System.out.println("ѧ��û�е�½��û�����д���");
	    	return;
	    }
	    sysStudentQuestionnaireRelModelParameter.setUserCode(sysUserModel.getCode());
	    sysStudentQuestionnaireRelModelParameter.setCreateQuestionCode(qestionnaireCode);
	    
	    sysStudentQuestionnaireRelModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	    sysStudentQuestionnaireRelModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	    sysStudentQuestionnaireRelModelParameter.setCreator(sysUserModel.getUserAccount());
	    sysStudentQuestionnaireRelModelParameter.setUpdater(sysUserModel.getUserAccount());		
	    sysStudentQuestionnaireRelModelParameter.setIsDelete(0);
	    sysStudentQuestionnaireRelModelParameter.setIsEffect(1);		
	    sysStudentQuestionnaireRelModelParameter.setOrderBy((new Random()).nextDouble());		
	    sysStudentQuestionnaireRelModelParameter.setDescr("��");     	
	    sysStudentQuestionnaireRelService.insert(sysStudentQuestionnaireRelModelParameter);
	    
	    //һ�µĴ���ʱ�����ݿ����������Ĵ�
	   
		if(FormatEmpty.isEmpty(questionCodeAndAnswer)) {
			System.out.println("�û�û�д��⣬���������ݿ������ѧ���Ĵ��");
			 response.getWriter().write("{\result\" : \"false\"}");
			return;
		}
		
		String[] questionCodeAndAnswerArray = (questionCodeAndAnswer.substring(0, questionCodeAndAnswer.length()-1)).split(",");
		SysStudentAnswerModel SysStudentAnswerModelParameter = new SysStudentAnswerModel();
		SysStudentAnswerModelParameter.setStuQuestionnaireRelCode(sysStudentQuestionnaireRelModelParameter.getCode());
		SysStudentAnswerModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		SysStudentAnswerModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		SysStudentAnswerModelParameter.setCreator(sysUserModel.getUserAccount());
		SysStudentAnswerModelParameter.setUpdater(sysUserModel.getUserAccount());		
		SysStudentAnswerModelParameter.setIsDelete(0);
		SysStudentAnswerModelParameter.setIsEffect(1);		
		SysStudentAnswerModelParameter.setOrderBy((new Random()).nextDouble());		
		SysStudentAnswerModelParameter.setDescr("��");  				
		for(String questionCodeAndAnswerForEach : questionCodeAndAnswerArray) {
			 SysStudentAnswerModelParameter.setCode(UniqueCode.getUUID());	    	
	    	 String questionCode =  questionCodeAndAnswerForEach.substring(0, questionCodeAndAnswerForEach.length()-1);
	    	 SysStudentAnswerModelParameter.setQuestionCode(questionCode);
	    	 String answerName = questionCodeAndAnswerForEach.substring(questionCodeAndAnswerForEach.length()-1);
	    	 SysStudentAnswerModelParameter.setAnswerName(answerName);
	    	 sysStudentAnswerService.insert(SysStudentAnswerModelParameter);
	    }
	    response.getWriter().write("{\"result\" : \"true\"}");	
	}
	
	@RequestMapping("selectQuestionnaireAnswer.do")
	public void selectQuestionnaireAnswer(String questionnaireCode , HttpServletRequest request , HttpServletResponse response) throws IOException {
		SysStudentQuestionnaireRelModel sysStudentQuestionnaireRelModel = new SysStudentQuestionnaireRelModel();
	    HttpSession session = request.getSession();
	    SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
	    if(sysUserModel==null) {
	    	System.out.println("�û�û�е�½���޷���ѯ�Ѿ���������Ŀ");
	    	return;
	    }
	    sysStudentQuestionnaireRelModel.setUserCode(sysUserModel.getCode());
	    sysStudentQuestionnaireRelModel.setCreateQuestionCode(questionnaireCode);
	    ArrayList<SysQuestionLibraryModel> sysQuestionLibraryModelList = (ArrayList<SysQuestionLibraryModel>) sysStudentAnswerService.selectQuestionLibraryAndStudentAnswer(sysStudentQuestionnaireRelModel);
	    if(FormatEmpty.isEmpty(sysQuestionLibraryModelList)) {
	    	System.out.println("������ʾ���û�в鵽�Ѿ���������Ŀ�������쳣");
	    	return;
	    }
	    ArrayList<Map<String , String>> listMapRequired = new ArrayList();
    	for(SysQuestionLibraryModel modelForEach : sysQuestionLibraryModelList) {
    		Map<String , String> mapModel = new HashMap();
    		mapModel.put("cb", "");
    		mapModel.put("id", modelForEach.getId().toString());
    		mapModel.put("code", modelForEach.getCode());
    		mapModel.put("questionTitle", modelForEach.getQuestionTitle());
    		mapModel.put("questionOption", modelForEach.getQuestionOption());
    		mapModel.put("answer", modelForEach.getSysStudentAnswerModel().getAnswerName());
    		listMapRequired.add(mapModel);
    	}
    	  JSONArray sysQuestionLibraryModelListJson = JSONArray.fromObject(listMapRequired);	    	  
    	  response.getWriter().write(sysQuestionLibraryModelListJson.toString()); 	      
	}	
}
