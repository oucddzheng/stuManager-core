package stu_system.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysStudentQuestionnaireRelMapper;
@Service("sysStudentQuestionnaireRelService")
public class SysStudentQuestionnaireRelService<T> extends BaseService<T> {

	@Autowired
	private SysStudentQuestionnaireRelMapper sysStudentQuestionnaireRelMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysStudentQuestionnaireRelMapper;
	}

}
