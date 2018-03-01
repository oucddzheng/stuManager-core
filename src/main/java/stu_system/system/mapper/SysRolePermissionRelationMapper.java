package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.system.model.SysPermissionModel;

public interface SysRolePermissionRelationMapper<T> extends BaseMapper<T> {
	List<SysPermissionModel>selectPermissionAndRolePermissionRel(SysPermissionModel sysPermissionModel);
}
