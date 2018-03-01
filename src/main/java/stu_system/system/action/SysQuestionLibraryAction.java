package stu_system.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysQuestionLibraryService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;
@Controller("sysQuestionLibraryAction")
@RequestMapping("/sysQuestionLibraryAction")
public class SysQuestionLibraryAction extends BaseAction {

	@Autowired
	private SysQuestionLibraryService<SysQuestionLibraryModel> sysQuestionLibraryService;
	
	@RequestMapping("/saveQuestion.do")
	public void saveQuestion(SysQuestionLibraryModel sysQuestionLibraryModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysQuestionLibraryModel sysQuestionLibraryModelParameter = new SysQuestionLibraryModel(); 
		sysQuestionLibraryModelParameter.setCode(UniqueCode.getUUID());
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel == null) {
			System.out.println("教务老师没有登陆，");
			return;
		}
		sysQuestionLibraryModelParameter.setUserCode(sysUserModel.getCode());
		String questionTitle = sysQuestionLibraryModel.getQuestionTitle();
		if(FormatEmpty.isEmpty(questionTitle)) {
			System.out.println("没有录入问题题目");
			return;
		}
		sysQuestionLibraryModelParameter.setQuestionTitle(questionTitle);
		String questionOption = sysQuestionLibraryModel.getQuestionOption();
		if(FormatEmpty.isEmpty(questionOption)) {
			System.out.println("没有录入题目的选项");
			return;
		}
		sysQuestionLibraryModelParameter.setQuestionOption(questionOption);
		String questionAnswer = sysQuestionLibraryModel.getQuestionAnswer();
		if(FormatEmpty.isEmpty(questionAnswer)) {
			System.out.println("没有录入问题的答案");
			return;			
		}
		sysQuestionLibraryModelParameter.setQuestionAnswer(questionAnswer);
		sysQuestionLibraryModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysQuestionLibraryModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysQuestionLibraryModelParameter.setCreator(sysUserModel.getUserAccount());
		sysQuestionLibraryModelParameter.setUpdater(sysUserModel.getUserAccount());
		sysQuestionLibraryModelParameter.setIsDelete(0);
		sysQuestionLibraryModelParameter.setIsEffect(1);
		sysQuestionLibraryModelParameter.setOrderBy((new Random()).nextDouble());
		String descr = sysQuestionLibraryModel.getDescr();
		if(FormatEmpty.isEmpty(descr)) {
			sysQuestionLibraryModelParameter.setDescr("无");
		}else {
			sysQuestionLibraryModelParameter.setDescr(descr);
		}
		sysQuestionLibraryService.insert(sysQuestionLibraryModelParameter);
		response.getWriter().write( "{\"success\":\"true\"}");				
	}
	@RequestMapping("/selectAllQuestions.do")
	public void selectAllQuestions(String questionTitle , String page , String rows ,  HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysQuestionLibraryModel sysQuestionLibraryModelParameter = new SysQuestionLibraryModel();
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel == null) {
			System.out.println("教务老师没有登陆，");
			return;
		}
		sysQuestionLibraryModelParameter.setQuestionTitle(questionTitle);
		sysQuestionLibraryModelParameter.setIsDelete(0);
		sysQuestionLibraryModelParameter.setIsEffect(1);		
		sysQuestionLibraryModelParameter.setUserCode(sysUserModel.getCode());
		sysQuestionLibraryModelParameter.setSort("id");
		sysQuestionLibraryModelParameter.setOrder("ASC");
		sysQuestionLibraryModelParameter.setPage(Integer.valueOf(page));
		sysQuestionLibraryModelParameter.setRows(Integer.valueOf(rows));
		List<SysQuestionLibraryModel> sysQuestionLibraryModelList = sysQuestionLibraryService.selectModel(sysQuestionLibraryModelParameter);
		if(FormatEmpty.isEmpty(sysQuestionLibraryModelList)) {
			System.out.println("题库中还没有题目");
			 response.getWriter().write("{\"total\":"+0+",\"rows\":\"[]\"}");
		}
		ArrayList<Map<String , String>> listMapRequired = new ArrayList();
    	for(SysQuestionLibraryModel modelForEach : sysQuestionLibraryModelList) {
    		Map<String , String> mapModel = new HashMap();
    		mapModel.put("cb", "");
    		mapModel.put("id", modelForEach.getId().toString());
    		mapModel.put("code", modelForEach.getCode());   		
    		mapModel.put("questionTitle", modelForEach.getQuestionTitle());
    		mapModel.put("questionOption", modelForEach.getQuestionOption());
    		mapModel.put("questionAnswer", modelForEach.getQuestionAnswer()); 
    		mapModel.put("descr", modelForEach.getDescr());
    		listMapRequired.add(mapModel);
    	}
    	  JSONArray listMapRequiredJson = JSONArray.fromObject(listMapRequired);	    	  
    	  response.getWriter().write("{\"total\":"+sysQuestionLibraryModelParameter.getPager().getRowCount()+",\"rows\":" + listMapRequiredJson.toString() +"}");			
	}
	@RequestMapping("/updateQuestions.do")
	 public void updateQuestions(SysQuestionLibraryModel sysQuestionLibraryModel ,  HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysQuestionLibraryModel sysQuestionLibraryModelParameter = new SysQuestionLibraryModel();
	    Integer id = sysQuestionLibraryModel.getId();
	    if(id == null) {
	    	System.out.println("没有选中要修改的记录");
	    	return;
	    }
	    sysQuestionLibraryModelParameter.setId(id);
		String questionTitle = sysQuestionLibraryModel.getQuestionTitle();
	    if(!FormatEmpty.isEmpty(questionTitle)) {
	    	sysQuestionLibraryModelParameter.setQuestionTitle(questionTitle);
	    }
	    String questionOption = sysQuestionLibraryModel.getQuestionOption();
	    if(!FormatEmpty.isEmpty(questionOption)) {
	    	sysQuestionLibraryModelParameter.setQuestionOption(questionOption);
	    }
	    String questionAnswer = sysQuestionLibraryModel.getQuestionAnswer();
	    if(!FormatEmpty.isEmpty(questionAnswer)) {
	    	sysQuestionLibraryModelParameter.setQuestionAnswer(questionAnswer);
	    }
	    String descr = sysQuestionLibraryModel.getDescr();
	    if(!FormatEmpty.isEmpty(descr)) {
	    	sysQuestionLibraryModelParameter.setDescr(descr);
	    }
	    sysQuestionLibraryModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		HttpSession session = request.getSession();
		//从session域中取出当前的登陆用户账号
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		if(FormatEmpty.isEmpty(currentUserAccount)) {
			System.out.println("当前用户还没有登陆");
			return;
		}
		sysQuestionLibraryModelParameter.setUpdater(currentUserAccount);
		sysQuestionLibraryService.updateActive(sysQuestionLibraryModelParameter);
		response.getWriter().write("{\"success\":\"true\"}");	
	}
	@RequestMapping("/deleteByUpdate.do")
	@ResponseBody
	public String deleteByUpdate(String ids , HttpServletRequest request , HttpServletResponse response) throws Exception {
		if(FormatEmpty.isEmpty(ids)) {
			System.out.println("没有选择要删除的用户");
			return "{\"success\":\"false\"}";
		}
		String questionsIdArray[] = ids.split(",");
		HttpSession session = request.getSession();
		//从session域中取出当前的登陆用户账号
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		if(FormatEmpty.isEmpty(currentUserAccount)) {
			System.out.println("教务没有登陆没法修改数据");
			return "{\"success\":\"false\"}";
		}		
		for(String questionId : questionsIdArray) {
			SysQuestionLibraryModel sysQuestionLibraryModelParameter = new SysQuestionLibraryModel();		    
		    sysQuestionLibraryModelParameter.setId(Integer.valueOf(questionId));
		    sysQuestionLibraryModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		    sysQuestionLibraryModelParameter.setUpdater(currentUserAccount);
		    sysQuestionLibraryModelParameter.setIsDelete(1);
		    sysQuestionLibraryModelParameter.setIsEffect(0);
		    sysQuestionLibraryService.updateActive(sysQuestionLibraryModelParameter);
		}
		return "{\"success\":\"true\"}";
	}
	
}
