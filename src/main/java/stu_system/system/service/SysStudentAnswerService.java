package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysStudentAnswerMapper;
import stu_system.system.model.SysCreateQuestionModel;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysStudentQuestionnaireRelModel;
@Service("sysStudentAnswerService")
public class SysStudentAnswerService<T> extends BaseService<T> {

	@Autowired
	private SysStudentAnswerMapper sysStudentAnswerMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysStudentAnswerMapper;
	}
    public List<SysQuestionLibraryModel> selectQuestionLibraryAndStudentAnswer(SysStudentQuestionnaireRelModel sysStudentQuestionnaireRelModel){
    	
    	return sysStudentAnswerMapper.selectQuestionLibraryAndStudentAnswer(sysStudentQuestionnaireRelModel);
    }
    public List<SysQuestionLibraryModel> selectQuestionAnswerForAnalysis(SysCreateQuestionModel sysCreateQuestionModel){
    	return sysStudentAnswerMapper.selectQuestionAnswerForAnalysis(sysCreateQuestionModel);
    }
    
}
