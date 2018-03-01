package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysAssignmentConditionMapper;
import stu_system.system.mapper.SysUserMapper;
import stu_system.system.model.SysAasignmentModel;
import stu_system.system.model.SysAssignmentConditionModel;
import stu_system.system.model.SysUserModel;
@Service("sysAasignmentConditionService")
public class SysAasignmentConditionService<T> extends BaseService<T> {

	@Autowired
	private SysAssignmentConditionMapper sysAssignmentConditionMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysAssignmentConditionMapper;
	}
	
	public List<SysAasignmentModel> selectAssignmentAndAssignment_conditionAndUser(SysUserModel sysUserModel){
		return sysAssignmentConditionMapper.selectAssignmentAndAssignment_conditionAndUser(sysUserModel);
	}
	public List<SysAssignmentConditionModel> selectUserAndAssignment_condition(SysAasignmentModel sysAasignmentModel){
		return sysAssignmentConditionMapper.selectUserAndAssignment_condition(sysAasignmentModel);
	}
}
