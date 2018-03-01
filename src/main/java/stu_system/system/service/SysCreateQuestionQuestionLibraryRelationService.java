package stu_system.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysCreateQuestionQuestionLibraryRelationMapper;

@Service("sysCreateQuestionQuestionLibraryRelationService")
public class SysCreateQuestionQuestionLibraryRelationService<T> extends BaseService<T> {

	@Autowired
	private SysCreateQuestionQuestionLibraryRelationMapper sysCreateQuestionQuestionLibraryRelationMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysCreateQuestionQuestionLibraryRelationMapper;
	}

}
