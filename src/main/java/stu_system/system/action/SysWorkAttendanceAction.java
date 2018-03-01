package stu_system.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysUserModel;
import stu_system.system.model.SysWorkAttendanceModel;
import stu_system.system.service.SysWorkAttendanceService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;
@Controller("sysWorkAttendanceAction")
@RequestMapping("/sysWorkAttendanceAction")
public class SysWorkAttendanceAction extends BaseAction {

	 @Autowired
	 private SysWorkAttendanceService<SysWorkAttendanceModel> sysWorkAttendanceService ;
     @RequestMapping("/saveAttendenceRecord.do")
     public void saveAttendenceRecord(SysWorkAttendanceModel sysWorkAttendanceModel , String userCodes , HttpServletRequest request , HttpServletResponse response) throws Exception {
    	 
    	 
    	 if(FormatEmpty.isEmpty(userCodes)) {
 			System.out.println("û��ѡ��Ҫ�ȼ����ڵ��û�");
 			response.getWriter().write( "{\"success\":\"false\"}");
 		}
 		 String userCodeArray[] = userCodes.split(",");   	 
    	 SysWorkAttendanceModel sysWorkAttendanceModelParameter = new SysWorkAttendanceModel();
    	 String time = sysWorkAttendanceModel.getTime();
         if(FormatEmpty.isEmpty(time)) {
        	 System.out.println("û�л��ʱ��");
        	 return;
         }         
         sysWorkAttendanceModelParameter.setTime(time);
    	 if(sysWorkAttendanceModel.getOneCheck()==null) {
    		 System.out.println("��һ�δ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setOneCheck(sysWorkAttendanceModel.getOneCheck());
    	 if(sysWorkAttendanceModel.getTwoCheck()==null) {
    		 System.out.println("�ڶ��δ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setTwoCheck(sysWorkAttendanceModel.getTwoCheck());
    	 if(sysWorkAttendanceModel.getThreeCheck()==null) {
    		 System.out.println("�����δ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setThreeCheck(sysWorkAttendanceModel.getThreeCheck());
    	 
    	 if(sysWorkAttendanceModel.getFourCheck()==null) {
    		 System.out.println("���Ĵδ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setFourCheck(sysWorkAttendanceModel.getFourCheck());
    	 
    	 if(sysWorkAttendanceModel.getFiveCheck()==null) {
    		 System.out.println("����δ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setFiveCheck(sysWorkAttendanceModel.getFiveCheck());
    	 
    	 if(sysWorkAttendanceModel.getSixCheck()==null) {
    		 System.out.println("�����δ�û�м�¼");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setSixCheck(sysWorkAttendanceModel.getSixCheck());
    	 HttpSession session = request.getSession();
         SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
         if(sysUserModel== null) {
        	 System.out.println("�û�û�е�½");
        	 return;
         }
         sysWorkAttendanceModelParameter.setCreator(sysUserModel.getUserAccount());
         sysWorkAttendanceModelParameter.setUpdater(sysUserModel.getUserAccount());
         sysWorkAttendanceModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
         sysWorkAttendanceModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
         sysWorkAttendanceModelParameter.setIsDelete(0);
         sysWorkAttendanceModelParameter.setIsEffect(1);
         sysWorkAttendanceModelParameter.setDescr("��");
         for(String userCode :userCodeArray) {
        	 sysWorkAttendanceModelParameter.setCode(UniqueCode.getUUID());
        	 sysWorkAttendanceModelParameter.setUserCode(userCode);
        	 sysWorkAttendanceModelParameter.setOrderBy((new Random()).nextDouble());
        	 sysWorkAttendanceService.insert(sysWorkAttendanceModelParameter);         
         }
        
         response.getWriter().write( "{\"success\":\"true\"}");
     
     }
     @RequestMapping("/selectAttendenceInformation.do")
     public void selectAttendenceInformation(SysWorkAttendanceModel sysWorkAttendanceModel ,  HttpServletRequest request , HttpServletResponse response) throws Exception {
    	 SysWorkAttendanceModel sysWorkAttendanceModelParameter = new SysWorkAttendanceModel();
    	 String time = sysWorkAttendanceModel.getTime();
    	 if(FormatEmpty.isEmpty(time)) {
    		 System.out.println("�û�û����������");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setTime(time);
    	 sysWorkAttendanceModelParameter.setUserTrueName(sysWorkAttendanceModel.getUserTrueName());
    	 HttpSession session =   request.getSession();
    	 SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
    	 if(sysUserModel== null) {
    		 System.out.println("������û�е�½");
    		 return ;
    	 }
    	 sysWorkAttendanceModelParameter.getSysUserModel().setClassCode(sysUserModel.getClassCode());
    	 ArrayList<SysWorkAttendanceModel> sysWorkAttendanceModelList =   (ArrayList<SysWorkAttendanceModel>) sysWorkAttendanceService.selectAttendenceStatus(sysWorkAttendanceModelParameter);
    	 
    	 ArrayList<Map<String , String>> sysWorkAttendanceModelMapList = new ArrayList();
    	 for(SysWorkAttendanceModel sysWorkAttendanceModelForEach :sysWorkAttendanceModelList) {
    		 Map sysWorkAttendanceModelMap = new HashMap();
    		 sysWorkAttendanceModelMap.put("id", sysWorkAttendanceModelForEach.getUserId());
    		 sysWorkAttendanceModelMap.put("code", sysWorkAttendanceModelForEach.getCode());
    		 sysWorkAttendanceModelMap.put("userTrueName", sysWorkAttendanceModelForEach.getUserTrueName());
    		 sysWorkAttendanceModelMap.put("time", sysWorkAttendanceModelForEach.getTime());
    		 sysWorkAttendanceModelMap.put("oneCheck", sysWorkAttendanceModelForEach.getOneCheckName());
    		 sysWorkAttendanceModelMap.put("twoCheck", sysWorkAttendanceModelForEach.getTwoCheckName());
    		 sysWorkAttendanceModelMap.put("threeCheck",sysWorkAttendanceModelForEach.getThreeCheckName());
    		 sysWorkAttendanceModelMap.put("fourCheck", sysWorkAttendanceModelForEach.getFourCheckName());
    		 sysWorkAttendanceModelMap.put("fiveCheck", sysWorkAttendanceModelForEach.getFiveCheckName());
    		 sysWorkAttendanceModelMap.put("sixCheck", sysWorkAttendanceModelForEach.getSixCheckName());
    		 sysWorkAttendanceModelMapList.add(sysWorkAttendanceModelMap);
    	 }
    	 JSONArray sysWorkAttendanceModelMapListResult = JSONArray.fromObject(sysWorkAttendanceModelMapList);
    	 response.getWriter().write( sysWorkAttendanceModelMapListResult.toString());	 
     }
   @RequestMapping("/updateUserAttendence.do")
   public void updateUserAttendence(SysWorkAttendanceModel sysWorkAttendanceModel , String attendenceCodes , HttpServletRequest request , HttpServletResponse response) throws Exception {
	   if(FormatEmpty.isEmpty(attendenceCodes)) {
			System.out.println("û��ѡ��Ҫ�ȼ����ڵ��û�");
			response.getWriter().write( "{\"success\":\"false\"}");
		}
		 String attendenceCodeArray[] = attendenceCodes.split(",");   	 
   	 SysWorkAttendanceModel sysWorkAttendanceModelParameter = new SysWorkAttendanceModel();
   	 
   	 if(sysWorkAttendanceModel.getOneCheck()==null) {
   		 System.out.println("��һ�δ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setOneCheck(sysWorkAttendanceModel.getOneCheck());
   	 if(sysWorkAttendanceModel.getTwoCheck()==null) {
   		 System.out.println("�ڶ��δ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setTwoCheck(sysWorkAttendanceModel.getTwoCheck());
   	 if(sysWorkAttendanceModel.getThreeCheck()==null) {
   		 System.out.println("�����δ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setThreeCheck(sysWorkAttendanceModel.getThreeCheck());
   	 
   	 if(sysWorkAttendanceModel.getFourCheck()==null) {
   		 System.out.println("���Ĵδ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setFourCheck(sysWorkAttendanceModel.getFourCheck());
   	 
   	 if(sysWorkAttendanceModel.getFiveCheck()==null) {
   		 System.out.println("����δ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setFiveCheck(sysWorkAttendanceModel.getFiveCheck());
   	 
   	 if(sysWorkAttendanceModel.getSixCheck()==null) {
   		 System.out.println("�����δ�û�м�¼");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setSixCheck(sysWorkAttendanceModel.getSixCheck());
   	 HttpSession session = request.getSession();
        SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
        if(sysUserModel== null) {
       	 System.out.println("�û�û�е�½");
       	 return;
        }       
        sysWorkAttendanceModelParameter.setUpdater(sysUserModel.getUserAccount());
        sysWorkAttendanceModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
        for(String attendenceCode :attendenceCodeArray) {
       	 sysWorkAttendanceModelParameter.setCode(attendenceCode);
       	 sysWorkAttendanceService.updateActive(sysWorkAttendanceModelParameter);       
        }
        response.getWriter().write( "{\"success\":\"true\"}"); 
   }
    @RequestMapping("/selectAttendenceINFForSTU.do")
   public void selectAttendenceINFForSTU(SysWorkAttendanceModel sysWorkAttendanceModel ,  HttpServletRequest request , HttpServletResponse response) throws Exception {
    	SysWorkAttendanceModel sysWorkAttendanceModelParameter = new SysWorkAttendanceModel();
   	 String time = sysWorkAttendanceModel.getTime();
   	 if(FormatEmpty.isEmpty(time)) {
   		 System.out.println("�û�û����������");
   		 return;
   	 }
   	 sysWorkAttendanceModelParameter.setTime(time);   	 
   	 HttpSession session =   request.getSession();
   	 SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
   	 if(sysUserModel== null) {
   		 System.out.println("ѧ����ѯ�Լ��Ŀ�����������Ǹ�ѧ��û�е�½");
   		 return ;
   	 }
   	 sysWorkAttendanceModelParameter.setUserCode(sysUserModel.getCode());
   	 ArrayList<SysWorkAttendanceModel> sysWorkAttendanceModelList =   (ArrayList<SysWorkAttendanceModel>) sysWorkAttendanceService.selectAttendenceStatus(sysWorkAttendanceModelParameter);
   	 
   	 ArrayList<Map<String , String>> sysWorkAttendanceModelMapList = new ArrayList();
   	 for(SysWorkAttendanceModel sysWorkAttendanceModelForEach :sysWorkAttendanceModelList) {
   		 Map sysWorkAttendanceModelMap = new HashMap();
   		 sysWorkAttendanceModelMap.put("id", sysWorkAttendanceModelForEach.getUserId());
   		 sysWorkAttendanceModelMap.put("code", sysWorkAttendanceModelForEach.getCode());
   		 sysWorkAttendanceModelMap.put("userTrueName", sysWorkAttendanceModelForEach.getUserTrueName());
   		 sysWorkAttendanceModelMap.put("time", sysWorkAttendanceModelForEach.getTime());
   		 sysWorkAttendanceModelMap.put("oneCheck", sysWorkAttendanceModelForEach.getOneCheckName());
   		 sysWorkAttendanceModelMap.put("twoCheck", sysWorkAttendanceModelForEach.getTwoCheckName());
   		 sysWorkAttendanceModelMap.put("threeCheck",sysWorkAttendanceModelForEach.getThreeCheckName());
   		 sysWorkAttendanceModelMap.put("fourCheck", sysWorkAttendanceModelForEach.getFourCheckName());
   		 sysWorkAttendanceModelMap.put("fiveCheck", sysWorkAttendanceModelForEach.getFiveCheckName());
   		 sysWorkAttendanceModelMap.put("sixCheck", sysWorkAttendanceModelForEach.getSixCheckName());
   		 sysWorkAttendanceModelMapList.add(sysWorkAttendanceModelMap);
   	 }
   	 JSONArray sysWorkAttendanceModelMapListResult = JSONArray.fromObject(sysWorkAttendanceModelMapList);
   	 response.getWriter().write( sysWorkAttendanceModelMapListResult.toString());	 
    }
    
    @RequestMapping("/selectAttendenceINFForEDU.do")
    public void selectAttendenceINFForEDU(SysWorkAttendanceModel sysWorkAttendanceModel ,  HttpServletRequest request , HttpServletResponse response) throws Exception {
     	SysWorkAttendanceModel sysWorkAttendanceModelParameter = new SysWorkAttendanceModel();
    	 String time = sysWorkAttendanceModel.getTime();
    	 if(FormatEmpty.isEmpty(time)) {
    		 System.out.println("�û�û����������");
    		 return;
    	 }
    	 sysWorkAttendanceModelParameter.setTime(time);   	 
    	 ArrayList<SysWorkAttendanceModel> sysWorkAttendanceModelList =   (ArrayList<SysWorkAttendanceModel>) sysWorkAttendanceService.selectAttendenceStatus(sysWorkAttendanceModelParameter);
    	 
    	 ArrayList<Map<String , String>> sysWorkAttendanceModelMapList = new ArrayList();
    	 for(SysWorkAttendanceModel sysWorkAttendanceModelForEach :sysWorkAttendanceModelList) {
    		 Map sysWorkAttendanceModelMap = new HashMap();
    		 sysWorkAttendanceModelMap.put("id", sysWorkAttendanceModelForEach.getUserId());
    		 sysWorkAttendanceModelMap.put("code", sysWorkAttendanceModelForEach.getCode());
    		 sysWorkAttendanceModelMap.put("userTrueName", sysWorkAttendanceModelForEach.getUserTrueName());
    		 sysWorkAttendanceModelMap.put("time", sysWorkAttendanceModelForEach.getTime());
    		 sysWorkAttendanceModelMap.put("oneCheck", sysWorkAttendanceModelForEach.getOneCheckName());
    		 sysWorkAttendanceModelMap.put("twoCheck", sysWorkAttendanceModelForEach.getTwoCheckName());
    		 sysWorkAttendanceModelMap.put("threeCheck",sysWorkAttendanceModelForEach.getThreeCheckName());
    		 sysWorkAttendanceModelMap.put("fourCheck", sysWorkAttendanceModelForEach.getFourCheckName());
    		 sysWorkAttendanceModelMap.put("fiveCheck", sysWorkAttendanceModelForEach.getFiveCheckName());
    		 sysWorkAttendanceModelMap.put("sixCheck", sysWorkAttendanceModelForEach.getSixCheckName());
    		 sysWorkAttendanceModelMapList.add(sysWorkAttendanceModelMap);
    	 }
    	 JSONArray sysWorkAttendanceModelMapListResult = JSONArray.fromObject(sysWorkAttendanceModelMapList);
    	 response.getWriter().write( sysWorkAttendanceModelMapListResult.toString());	 
     }
    
    
}
