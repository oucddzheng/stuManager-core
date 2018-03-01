package stu_system.system.action;

import java.io.IOException;
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

import net.sf.json.JSONArray;
import stu_system.system.model.SysAasignmentModel;
import stu_system.system.model.SysAssignmentConditionModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysAasignmentConditionService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;

@Controller("sysAssignmentConditionAction")
@RequestMapping("/sysAssignmentConditionAction")
public class SysAssignmentConditionAction {
	@Autowired
    private SysAasignmentConditionService<SysAssignmentConditionModel> sysAasignmentConditionService;
    
    @RequestMapping("/getAssignmentCondition.do")
	public void getAssignmentCondition(HttpServletRequest request , HttpServletResponse response) throws IOException {
    	HttpSession session = request.getSession();
    	SysUserModel sysUserModel =  (SysUserModel) session.getAttribute("currentUser"); 
    	if(sysUserModel == null) {
    		System.out.println("学生没有登陆，没法进行答题");
    		return;
    	}
    	
    	
        List<SysAasignmentModel> sysAasignmentModelList = sysAasignmentConditionService.selectAssignmentAndAssignment_conditionAndUser(sysUserModel);
        if(FormatEmpty.isEmpty(sysAasignmentModelList)) {
        	System.out.println("老师还没有发布任何题目");
        	response.getWriter().write("[]");
        	return;
        } 
        ArrayList<Map<String , String>> sysAssignmentModelMapArrayList = new ArrayList();
		for(SysAasignmentModel sysAasignmentModelForEach : sysAasignmentModelList) {
			Map<String , String > sysAasignmentModelMap = new HashMap();
			sysAasignmentModelMap.put("id", sysAasignmentModelForEach.getId().toString());
			sysAasignmentModelMap.put("code", sysAasignmentModelForEach.getCode());
			sysAasignmentModelMap.put("time", sysAasignmentModelForEach.getTime());
			sysAasignmentModelMap.put("homeworkName", sysAasignmentModelForEach.getHomeworkName());
			sysAasignmentModelMap.put("gradeStandard", sysAasignmentModelForEach.getGradeStandard());
			sysAasignmentModelMap.put("type", sysAasignmentModelForEach.getTypeName());
			
			Double oneselfScore = sysAasignmentModelForEach.getSysAssignmentConditionModel().getOneselfScore();
			if(oneselfScore == null) {
				sysAasignmentModelMap.put("oneself_score", "");
			}else {
				sysAasignmentModelMap.put("oneself_score", oneselfScore.toString());
			}
			String oneselfGradeName = sysAasignmentModelForEach.getSysAssignmentConditionModel().getOneselfGradeName();
			if(oneselfGradeName == null) {
				sysAasignmentModelMap.put("oneself_grade", "");
			}else {
				sysAasignmentModelMap.put("oneself_grade", oneselfGradeName);
			}
			
			sysAasignmentModelMap.put("descr", sysAasignmentModelForEach.getDescr());			
			/*sysAasignmentModelMap.put("selfEvaluation", "<a href=\"javascript:selfEvaluation()\" class=\"easyui-linkbutton\" iconCls=\"icon-add\" plain=\"true\" style=\"margin-left:50px\">自我评分</a>");*/
			sysAssignmentModelMapArrayList.add(sysAasignmentModelMap);
			
		}
		JSONArray sysAasignmentModelMapArrayListResult = JSONArray.fromObject(sysAssignmentModelMapArrayList);
		//转化成json字符串
		response.getWriter().write(sysAasignmentModelMapArrayListResult.toString());       
    }
    @RequestMapping("/saveAssignmentCondition.do")
    public void saveAssignmentCondition(SysAssignmentConditionModel sysAssignmentConditionModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
    	SysAssignmentConditionModel sysAssignmentConditionModelParameter = new SysAssignmentConditionModel();
    	sysAssignmentConditionModelParameter.setCode(UniqueCode.getUUID());   	
    	HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("学生没有登陆");
			return;
		}
		sysAssignmentConditionModelParameter.setStudentCode(sysUserModel.getCode());
    	String assignmentCode =  sysAssignmentConditionModel.getAssignmentCode();
    	if(FormatEmpty.isEmpty(assignmentCode)) {
    		System.out.println("作业的code为空");
    		return;
    	}
    	sysAssignmentConditionModelParameter.setAssignmentCode(assignmentCode);
    	System.out.println(assignmentCode);
    	Double oneselfScore = sysAssignmentConditionModel.getOneselfScore();
    	if(oneselfScore == null ) {
    		System.out.println("分数没有输入");
    		return;
    	}
    	sysAssignmentConditionModelParameter.setOneselfScore(oneselfScore);
    	System.out.println(oneselfScore);
    	Integer oneselfGrade = sysAssignmentConditionModel.getOneselfGrade();
    	if(oneselfGrade == null) {
    		System.out.println("没有输入等级");
    		return;
    	}
    	sysAssignmentConditionModelParameter.setOneselfGrade(oneselfGrade);
    	System.out.println(oneselfGrade);
    	sysAssignmentConditionModelParameter.setTeacherScore(100.0);
    	sysAssignmentConditionModelParameter.setTeacherGrade(19);
    	
