package stu_system.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysPermissionModel;
import stu_system.system.model.SysUserModel;
import stu_system.system.service.SysPermissionService;
import tools.FormatEmpty;
@Controller("sysPermissionAction")
@RequestMapping("/sysPermissionAction")
public class SysPermissionAction extends BaseAction {
	
	@Autowired
	private SysPermissionService sysPermissionService;
	@RequestMapping("/selectPermission.do")
	public void selectPermission( SysPermissionModel sysPermissionModel, String roleCode ,HttpServletRequest request , HttpServletResponse response) throws IOException {
		
		SysPermissionModel sysPermissionModelParameter = new SysPermissionModel();
		
		HttpSession session = request.getSession();
		SysUserModel sysUserModel = (SysUserModel) session.getAttribute("currentUser");
		if(sysUserModel==null && sysUserModel.getRoleCode().equals("3")) {
			System.out.println("用户信息错误无法加载权限管理");
			return;
		}
		
		Integer type = sysPermissionModel.getType();
		if(type==null) {
			System.out.println("没有权限类型，无法加载权限");
			return;
		}
		sysPermissionModelParameter.setType(type);		
		if(FormatEmpty.isEmpty(roleCode)) {
			System.out.println("roleCode为空，没法进行查询");
		}
		sysPermissionModelParameter.getSysRolePermissionRelationModel().setRoleCode(roleCode);
		
		ArrayList<SysPermissionModel> sysPermissionModelList = (ArrayList<SysPermissionModel>)sysPermissionService.selectPermissionAndRolePermissionRel(sysPermissionModelParameter) ;
	    if(FormatEmpty.isEmpty(sysPermissionModelList)) {
	    	response.getWriter().write("[]");
	    }else {
	    	ArrayList<Map<String , String>> listMapRequired = new ArrayList();
	    	for(SysPermissionModel modelForEach : sysPermissionModelList) {
	    		Map<String , String> mapModel = new HashMap();
	    		mapModel.put("cb", "");
	    		mapModel.put("id", modelForEach.getId().toString());
	    		mapModel.put("code", modelForEach.getCode());
	    		mapModel.put("menuName", modelForEach.getPermissionName());	    			    		
	    		if(modelForEach.getSysRolePermissionRelationModel().getRoleCode()==null) {
	    			mapModel.put("operation", "");
		    		mapModel.put("idInRolePermissionRelation", "");

	    		}else {
	    			mapModel.put("operation", modelForEach.getSysRolePermissionRelationModel().getRoleCode());
		    		mapModel.put("idInRolePermissionRelation", modelForEach.getSysRolePermissionRelationModel().getId().toString());

	    		}	
	    		listMapRequired.add(mapModel);
	    	}
	    	  JSONArray sysPermissionModellistJson = JSONArray.fromObject(listMapRequired);	    	  
	    	   response.getWriter().write(sysPermissionModellistJson.toString());
	    }		
	}
	

}
