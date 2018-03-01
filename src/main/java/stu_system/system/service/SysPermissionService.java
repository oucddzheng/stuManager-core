package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysPermissionMapper;

@Service("sysPermissionService")
public class SysPermissionService<T> extends BaseService<T> {
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysPermissionMapper;
	}
	
	public List<T> selectPermissionAndRolePermissionRel(T t){
		
		return sysPermissionMapper.selectPermissionAndRolePermissionRel(t);
		
	}
	

}
