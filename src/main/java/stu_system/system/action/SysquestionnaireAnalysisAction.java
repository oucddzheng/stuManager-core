package stu_system.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysCreateQuestionModel;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysStudentQuestionnaireRelModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysStudentAnswerService;
import stu_system.system.service.SysStudentQuestionnaireRelService;
import stu_system.system.service.SysUserService;
import tools.FormatEmpty;

@Controller("sysquestionnaireAnalysisAction")
@RequestMapping("/sysquestionnaireAnalysisAction")
public class SysquestionnaireAnalysisAction extends BaseAction {
	
	@Autowired
	private SysStudentQuestionnaireRelService sysStudentQuestionnaireRelService;
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysStudentAnswerService sysStudentAnswerService;
	
	@RequestMapping("/selectCountOfQuestionnaireAnswered.do")
	public void selectCountOfQuestionnaireAnswered(String questionnaireCode , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysStudentQuestionnaireRelModel sysStudentQuestionnaireRelModelParameter = new SysStudentQuestionnaireRelModel();
		if(FormatEmpty.isEmpty(questionnaireCode )){
			System.out.println("问卷的code没有传过来，无法查询该问卷的回答情况");
			response.getWriter().write("{}");
			return;
		}
		sysStudentQuestionnaireRelModelParameter.setCreateQuestionCode(questionnaireCode);
		sysStudentQuestionnaireRelModelParameter.setIsDelete(0);
		sysStudentQuestionnaireRelModelParameter.setIsEffect(1);
		Integer numberQuestionnaireAnswered = sysStudentQuestionnaireRelService.selectCount(sysStudentQuestionnaireRelModelParameter);
		SysUserModel sysUserModelParameter = new SysUserModel();
		sysUserModelParameter.setRoleCode("1");	
		Integer numberTotalStu = sysUserService.selectCount(sysUserModelParameter);
		
		     
		     ArrayList<Map<String , String>> seriesDataList = new ArrayList();   
		        Map<String , String > seriesDataMapAnswered = new HashMap();
		        seriesDataMapAnswered.put("name", "已回答");
		        seriesDataMapAnswered.put("value", String.valueOf(numberQuestionnaireAnswered));
		        seriesDataList.add(seriesDataMapAnswered) ;
		        Map<String , String > seriesDataMapNoAnswered = new HashMap();
		        seriesDataMapNoAnswered.put("name", "未回答");
		        seriesDataMapNoAnswered.put("value", String.valueOf(numberTotalStu - numberQuestionnaireAnswered));
		        seriesDataList.add(seriesDataMapNoAnswered);
		     JSONArray  seriesDataListJSON = JSONArray.fromObject(seriesDataList);  
		    	System.out.println(seriesDataListJSON.toString());	 
		   response.getWriter().write(seriesDataListJSON.toString());		
	}
	@RequestMapping("/selectQuestionAnswerForAnalysis.do")
    public void selectQuestionAnswerForAnalysis(String questionnaireCode , HttpServletRequest request , HttpServletResponse response) throws IOException {
    	SysCreateQuestionModel sysCreateQuestionModelParameter = new SysCreateQuestionModel();
    	if(FormatEmpty.isEmpty(questionnaireCode)) {
    		System.out.println("问卷的code没有传过来，没法查询同学们的问卷答案");
    		response.getWriter().write("{}");
    		return;
    	}
    	sysCreateQuestionModelParameter.setCode(questionnaireCode);
        ArrayList<SysQuestionLibraryModel> sysQuestionLibraryModelList = (ArrayList<SysQuestionLibraryModel>) sysStudentAnswerService.selectQuestionAnswerForAnalysis(sysCreateQuestionModelParameter);
    	if(FormatEmpty.isEmpty(sysQuestionLibraryModelList)) {
    		System.out.println("没有从题库中查到学生们的答题情况，出现异常");
    		response.getWriter().write("{}");
    		return;
    	}
    	ArrayList<Integer> questionIdList = new ArrayList();
    	   for(SysQuestionLibraryModel sysQuestionLibraryModelForEach : sysQuestionLibraryModelList) {
    		   questionIdList.add(sysQuestionLibraryModelForEach.getId());
    	   }
    	   //利用set集合和list集合将list集合中的重复值给去掉
    	   Set set = new  HashSet(); 
    	   ArrayList<Integer> questionIdListNoRepeat = new ArrayList();  
    	   for (Integer id : questionIdList) {
    		    if(set.add(id)) {
    		    	questionIdListNoRepeat.add(id);
    		    }
    	   }
    	   Collections.sort(questionIdListNoRepeat);
    	   ArrayList<Map<String , Integer>> questionAndAnswerMapList = new ArrayList();
    	    for(Integer id : questionIdListNoRepeat) {
    	    	Map questionAndAnswerMap  = new HashMap();
    	    	questionAndAnswerMap.put("题号", id);
    	    	Integer Acount = 0;
    	    	Integer Bcount = 0;
    	    	Integer Ccount = 0;
    	    	for(SysQuestionLibraryModel sysQuestionLibraryModelForEach : sysQuestionLibraryModelList) {
    	    		if(sysQuestionLibraryModelForEach.getId()==id ) {
    	    			if(sysQuestionLibraryModelForEach.getAnswerName().equals("A")) {
    	 					Acount = sysQuestionLibraryModelForEach.getNumber();   	 	 	 			
    	 				}
    	 				if(sysQuestionLibraryModelForEach.getAnswerName().equals("B")) {
    	 					Bcount = sysQuestionLibraryModelForEach.getNumber();    	 	 	 			
    	 				}
    	 				if(sysQuestionLibraryModelForEach.getAnswerName().equals("C")) {
    	 					Ccount = sysQuestionLibraryModelForEach.getNumber();   	 	 	 			
    	 				}	 			
    	    		}
    	    		
    	    	}
    	    	questionAndAnswerMap.put("A", Acount);
    	    	questionAndAnswerMap.put("B", Bcount);
    	    	questionAndAnswerMap.put("C", Ccount);	
    	    	questionAndAnswerMapList.add(questionAndAnswerMap);    	    
    	    }
    	    ArrayList<Integer> questionNumberList = new ArrayList();	  
  		    ArrayList<Integer> questionOptionAList = new ArrayList();	  
  		    ArrayList<Integer> questionOptionBList = new ArrayList();	  
  		    ArrayList<Integer> questionOptionCList = new ArrayList();
  		  
  		  for (Map<String , Integer> mapForEach : questionAndAnswerMapList) {
  			  questionNumberList.add(mapForEach.get("题号"));
  			  questionOptionAList.add(mapForEach.get("A"));
  			  questionOptionBList.add(mapForEach.get("B"));
  			  questionOptionCList.add(mapForEach.get("C"));			  
  		  }
  	  Map<String , ArrayList> questtionNumberAndOptionEchart = new HashMap();
  	  questtionNumberAndOptionEchart.put("questionNumber", questionNumberList);
  	  questtionNumberAndOptionEchart.put("AOption", questionOptionAList);
  	  questtionNumberAndOptionEchart.put("BOption", questionOptionBList);
  	  questtionNumberAndOptionEchart.put("COption", questionOptionCList);
  	  JSONObject answerAnalysisEchartsDataJSON = JSONObject.fromObject(questtionNumberAndOptionEchart);
      response.getWriter().write(answerAnalysisEchartsDataJSON.toString());   
      System.out.println(answerAnalysisEchartsDataJSON.toString());
    }	
}
