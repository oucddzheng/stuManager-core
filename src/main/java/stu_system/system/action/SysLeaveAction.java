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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysLeaveModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysLeaveService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;

@Controller("sysLeaveAction")
@RequestMapping("/sysLeaveAction")
public class SysLeaveAction extends BaseAction {
	@Autowired
	private SysLeaveService<SysLeaveModel> sysLeaveService;
	@RequestMapping("/addLeave.do")
	public void addLeave(SysLeaveModel sysLeaveModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		//����code
		sysLeaveModelParameter.setCode(UniqueCode.getUUID());
		HttpSession session = request.getSession();
        //��session��ȡ����ǰ���ڵ�½���û���code
		SysUserModel  sysUserModel = (SysUserModel)session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("�û�û�е�½��û���������");
			return;
		}	
		sysLeaveModelParameter.setUserCode(	sysUserModel.getCode());
		String why = sysLeaveModel.getWhy();
		if(FormatEmpty.isEmpty(why)) {
			System.out.println("û����д���ԭ��");
			return;
		}
		sysLeaveModelParameter.setWhy(why);
		String beginTime = sysLeaveModel.getBeginTime();
		if(FormatEmpty.isEmpty(beginTime)) {
			System.out.println("û����д��ٵĿ�ʼʱ�䡣");
			return;
		}
		sysLeaveModelParameter.setBeginTime(beginTime);
		String endTime = sysLeaveModel.getEndTime();
		if(FormatEmpty.isEmpty(endTime)) {
			System.out.println("û����д��ٵĽ���ʱ��");
			return;
		}
		sysLeaveModelParameter.setEndTime(endTime);
		sysLeaveModelParameter.setStatus(3);
		//��ʼ������ʱ��͸���ʱ��
		sysLeaveModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		sysLeaveModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		//�����ߺ͸����߸�ֵ
		String currentUserAccount =sysUserModel.getUserAccount() ;
		sysLeaveModelParameter.setCreator(currentUserAccount);
		sysLeaveModelParameter.setUpdater(currentUserAccount);
		sysLeaveModelParameter.setIsDelete(0);
		sysLeaveModelParameter.setIsEffect(1);
		sysLeaveModelParameter.setOrderBy((new Random()).nextDouble());
		
