package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.system.model.SysAasignmentModel;
import stu_system.system.model.SysAssignmentConditionModel;
import stu_system.system.model.SysUserModel;

public interface SysAssignmentConditionMapper<T> extends BaseMapper<T> {
	//������ĺ�����û��ʹ�÷��ͣ����õ��Լ��������������	
	List<SysAasignmentModel> selectAssignmentAndAssignment_conditionAndUser(SysUserModel sysUserModel);     
	List<SysAssignmentConditionModel> selectUserAndAssignment_condition(SysAasignmentModel sysAasignmentModel);
}
