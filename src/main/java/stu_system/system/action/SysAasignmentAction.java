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
			System.out.println("������Ŀ��ʱ��Ϊ��");
			return;
		}
		sysAasignmentModelParameter.setTime(time);
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("��ʦ��û�е�½�޷�������ҵ");
			return;
		}
		//����¼����Ŀ����ʦ��code
		sysAasignmentModelParameter.setTeacherCode(sysUserModel.getCode());
		String homeworkName = sysAasignmentModel.getHomeworkName();
		if(FormatEmpty.isEmpty(homeworkName)) {
			System.out.println("��ͥ��ҵ�����ֲ���Ϊ��");
			return;
		}
		sysAasignmentModelParameter.setHomeworkName(homeworkName);
		String gradeStandard = sysAasignmentModel.getGradeStandard();
		if(FormatEmpty.isEmpty(gradeStandard)) {
			System.out.println("��Ŀ�����ֱ�׼����Ϊ��");
			return;
		}
		sysAasignmentModelParameter.setGradeStandard(gradeStandard);
		Integer type = sysAasignmentModel.getType();
		if(type==null) {
			System.out.println("��������Ŀû��������Ŀ����");
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
		
		sysAasignmentModelParameter.setDescr("��");
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
			System.out.println("��ʦû�е�½û�����в�ѯ��������ҵ");
			return"[]";
		}
		sysAasignmentModelParameter.setTeacherCode(sysUserModel.getCode());
		ArrayList<SysAasignmentModel> sysAasignmentModelList = (ArrayList<SysAasignmentModel>) sysAasignmentService.selectAasignmentAndDictionary(sysAasignmentModelParameter);
		if(FormatEmpty.isEmpty(sysAasignmentModelList)) {
			System.out.println("�ý�ʦ��û�з�������ҵ");
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
		//ת����json�ַ���
		return sysAasignmentModelMapArrayListResult .toString();
	}

}