    	sysAssignmentConditionModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
    	sysAssignmentConditionModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
    	sysAssignmentConditionModelParameter.setCreator(sysUserModel.getUserAccount());
    	sysAssignmentConditionModelParameter.setUpdater(sysUserModel.getUserAccount());
		
    	sysAssignmentConditionModelParameter.setIsDelete(0);
    	sysAssignmentConditionModelParameter.setIsEffect(1);		
    	sysAssignmentConditionModelParameter.setOrderBy((new Random()).nextDouble());		
    	sysAssignmentConditionModelParameter.setDescr("无");
    	sysAasignmentConditionService.insert(sysAssignmentConditionModelParameter);
    	response.getWriter().write( "{\"success\":\"true\"}");	
    }
    @RequestMapping("/getAasignmentConditionCheck.do")
    public void getAasignmentConditionCheck(SysAasignmentModel sysAasignmentModel , HttpServletRequest request , HttpServletResponse response) throws IOException {
    	SysAasignmentModel sysAasignmentModelParameter = new SysAasignmentModel();
    	String code = sysAasignmentModel.getCode();
    	if(FormatEmpty.isEmpty(code)) {
    		System.out.println("没有获得题目的code");
    		return;
    	}
    	sysAasignmentModelParameter.setCode(code);
    	HttpSession session = request.getSession();
    	SysUserModel sysUserModel =  (SysUserModel) session.getAttribute("currentUser"); 
    	if(sysUserModel == null) {
    		System.out.println("学生没有登陆，没法进行答题");
    		return;
    	}
    	sysAasignmentModelParameter.setTeacherCode(sysUserModel.getCode());
    	
    	 List<SysAssignmentConditionModel> sysAssignmentConditionModelList = sysAasignmentConditionService.selectUserAndAssignment_condition(sysAasignmentModelParameter);
         if(FormatEmpty.isEmpty(sysAssignmentConditionModelList)) {
         	System.out.println("老师还没有发布任何题目");
         	response.getWriter().write("[]");
         	return;
         } 
    	
    	
        ArrayList<Map<String , String>> sysAssignmentConditionModelMapArrayList = new ArrayList();
 		for(SysAssignmentConditionModel sysAssignmentConditionModeForEach : sysAssignmentConditionModelList) {
 			Map<String , String > sysAssignmentConditionModeMap = new HashMap();
 			sysAssignmentConditionModeMap.put("trueName", sysAssignmentConditionModeForEach.getSysUserModel().getUserTrueName());
 			
 			
 			Double oneselfScore = sysAssignmentConditionModeForEach.getOneselfScore();
 			if(oneselfScore == null) {
 				sysAssignmentConditionModeMap.put("oneself_score", "");
 			}else {
 				sysAssignmentConditionModeMap.put("oneself_score", oneselfScore.toString());
 			}
 			String oneselfGradeName = sysAssignmentConditionModeForEach.getOneselfGradeName();
 			if(oneselfGradeName == null) {
 				sysAssignmentConditionModeMap.put("oneself_grade", "");
 			}else {
 				sysAssignmentConditionModeMap.put("oneself_grade", oneselfGradeName);
 			}
 			
 			
 			sysAssignmentConditionModelMapArrayList.add(sysAssignmentConditionModeMap);
 			
 		}
 		JSONArray sysAasignmentModelMapArrayListResult = JSONArray.fromObject(sysAssignmentConditionModelMapArrayList);
 		//转化成json字符串
 		response.getWriter().write(sysAasignmentModelMapArrayListResult.toString()); 
    	
    }
}
