package stu_system.system.action;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysClassModel;
import stu_system.system.service.SysClassService;

@Controller("sysClassAction")
@RequestMapping("/sysClassAction")
public class SysClassAction extends BaseAction {
	@Autowired
	private SysClassService<SysClassModel> sysClassService;
	@RequestMapping("/getClassName.do")
	public void getClassName(SysClassModel model , HttpServletRequest request , HttpServletResponse response) throws Exception{
		SysClassModel sysClassModelParameter = new SysClassModel();
		List<SysClassModel> sysClassModelList = sysClassService.selectAll(sysClassModelParameter);
		List<Map<String ,String>> sysClassModelListNeeded = new ArrayList<>();
		
		for (SysClassModel modelForEach :sysClassModelList) {
			Map<String , String> sysClassModelMap = new  HashMap<>();
			sysClassModelMap.put("code", modelForEach.getCode());
			sysClassModelMap.put("className",modelForEach.getClassName());
			sysClassModelListNeeded.add(sysClassModelMap);
		}
		JSONArray result = JSONArray.fromObject(sysClassModelListNeeded);
	    response.getWriter().write(result.toString()); 		
	}
	
	

}
