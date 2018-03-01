package stu_system.system.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysCreateQuestionMapper;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysUserModel;
@Service("sysCreateQuestionService")
public class SysCreateQuestionService<T> extends BaseService<T> {

	@Autowired
	private SysCreateQuestionMapper sysCreateQuestionMapper;
	
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCreateQuestionMapper;
	}
    public List<T> selectCreateQuestionAndStudentRelation(SysUserModel sysUserModel){
    	
    	return sysCreateQuestionMapper.selectCreateQuestionAndStudentRelation(sysUserModel);
    	
    }  
public List<SysQuestionLibraryModel> selectQuestionForAnswerOfStu(T t){
    	
    	return sysCreateQuestionMapper.selectQuestionForAnswerOfStu(t);
    	
    }  
    
}
