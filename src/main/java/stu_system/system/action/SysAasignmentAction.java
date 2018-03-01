package stu_system.system.action;

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
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysAasignmentModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysAasignmentService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;
@Controller("sysAasignmentAction")
@RequestMapping("/sysAasignmentAction")
public class SysAasignmentAction extends BaseAction {
	@Autowired
	private SysAasignmentService<SysAasignmentModel> sysAasignmentService;
	@RequestMapping("/saveAasignment.do")
	public void saveAasignment(SysAasignmentModel sysAasignmentModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysAasignmentModel sysAasignmentModelParameter = new SysAasignmentModel();
		sysAasignmentModelParameter.setCode(UniqueCode.getUUID());
		String time = sysAasignmentModel.getTime();
		if(FormatEmpty.isEmpty(time)) {
			System.out.println("发布题目的时间为空");
			return;
		}
		sysAasignmentModelParameter.setTime(time);
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("教师还没有登陆无法发布作业");
			return;
		}
		//设置录入题目的老师的code
		sysAasignmentModelParameter.setTeacherCode(sysUserModel.getCode());
		String homeworkName = sysAasignmentModel.getHomeworkName();
		if(FormatEmpty.isEmpty(homeworkName)) {
			System.out.println("家庭作业的名字不能为空");
			return;
		}
		sysAasignmentModelParameter.setHomeworkName(homeworkName);
		String gradeStandard = sysAasignmentModel.getGradeStandard();
		if(FormatEmpty.isEmpty(gradeStandard)) {
			System.out.println("题目的评分标准不能为空");
			return;
		}
		sysAasignmentModelParameter.setGradeStandard(gradeStandard);
		Integer type = sysAasignmentModel.getType();
		if(type==null) {
			System.out.println("发布的题目没有输入题目类型");
			return;
		}
		sysAasignmentModelParameter.setType(type);
		sysAasignmentModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysAasignmentModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysAasignmentModelParameter.setCreator(sysUserModel.getUserAccount());
		sysAasignmentModelParameter.setUpdater(sysUserModel.getUserAccount());
		
		sysAasignmentModelParameter.setIsDelete(0);
		sysAasignmentModelParameter.setIsEffect(1);		
		sysAasignmentModelParameter.setOrderBy((new Random()).nextDouble());
		
		sysAasignmentModelParameter.setDescr("无");
		sysAasignmentService.insert(sysAasignmentModelParameter);
		response.getWriter().write( "{\"success\":\"true\"}");		
	}
	@RequestMapping(value = "/getAasignmentInformation.do" ,produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getAasignmentInformation(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysAasignmentModel sysAasignmentModelParameter = new SysAasignmentModel();
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("老师没有登陆没法进行查询发布的作业");
			return"[]";
		}
		sysAasignmentModelParameter.setTeacherCode(sysUserModel.getCode());
		ArrayList<SysAasignmentModel> sysAasignmentModelList = (ArrayList<SysAasignmentModel>) sysAasignmentService.selectAasignmentAndDictionary(sysAasignmentModelParameter);
		if(FormatEmpty.isEmpty(sysAasignmentModelList)) {
			System.out.println("该教师还没有发布过作业");
			return"[]";			
		}
		ArrayList<Map<String , String>> sysAasignmentModelMapArrayList = new ArrayList();
		for(SysAasignmentModel sysAasignmentModelForEach : sysAasignmentModelList) {
			Map<String , String > sysAasignmentModelMap = new HashMap();
			sysAasignmentModelMap.put("id", sysAasignmentModelForEach.getId().toString());
			sysAasignmentModelMap.put("code", sysAasignmentModelForEach.getCode());
			sysAasignmentModelMap.put("time", sysAasignmentModelForEach.getTime());
			sysAasignmentModelMap.put("homeworkName", sysAasignmentModelForEach.getHomeworkName());
			sysAasignmentModelMap.put("gradeStandard", sysAasignmentModelForEach.getGradeStandard());
			sysAasignmentModelMap.put("type", sysAasignmentModelForEach.getTypeName());			
			sysAasignmentModelMapArrayList.add(sysAasignmentModelMap);
		}
		JSONArray sysAasignmentModelMapArrayListResult = JSONArray.fromObject(sysAasignmentModelMapArrayList);
		//转化成json字符串
		return sysAasignmentModelMapArrayListResult .toString();
	}

}
