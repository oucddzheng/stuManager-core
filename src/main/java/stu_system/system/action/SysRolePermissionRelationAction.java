package stu_system.system.action;

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

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysPermissionModel;
import stu_system.system.model.SysRolePermissionRelationModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysRolePermissionRelationService;
import tools.FormatCalendar;
import tools.FormatEmpty;
import tools.UniqueCode;
@Controller("sysRolePermissionRelationAction")
@RequestMapping("sysRolePermissionRelationAction")
public class SysRolePermissionRelationAction extends BaseAction {
	@Autowired
	 private SysRolePermissionRelationService<SysRolePermissionRelationModel> sysRolePermissionRelationService;
    @RequestMapping("/saveRolePermissionRel.do")
	public void saveRolePermissionRel(SysRolePermissionRelationModel sysRolePermissionRelationModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
    	 SysRolePermissionRelationModel sysRolePermissionRelationModelParameter = new SysRolePermissionRelationModel();
         String roleCode = sysRolePermissionRelationModel.getRoleCode();
    	 if(FormatEmpty.isEmpty(roleCode)) {
        	 System.out.println("��ɫ��codeû�д�����û������Ȩ�޲˵�");
        	 return;
         }
    	 sysRolePermissionRelationModelParameter.setRoleCode(roleCode);
    	 String menuCode = sysRolePermissionRelationModel.getMenuCode();
    	 if(FormatEmpty.isEmpty(menuCode)) {
    		 System.out.println("�˵���codeû�д��ݹ�����û������Ȩ��");
    		 return ;    		 
    	 }
    	 sysRolePermissionRelationModelParameter.setMenuCode(menuCode);
    	 ArrayList<SysRolePermissionRelationModel> sysRolePermissionRelationModelList = (ArrayList<SysRolePermissionRelationModel>) sysRolePermissionRelationService.selectAll(sysRolePermissionRelationModelParameter);
         if(sysRolePermissionRelationModelList.size()==0) {
        	 saveRolePermissionRelByInsert(sysRolePermissionRelationModelParameter , request);
         }else {
        	 SysRolePermissionRelationModel sysRolePermissionRelationModelFromlib = sysRolePermissionRelationModelList.get(0);
        	 sysRolePermissionRelationModelParameter.setId(sysRolePermissionRelationModelFromlib.getId());
        	 sysRolePermissionRelationModelParameter.setCode(sysRolePermissionRelationModelFromlib.getCode());
        	 saveRolePermissionRelByUpdate(sysRolePermissionRelationModelParameter , request);
         }    
         response.getWriter().write("{\"success\":\"true\"}");
     }
     public void saveRolePermissionRelByInsert(SysRolePermissionRelationModel sysRolePermissionRelationModelParameter , HttpServletRequest request ) throws Exception {
    	    sysRolePermissionRelationModelParameter.setCode(UniqueCode.getUUID());
    	    sysRolePermissionRelationModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
    	    sysRolePermissionRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	 		HttpSession session = request.getSession();
	 		//��session����ȡ����ǰ�ĵ�½�û��˺�
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("��ǰ�û���û�е�½");
	 			return;
	 		}
	 		sysRolePermissionRelationModelParameter.setCreator(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setUpdater(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setIsDelete(0);
	 		sysRolePermissionRelationModelParameter.setIsEffect(1);
	 		sysRolePermissionRelationModelParameter.setOrderBy((new Random()).nextDouble());
	 		sysRolePermissionRelationModelParameter.setDescr("��");
	 		sysRolePermissionRelationService.insert(sysRolePermissionRelationModelParameter);	 		
     }
     public void saveRolePermissionRelByUpdate(SysRolePermissionRelationModel sysRolePermissionRelationModel , HttpServletRequest request) throws Exception {
    	 
    	 SysRolePermissionRelationModel sysRolePermissionRelationModelParameter = new SysRolePermissionRelationModel();
    	 sysRolePermissionRelationModelParameter.setId(sysRolePermissionRelationModel.getId());
    	 sysRolePermissionRelationModelParameter.setCode(sysRolePermissionRelationModel.getCode());
 	     sysRolePermissionRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	 		HttpSession session = request.getSession();
	 		//��session����ȡ����ǰ�ĵ�½�û��˺�
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("��ǰ�û���û�е�½");
	 			return;
	 		}
	 		sysRolePermissionRelationModelParameter.setUpdater(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setIsDelete(0);
	 		sysRolePermissionRelationModelParameter.setIsEffect(1);	 		
	 		sysRolePermissionRelationService.updateActive(sysRolePermissionRelationModelParameter);	 		
     }
    @RequestMapping("/deleteRolePermissionRelByUpdate.do")
     public void deleteRolePermissionRelByUpdate(SysRolePermissionRelationModel sysRolePermissionRelationModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
    	 SysRolePermissionRelationModel sysRolePermissionRelationModelParameter = new SysRolePermissionRelationModel();
         Integer id = sysRolePermissionRelationModel.getId();
         if(id==null) {
        	 System.out.println("Ҫɾ����idû�д�������û������ɾ��");
        	 return;
         }
         sysRolePermissionRelationModelParameter.setId(id);
    	 /*String roleCode = sysRolePermissionRelationModel.getRoleCode();
    	 if(FormatEmpty.isEmpty(roleCode)) {
        	 System.out.println("��ɫ��codeû�д�����û������Ȩ�޲˵�");
        	 return;
         }
    	 sysRolePermissionRelationModelParameter.setRoleCode(roleCode);
    	 String menuCode = sysRolePermissionRelationModel.getMenuCode();
    	 if(FormatEmpty.isEmpty(menuCode)) {
    		 System.out.println("�˵���codeû�д��ݹ�����û������Ȩ��");
    		 return ;    		 
    	 }
    	 sysRolePermissionRelationModelParameter.setMenuCode(menuCode);*/
         sysRolePermissionRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	 		HttpSession session = request.getSession();
	 		//��session����ȡ����ǰ�ĵ�½�û��˺�
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("��ǰ�û���û�е�½");
	 			return;
	 		}
	 		sysRolePermissionRelationModelParameter.setUpdater(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setIsDelete(1);
	 		sysRolePermissionRelationModelParameter.setIsEffect(0);	 		
	 		sysRolePermissionRelationService.updateActive(sysRolePermissionRelationModelParameter);	
	 		response.getWriter().write("{\"success\":\"true\"}");        
     }
    @RequestMapping("/getPermissionInMain.do")
     public void getPermissionInMain(String roleCode , HttpServletRequest request , HttpServletResponse response) throws IOException {
    	 SysPermissionModel sysPermissionModelParameter =  new SysPermissionModel();
    	 if(FormatEmpty.isEmpty(roleCode)) {
    		 System.out.println("�û��Ľ�ɫcodeΪ�գ���������ҳ�м��ظý�ɫ��Ȩ��");
    		 return;
    	 }
    	sysPermissionModelParameter.getSysRolePermissionRelationModel().setRoleCode(roleCode);
 	    ArrayList<SysPermissionModel> sysPermissionModelList = (ArrayList<SysPermissionModel>) sysRolePermissionRelationService.selectPermissionAndRolePermissionRel(sysPermissionModelParameter);
        if(FormatEmpty.isEmpty(sysPermissionModelList)) {
        	response.getWriter().write("[]");
        	System.out.println("�ý�ɫû���κε�Ȩ��");
        	return;
        }
        ArrayList<HashMap> arrayListMapForMain = new ArrayList();
        for(SysPermissionModel sysPermissionModelForEach : sysPermissionModelList) {
        	if(sysPermissionModelForEach.getType()==1) {
        		HashMap<String , Object> sysPermissionModelMap = new HashMap();
        		sysPermissionModelMap.put("firstPermissionId", sysPermissionModelForEach.getId().toString());
        		sysPermissionModelMap.put("firstPermissionCode", sysPermissionModelForEach.getCode());
        		sysPermissionModelMap.put("firstPermissionName", sysPermissionModelForEach.getPermissionName());
        		ArrayList<HashMap> arrayListMap = new ArrayList();
        		for(SysPermissionModel sysPermissionModelForEach2 : sysPermissionModelList) {
        			    
        			if(sysPermissionModelForEach2.getParentCode().equals(sysPermissionModelForEach.getCode())) {
        				HashMap<String , String> sysPermissionModelMap2 = new HashMap();
                		sysPermissionModelMap2.put("secondPermissionId", sysPermissionModelForEach2.getId().toString());
                		sysPermissionModelMap2.put("secondPermissionCode", sysPermissionModelForEach2.getCode());
                		sysPermissionModelMap2.put("secondPermissionName", sysPermissionModelForEach2.getPermissionName());
                		sysPermissionModelMap2.put("url", sysPermissionModelForEach2.getUrl());
                		arrayListMap.add(sysPermissionModelMap2);        			
        			}       		
        		}
        		/*JSONArray arrayListMapJSON = JSONArray.fromObject(arrayListMap);*/
        		sysPermissionModelMap.put("secondPermission", arrayListMap);
        		arrayListMapForMain.add(sysPermissionModelMap);
        	}       	       	 
        }
        JSONArray arrayListMapForMainJSON = JSONArray.fromObject(arrayListMapForMain);
        response.getWriter().write(arrayListMapForMainJSON.toString());
       /* System.out.println(arrayListMapForMainJSON.toString());*/
        return;    	 
     }
}
