package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysUserModel;

public interface SysCreateQuestionMapper<T> extends BaseMapper<T> {
	 List<T> selectCreateQuestionAndStudentRelation(SysUserModel sysUserModel);
	 List<SysQuestionLibraryModel> selectQuestionForAnswerOfStu(T t);
	 
}
