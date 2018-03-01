package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;

public interface SysPermissionMapper<T> extends BaseMapper<T> {
	List<T>selectPermissionAndRolePermissionRel(T t);
	 

}