		String descr = sysLeaveModel.getDescr();
		if(FormatEmpty.isEmpty(descr)) {
			sysLeaveModelParameter.setDescr("��");
		}
		sysLeaveModelParameter.setDescr(descr);
		sysLeaveService.insert(sysLeaveModelParameter);
		response.getWriter().write("{\"success\":\"true\"}");
	}
	@RequestMapping("/logOffLeavel.do")
	public void logOffLeavel(SysLeaveModel sysLeaveModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		Integer leavelId = sysLeaveModel.getId();
		if(leavelId == null) {
			System.out.println("������idû�д��ݹ���������ע������");
			return ;
		}
		
		HttpSession session = request.getSession();
		//��session����ȡ����ǰ�ĵ�½�û��˺�
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		
			SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
			sysLeaveModelParameter.setId(leavelId);
			sysLeaveModelParameter.setIsEffect(0);
			sysLeaveModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sysLeaveModelParameter.setUpdater(currentUserAccount);			
			sysLeaveService.updateActive(sysLeaveModelParameter);		
		    response.getWriter().write("{\"success\":\"true\"}");
	}
	
	
	
	
	@RequestMapping("/getLeaveInformation.do")
	public void getLeaveInformation(HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		HttpSession session = request.getSession();
        //��session��ȡ����ǰ���ڵ�½���û���code
		SysUserModel  sysUserModel = (SysUserModel)session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("�û�û�е�½��û���������");
			return;
		}	
		sysLeaveModelParameter.setUserCode(sysUserModel.getCode());	
		sysLeaveModelParameter.setIsEffect(1);
		List<SysLeaveModel> sysLeaveModelList = sysLeaveService.selectLeaveTableAndUserAndDictionary(sysLeaveModelParameter);
	    if(FormatEmpty.isEmpty(sysLeaveModelList)) {
	    	System.out.println("�û�û�������");
	    	return;	    	
	    }
	    SysLeaveModel sysLeaveModelResult = sysLeaveModelList.get(0);
	    Map sysLeaveModelMap = new HashMap();
	    
	    sysLeaveModelMap.put("id", sysLeaveModelResult.getId());
	    sysLeaveModelMap.put("code", sysLeaveModelResult.getCode());
	    sysLeaveModelMap.put("userTrueName", sysLeaveModelResult.getSysUserModel().getUserTrueName());
	    
	    sysLeaveModelMap.put("why", sysLeaveModelResult.getWhy())  ;
	    sysLeaveModelMap.put("createTime", sysLeaveModelResult.getCreateTime());
	    sysLeaveModelMap.put("beginTime", sysLeaveModelResult.getBeginTime());
	    sysLeaveModelMap.put("endTime", sysLeaveModelResult.getEndTime());
	    sysLeaveModelMap.put("descr", sysLeaveModelResult.getDescr());
	    sysLeaveModelMap.put("status", sysLeaveModelResult.getStatus());
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();
	    sysLeaveModelMapList.add(sysLeaveModelMap);
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
	   response.getWriter().write(sysLeaveModelMapListResult.toString());
	  
	}
	@RequestMapping("/getLeaveInformationByTeacher.do")
	public void getLeaveInformationByTeacher(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		HttpSession session = request.getSession();
        //��session��ȡ����ǰ���ڵ�½���û���code
		SysUserModel  sysUserModel = (SysUserModel)session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("�û�û�е�½��û���������");
			return;
		}	
		//���������䵱�н����������ڰ༶��classcode ��������ٱ��е�code�У���������һ��ȡ�ɵķ�����
		sysLeaveModelParameter.setCode(sysUserModel.getClassCode());
		sysLeaveModelParameter.setIsEffect(1);
		sysLeaveModelParameter.setStatus(3);
		
		ArrayList<SysLeaveModel> sysLeaveModelList = (ArrayList<SysLeaveModel>) sysLeaveService.selectLeaveTableAndUserAndDictionaryByTeacher(sysLeaveModelParameter);
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();
	    
	    for(SysLeaveModel sysLeaveModelForEach :sysLeaveModelList) {
	    	Map sysLeaveModelMap = new HashMap();
		    sysLeaveModelMap.put("userTrueName", sysLeaveModelForEach.getSysUserModel().getUserTrueName());
		    sysLeaveModelMap.put("id", sysLeaveModelForEach.getId());
		    sysLeaveModelMap.put("why", sysLeaveModelForEach.getWhy())  ;
		    sysLeaveModelMap.put("createTime", sysLeaveModelForEach.getCreateTime());
		    sysLeaveModelMap.put("beginTime", sysLeaveModelForEach.getBeginTime());
		    sysLeaveModelMap.put("endTime", sysLeaveModelForEach.getEndTime());
		    sysLeaveModelMap.put("descr", sysLeaveModelForEach.getDescr());
		    sysLeaveModelMap.put("status", sysLeaveModelForEach.getStatus());
		    sysLeaveModelMapList.add(sysLeaveModelMap);		    
	    }
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
		response.getWriter().write(sysLeaveModelMapListResult.toString());
	}
	@RequestMapping("/updateLeavelStatus.do")
	public void updateLeavelStatus(String ids , String status , HttpServletRequest request , HttpServletResponse response) throws Exception {
		if(FormatEmpty.isEmpty(ids)) {
			System.out.println("û��ѡ��Ҫɾ�����û�");
			response.getWriter().write( "{\"success\":\"false\"}");
		}
		String leavelModelIdArray[] = ids.split(",");
		HttpSession session = request.getSession();
		//��session����ȡ����ǰ�ĵ�½�û��˺�
		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
		for(String leavelModelId : leavelModelIdArray) {
			SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
			sysLeaveModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sysLeaveModelParameter.setUpdater(currentUserAccount);
			sysLeaveModelParameter.setStatus(Integer.valueOf(status));
			sysLeaveModelParameter.setId(Integer.valueOf(leavelModelId));
			sysLeaveService.updateActive(sysLeaveModelParameter);
		}
		response.getWriter().write("{\"success\":\"true\"}");
	}
	
	@RequestMapping("/getLeaveInformationByEducational.do")
	public void getLeaveInformationByEducational(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		sysLeaveModelParameter.setIsEffect(1);
		sysLeaveModelParameter.setStatus(6);
		
		ArrayList<SysLeaveModel> sysLeaveModelList = (ArrayList<SysLeaveModel>) sysLeaveService.selectLeaveTableAndUserAndDictionary(sysLeaveModelParameter);
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();
	    
	    for(SysLeaveModel sysLeaveModelForEach :sysLeaveModelList) {
	    	Map sysLeaveModelMap = new HashMap();
		    sysLeaveModelMap.put("userTrueName", sysLeaveModelForEach.getSysUserModel().getUserTrueName());
		    sysLeaveModelMap.put("id", sysLeaveModelForEach.getId());
		    sysLeaveModelMap.put("why", sysLeaveModelForEach.getWhy())  ;
		    sysLeaveModelMap.put("createTime", sysLeaveModelForEach.getCreateTime());
		    sysLeaveModelMap.put("beginTime", sysLeaveModelForEach.getBeginTime());
		    sysLeaveModelMap.put("endTime", sysLeaveModelForEach.getEndTime());
		    sysLeaveModelMap.put("descr", sysLeaveModelForEach.getDescr());
		    sysLeaveModelMap.put("status", sysLeaveModelForEach.getStatus());
		    sysLeaveModelMapList.add(sysLeaveModelMap);		    
	    }
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
		response.getWriter().write(sysLeaveModelMapListResult.toString());
	}
	
	@RequestMapping("/selectLeaveRecordForStudents.do")
	public void selectLeaveRecordForStudents(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		HttpSession session = request.getSession();
        //��session��ȡ����ǰ���ڵ�½���û���code
		SysUserModel  sysUserModel = (SysUserModel)session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("�û�û�е�½��û���������");
			return;
		}	
		sysLeaveModelParameter.setUserCode(sysUserModel.getCode());	
		
		List<SysLeaveModel> sysLeaveModelList = sysLeaveService.selectLeaveTableAndUserAndDictionary(sysLeaveModelParameter);
	    if(FormatEmpty.isEmpty(sysLeaveModelList)) {
	    	System.out.println("�û�û�������");
	    	return;	    	
	    }	    	   
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();	    
	    for(SysLeaveModel sysLeaveModelForEach : sysLeaveModelList) {
	    	Map sysLeaveModelMap = new HashMap();		    
		    sysLeaveModelMap.put("id", sysLeaveModelForEach.getId());
		    sysLeaveModelMap.put("code", sysLeaveModelForEach.getCode());
		    sysLeaveModelMap.put("userTrueName", sysLeaveModelForEach.getSysUserModel().getUserTrueName());		    
		    sysLeaveModelMap.put("why", sysLeaveModelForEach.getWhy())  ;
		    sysLeaveModelMap.put("createTime", sysLeaveModelForEach.getCreateTime());
		    sysLeaveModelMap.put("beginTime", sysLeaveModelForEach.getBeginTime());
		    sysLeaveModelMap.put("endTime", sysLeaveModelForEach.getEndTime());		   
		    sysLeaveModelMap.put("status", sysLeaveModelForEach.getStatus());
		    sysLeaveModelMap.put("isEffect", sysLeaveModelForEach.getIsEffect());		    
		    sysLeaveModelMap.put("descr", sysLeaveModelForEach.getDescr());
		    sysLeaveModelMapList.add(sysLeaveModelMap);
	    }	    
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
	    response.getWriter().write(sysLeaveModelMapListResult.toString());
	}
	
	@RequestMapping("/selectLeaveRecordForTeacher.do")
	public void selectLeaveRecordForTeacher(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();
		HttpSession session = request.getSession();
        //��session��ȡ����ǰ���ڵ�½���û���code
		SysUserModel  sysUserModel = (SysUserModel)session.getAttribute("currentUser");
		if(sysUserModel==null) {
			System.out.println("�û�û�е�½��û���������");
			return;
		}	
		//���������䵱�н����������ڰ༶��classcode ��������ٱ��е�code�У���������һ��ȡ�ɵķ�����
		sysLeaveModelParameter.setCode(sysUserModel.getClassCode());
		ArrayList<SysLeaveModel> sysLeaveModelList = (ArrayList<SysLeaveModel>) sysLeaveService.selectLeaveTableAndUserAndDictionaryForRecordByTeacher(sysLeaveModelParameter);
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();
	    
	    for(SysLeaveModel sysLeaveModelForEach :sysLeaveModelList) {
	    	Map sysLeaveModelMap = new HashMap();
	    	
	    	sysLeaveModelMap.put("id", sysLeaveModelForEach.getId());
		    sysLeaveModelMap.put("code", sysLeaveModelForEach.getCode());
		    sysLeaveModelMap.put("userTrueName", sysLeaveModelForEach.getSysUserModel().getUserTrueName());		    
		    sysLeaveModelMap.put("why", sysLeaveModelForEach.getWhy())  ;
		    sysLeaveModelMap.put("createTime", sysLeaveModelForEach.getCreateTime());
		    sysLeaveModelMap.put("beginTime", sysLeaveModelForEach.getBeginTime());
		    sysLeaveModelMap.put("endTime", sysLeaveModelForEach.getEndTime());		   
		    sysLeaveModelMap.put("status", sysLeaveModelForEach.getStatus());
		    sysLeaveModelMap.put("isEffect", sysLeaveModelForEach.getIsEffect());		    
		    sysLeaveModelMap.put("descr", sysLeaveModelForEach.getDescr());
		    sysLeaveModelMapList.add(sysLeaveModelMap);
	    }
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
		response.getWriter().write(sysLeaveModelMapListResult.toString());		
	}
	
	@RequestMapping("/selectLeaveRecordForEducation.do")
	public void selectLeaveRecordForEducation(HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysLeaveModel sysLeaveModelParameter = new SysLeaveModel();		
		ArrayList<SysLeaveModel> sysLeaveModelList = (ArrayList<SysLeaveModel>) sysLeaveService.selectLeaveTableAndUserAndDictionary(sysLeaveModelParameter);
	    ArrayList<Map<String , String >> sysLeaveModelMapList = new ArrayList();
	    
	    for(SysLeaveModel sysLeaveModelForEach :sysLeaveModelList) {
	    	Map sysLeaveModelMap = new HashMap();
	    	sysLeaveModelMap.put("id", sysLeaveModelForEach.getId());
		    sysLeaveModelMap.put("code", sysLeaveModelForEach.getCode());
		    sysLeaveModelMap.put("userTrueName", sysLeaveModelForEach.getSysUserModel().getUserTrueName());		    
		    sysLeaveModelMap.put("why", sysLeaveModelForEach.getWhy())  ;
		    sysLeaveModelMap.put("createTime", sysLeaveModelForEach.getCreateTime());
		    sysLeaveModelMap.put("beginTime", sysLeaveModelForEach.getBeginTime());
		    sysLeaveModelMap.put("endTime", sysLeaveModelForEach.getEndTime());		   
		    sysLeaveModelMap.put("status", sysLeaveModelForEach.getStatus());
		    sysLeaveModelMap.put("isEffect", sysLeaveModelForEach.getIsEffect());		    
		    sysLeaveModelMap.put("descr", sysLeaveModelForEach.getDescr());
		    sysLeaveModelMapList.add(sysLeaveModelMap);		    
	    }
	    JSONArray sysLeaveModelMapListResult = JSONArray.fromObject(sysLeaveModelMapList);
		response.getWriter().write(sysLeaveModelMapListResult.toString());
	}
	
}
