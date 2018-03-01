package stu_system.system.action;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysCreateQuestionModel;
import stu_system.system.model.SysCreateQuestionQuestionLibraryRelationModel;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysQuestionnaireForStuModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysCreateQuestionQuestionLibraryRelationService;
import stu_system.system.service.SysCreateQuestionService;
import stu_system.system.service.SysUserService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;
@Controller("sysCreateQuestionAction")
@RequestMapping("/sysCreateQuestionAction")
public class SysCreateQuestionAction extends BaseAction {
    @Autowired
	private SysCreateQuestionService<SysCreateQuestionModel> sysCreateQuestionService;
    @Autowired
    private SysCreateQuestionQuestionLibraryRelationService<SysCreateQuestionQuestionLibraryRelationModel> sysCreateQuestionQuestionLibraryRelationService;
    
    @RequestMapping("/saveCreateQuestion.do")
    public void saveCreateQuestion(SysCreateQuestionModel sysCreateQuestionModel  , String codes  , HttpServletRequest request , HttpServletResponse response) throws Exception {
    	if(FormatEmpty.isEmpty(codes)) {
			System.out.println("没有从题库中选择题目");
			return ;
		}
       SysCreateQuestionModel sysCreateQuestionModelParameter = new SysCreateQuestionModel();
       String questionName = sysCreateQuestionModel.getQuestionName();
       if(FormatEmpty.isEmpty(questionName)) {
    	   System.out.println("没有问卷标题，无法创建问卷");
    	   return;
       }
       sysCreateQuestionModelParameter.setCode(UniqueCode.getUUID());
       sysCreateQuestionModelParameter.setQuestionName(questionName);
       HttpSession session = request.getSession();
       SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
       if(sysUserModel == null) {
    	   System.out.println("教务老师没有登陆，无法创建问卷");
    	   return;
       }
       sysCreateQuestionModelParameter.setUserCode(sysUserModel.getCode());
      
       sysCreateQuestionModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
       sysCreateQuestionModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
       sysCreateQuestionModelParameter.setCreator(sysUserModel.getUserAccount());
       sysCreateQuestionModelParameter.setUpdater(sysUserModel.getUserAccount());		
       sysCreateQuestionModelParameter.setIsDelete(0);
       sysCreateQuestionModelParameter.setIsEffect(1);		
       sysCreateQuestionModelParameter.setOrderBy((new Random()).nextDouble());		
       sysCreateQuestionModelParameter.setDescr("无");     	   
       sysCreateQuestionService.insert(sysCreateQuestionModelParameter);      
       //下面是往关联表中添加关联的题目     
		String questionCodesArray[] = codes.split(","); //questionCodesArray这个数组中存放了从题库中选中的题目的code
		SysCreateQuestionQuestionLibraryRelationModel sysCreateQuestionQuestionLibraryRelationModelParameter = new SysCreateQuestionQuestionLibraryRelationModel();
		sysCreateQuestionQuestionLibraryRelationModelParameter.setCreateQuestionCode(sysCreateQuestionModelParameter.getCode());
		sysCreateQuestionQuestionLibraryRelationModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysCreateQuestionQuestionLibraryRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));		
		sysCreateQuestionQuestionLibraryRelationModelParameter.setCreator(sysUserModel.getUserAccount());
		sysCreateQuestionQuestionLibraryRelationModelParameter.setUpdater(sysUserModel.getUserAccount());
		sysCreateQuestionQuestionLibraryRelationModelParameter.setIsDelete(0);
		sysCreateQuestionQuestionLibraryRelationModelParameter.setIsEffect(1);
		sysCreateQuestionQuestionLibraryRelationModelParameter.setOrderBy((new Random()).nextDouble());
		sysCreateQuestionQuestionLibraryRelationModelParameter.setDescr("无");
		
		for(String questionCode : questionCodesArray) {			
			sysCreateQuestionQuestionLibraryRelationModelParameter.setCode(UniqueCode.getUUID());
			sysCreateQuestionQuestionLibraryRelationModelParameter.setQuestionLibraryCode(questionCode);			
			sysCreateQuestionQuestionLibraryRelationService.insert(sysCreateQuestionQuestionLibraryRelationModelParameter);		
		}      
      /* System.out.println("当前插入记录id是"+sysCreateQuestionModelParameter.getId());*/
       response.getWriter().write( "{\"success\":\"true\"}");	      
    }  
    @RequestMapping("/selectCreateQuestion.do")
   public void selectCreateQuestion(String questionnaireName , String page , String rows ,  HttpServletRequest request , HttpServletResponse response) throws Exception { 
	   SysCreateQuestionModel sysCreateQuestionModelParameter = new SysCreateQuestionModel();		
       
	   HttpSession session = request.getSession();
	   SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
	   if(sysUserModel==null) {
		   System.out.println("用户还没有登陆，没法进行查询");
		   return;
	   }
	   sysCreateQuestionModelParameter.setUserCode(sysUserModel.getCode());	   
	   sysCreateQuestionModelParameter.setQuestionName(questionnaireName);
	   sysCreateQuestionModelParameter.setIsDelete(0);
	   sysCreateQuestionModelParameter.setIsEffect(1);
	   sysCreateQuestionModelParameter.setSort("id");
	   sysCreateQuestionModelParameter.setOrder("ASC");
	   sysCreateQuestionModelParameter.setPage(Integer.valueOf(page));
	   sysCreateQuestionModelParameter.setRows(Integer.valueOf(rows));
	   ArrayList<SysCreateQuestionModel> sysCreateQuestionModelList =  (ArrayList<SysCreateQuestionModel>) sysCreateQuestionService.selectModel(sysCreateQuestionModelParameter);
	   if(FormatEmpty.isEmpty(sysCreateQuestionModelList)) {
	    	response.getWriter().write("{\"total\":"+0+",\"rows\":\"[] \"}");
	    }else {
	    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
	    	for(SysCreateQuestionModel modelForEach : sysCreateQuestionModelList) {
	    		Map<String , String> mapModel = new HashMap();
	    		mapModel.put("cb", "");
	    		mapModel.put("id", modelForEach.getId().toString());
	    		mapModel.put("code", modelForEach.getCode());
	    		mapModel.put("questionnaireName", modelForEach.getQuestionName());
	    		listMapRequired.add(mapModel);
	    	}
	    	  JSONArray sysCreateQuestionModellistJson = JSONArray.fromObject(listMapRequired);	    	  
	    	  response.getWriter().write("{\"total\":"+sysCreateQuestionModelParameter.getPager().getRowCount()+",\"rows\":" + sysCreateQuestionModellistJson.toString() +"}");
	    }	   
   }
    
    @RequestMapping("/selectCreateQuestionForStu.do")
    public void selectCreateQuestionForStu(HttpServletRequest request , HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession();
    	SysUserModel  sysUserModel = (SysUserModel) session.getAttribute("currentUser");
    	if(sysUserModel == null) {
    		System.out.println("学生没有登陆，请登陆后再加载问卷");
    		return;
    	}
    	
    	SysUserModel  sysUserModelParameter = new SysUserModel();
    	sysUserModelParameter.setCode(sysUserModel.getCode());
    	ArrayList<SysCreateQuestionModel> sysCreateQuestionModelList  = (ArrayList<SysCreateQuestionModel>) sysCreateQuestionService.selectCreateQuestionAndStudentRelation(sysUserModelParameter);
    	if(FormatEmpty.isEmpty(sysCreateQuestionModelList)) {
    		System.out.println("没有问卷");
    		response.getWriter().write("[]");
    	}
    	
    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
    	for(SysCreateQuestionModel modelForEach : sysCreateQuestionModelList) {
    		Map<String , String> mapModel = new HashMap();
    		mapModel.put("cb", "");
    		mapModel.put("id", modelForEach.getId().toString());
    		mapModel.put("code", modelForEach.getCode());
    		mapModel.put("questionnaireName", modelForEach.getQuestionName());
    		String descr = modelForEach.getDescr();
    		if(FormatEmpty.isEmpty(descr)) {
    			mapModel.put("descr", "");
    		}else {
    			mapModel.put("descr", descr);
    		}
    		
    		SysQuestionnaireForStuModel sysQuestionnaireForStuModel = modelForEach.getSysQuestionnaireForStuModel(); 		   		
    		if(sysQuestionnaireForStuModel.getUserCode() ==null) {
    			mapModel.put("operation", "0");		
    		}else {
    			mapModel.put("operation", "1");
    		}
    		listMapRequired.add(mapModel);
    	}
    	  JSONArray sysUserModellistJson = JSONArray.fromObject(listMapRequired);	    	  
    	   response.getWriter().write(sysUserModellistJson.toString());   	
    }
    @RequestMapping("/dispacherForQuestionnaire.do")
    public ModelAndView dispacherForQuestionnaire(String code , String forword , String questionnaireName , HttpServletRequest request , HttpServletResponse response) {
    	if(FormatEmpty.isEmpty(code)) {
    		System.out.println("没有获得问卷的code");
    		return null;
    	}
    	if(FormatEmpty.isEmpty(questionnaireName)) {
    		System.out.println("没有获得问卷的标题");
    		return null;
    	}
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("questionnaireCode", code);
    	mav.addObject("questionnaireName", questionnaireName);
    	mav.setViewName(forword);
    	return mav;
    }
    @RequestMapping("/selectQuestionForAnswerOfStu.do")
    public void selectQuestionForAnswerOfStu(SysCreateQuestionModel sysCreateQuestionModel , HttpServletRequest request , HttpServletResponse response) throws IOException {
    	SysCreateQuestionModel sysCreateQuestionModelParameter = new SysCreateQuestionModel();
        String code = sysCreateQuestionModel.getCode();
        if(FormatEmpty.isEmpty(code)) {
        	System.out.println("问卷的code没有传过来，没法加载该问卷的题目");
        }
        sysCreateQuestionModelParameter.setCode(code);
        ArrayList<SysQuestionLibraryModel> sysQuestionLibraryModelList = (ArrayList<SysQuestionLibraryModel>) sysCreateQuestionService.selectQuestionForAnswerOfStu(sysCreateQuestionModelParameter);
        if(FormatEmpty.isEmpty(sysQuestionLibraryModelList)) {
    		System.out.println("问卷中没有题目");
    		response.getWriter().write("[]");
    	}
    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
    	for(SysQuestionLibraryModel modelForEach : sysQuestionLibraryModelList) {
    		Map<String , String> mapModel = new HashMap();
    		mapModel.put("cb", "");
    		mapModel.put("id", modelForEach.getId().toString());
    		mapModel.put("code", modelForEach.getCode());
    		mapModel.put("questionTitle", modelForEach.getQuestionTitle());
    		mapModel.put("questionOption", modelForEach.getQuestionOption());
    		listMapRequired.add(mapModel);
    	}
    	  JSONArray sysQuestionLibraryModelListJson = JSONArray.fromObject(listMapRequired);	    	  
    	  response.getWriter().write(sysQuestionLibraryModelListJson.toString());        
    }
    
    
    
    
}
