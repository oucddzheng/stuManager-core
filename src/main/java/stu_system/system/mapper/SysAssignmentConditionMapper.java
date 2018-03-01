package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.system.model.SysAasignmentModel;
import stu_system.system.model.SysAssignmentConditionModel;
import stu_system.system.model.SysUserModel;

public interface SysAssignmentConditionMapper<T> extends BaseMapper<T> {
	//在下面的函数中没有使用泛型，是用的自己定义的数据类型	
	List<SysAasignmentModel> selectAssignmentAndAssignment_conditionAndUser(SysUserModel sysUserModel);     
	List<SysAssignmentConditionModel> selectUserAndAssignment_condition(SysAasignmentModel sysAasignmentModel);
}
