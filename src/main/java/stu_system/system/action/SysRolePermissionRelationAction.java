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
        	 System.out.println("角色的code没有传过来没法保存权限菜单");
        	 return;
         }
    	 sysRolePermissionRelationModelParameter.setRoleCode(roleCode);
    	 String menuCode = sysRolePermissionRelationModel.getMenuCode();
    	 if(FormatEmpty.isEmpty(menuCode)) {
    		 System.out.println("菜单的code没有传递过来，没法保存权限");
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
	 		//从session域中取出当前的登陆用户账号
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("当前用户还没有登陆");
	 			return;
	 		}
	 		sysRolePermissionRelationModelParameter.setCreator(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setUpdater(currentUserAccount);
	 		sysRolePermissionRelationModelParameter.setIsDelete(0);
	 		sysRolePermissionRelationModelParameter.setIsEffect(1);
	 		sysRolePermissionRelationModelParameter.setOrderBy((new Random()).nextDouble());
	 		sysRolePermissionRelationModelParameter.setDescr("无");
	 		sysRolePermissionRelationService.insert(sysRolePermissionRelationModelParameter);	 		
     }
     public void saveRolePermissionRelByUpdate(SysRolePermissionRelationModel sysRolePermissionRelationModel , HttpServletRequest request) throws Exception {
    	 
    	 SysRolePermissionRelationModel sysRolePermissionRelationModelParameter = new SysRolePermissionRelationModel();
    	 sysRolePermissionRelationModelParameter.setId(sysRolePermissionRelationModel.getId());
    	 sysRolePermissionRelationModelParameter.setCode(sysRolePermissionRelationModel.getCode());
 	     sysRolePermissionRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	 		HttpSession session = request.getSession();
	 		//从session域中取出当前的登陆用户账号
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("当前用户还没有登陆");
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
        	 System.out.println("要删除的id没有传过来，没法进行删除");
        	 return;
         }
         sysRolePermissionRelationModelParameter.setId(id);
    	 /*String roleCode = sysRolePermissionRelationModel.getRoleCode();
    	 if(FormatEmpty.isEmpty(roleCode)) {
        	 System.out.println("角色的code没有传过来没法保存权限菜单");
        	 return;
         }
    	 sysRolePermissionRelationModelParameter.setRoleCode(roleCode);
    	 String menuCode = sysRolePermissionRelationModel.getMenuCode();
    	 if(FormatEmpty.isEmpty(menuCode)) {
    		 System.out.println("菜单的code没有传递过来，没法保存权限");
    		 return ;    		 
    	 }
    	 sysRolePermissionRelationModelParameter.setMenuCode(menuCode);*/
         sysRolePermissionRelationModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	 		HttpSession session = request.getSession();
	 		//从session域中取出当前的登陆用户账号
	 		String currentUserAccount =((SysUserModel)session.getAttribute("currentUser")).getUserAccount() ;
	 		if(FormatEmpty.isEmpty(currentUserAccount)) {
	 			System.out.println("当前用户还没有登陆");
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
    		 System.out.println("用户的角色code为空，不能在主页中加载该角色的权限");
    		 return;
    	 }
    	sysPermissionModelParameter.getSysRolePermissionRelationModel().setRoleCode(roleCode);
 	    ArrayList<SysPermissionModel> sysPermissionModelList = (ArrayList<SysPermissionModel>) sysRolePermissionRelationService.selectPermissionAndRolePermissionRel(sysPermissionModelParameter);
        if(FormatEmpty.isEmpty(sysPermissionModelList)) {
        	response.getWriter().write("[]");
        	System.out.println("该角色没有任何的权限");
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
