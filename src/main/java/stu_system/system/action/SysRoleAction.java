package stu_system.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysRoleModel;
import stu_system.system.service.SysRoleService;
@Controller("sysRoleAction")
@RequestMapping("/sysRoleAction")
public class SysRoleAction extends BaseAction {
	
   @Autowired
   private SysRoleService<SysRoleModel> sysRoleService;
   
   @RequestMapping("/getRoles.do")
   //这个方法时用来获得role角色表中的数据
   public void getRoles(HttpServletRequest request , HttpServletResponse response) throws Exception {
	  
	   SysRoleModel sysRoleModelParameter = new SysRoleModel();
	   List<SysRoleModel> sysRoleModelList = sysRoleService.selectAll(sysRoleModelParameter);
	   List<Map<String,String>> list = new ArrayList<>();
	   for(SysRoleModel model : sysRoleModelList) {
		   Map<String,String> map = new HashMap<>();
		   map.put("code", model.getCode());
		   map.put("roleName", model.getRoleName());
		   list.add(map);
	   }
       JSONArray result = JSONArray.fromObject(list);
       response.getWriter().write(result.toString());  
   }
	
	

}
