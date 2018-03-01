package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysRolePermissionRelationMapper;
import stu_system.system.model.SysPermissionModel;
@Service("sysRolePermissionRelationService")
public class SysRolePermissionRelationService<T> extends BaseService<T> {

	@Autowired
	private SysRolePermissionRelationMapper sysRolePermissionRelationMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysRolePermissionRelationMapper;
	}
    
    public List<SysPermissionModel> selectPermissionAndRolePermissionRel(SysPermissionModel sysPermissionModel){
		
		return sysRolePermissionRelationMapper.selectPermissionAndRolePermissionRel(sysPermissionModel);
		
	}
	
	
}
