package stu_system.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import stu_system.core.action.BaseAction;
import stu_system.system.model.SysDictionaryModel;
import stu_system.system.service.SysDictionaryService;
import tools.FormatEmpty;
@Controller("sysDictionaryAction")
@RequestMapping("/sysDictionaryAction")
public class SysDictionaryAction extends BaseAction {
	@Autowired
	private SysDictionaryService<SysDictionaryModel> sysDictionaryService;
	@RequestMapping("/getDictionary.do")
	public void getDictionary(String type , HttpServletRequest request , HttpServletResponse response) throws Exception {
		SysDictionaryModel sysDictionaryModelParameter = new SysDictionaryModel();
		sysDictionaryModelParameter.setType(Integer.valueOf(type));
		ArrayList<SysDictionaryModel> sysDictionaryModelList = (ArrayList<SysDictionaryModel>) sysDictionaryService.selectAll(sysDictionaryModelParameter);
	    if(FormatEmpty.isEmpty(sysDictionaryModelList)) {
	    	System.out.println("字典表中没有要查询的数据");
	    	return;
	    }
		ArrayList<Map<String , String>> sysDictionaryModelMaplist = new ArrayList();
	     for(SysDictionaryModel sysDictionaryModelForEach : sysDictionaryModelList) {
	    	 Map sysDictionaryModelMap = new HashMap();
	    	 sysDictionaryModelMap.put("code", sysDictionaryModelForEach.getCode());
	    	 sysDictionaryModelMap.put("name", sysDictionaryModelForEach.getName());
	    	 sysDictionaryModelMaplist.add(sysDictionaryModelMap);	     
	     }	
	    JSONArray sysDictionaryModelMaplistResult = JSONArray.fromObject(sysDictionaryModelMaplist);
	    response.getWriter().write(sysDictionaryModelMaplistResult.toString());	
	}

}
