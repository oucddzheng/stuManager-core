package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysPermissionModel extends BaseModel {
	
	/*id, code, permission_name, url, parent_code, type, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr
*/   
	private String permissionName;
	private String url;
	private String parentCode;
	
	private SysRolePermissionRelationModel sysRolePermissionRelationModel = new SysRolePermissionRelationModel();
	
	
	
	
	
	public SysRolePermissionRelationModel getSysRolePermissionRelationModel() {
		return sysRolePermissionRelationModel;
	}
	public void setSysRolePermissionRelationModel(SysRolePermissionRelationModel sysRolePermissionRelationModel) {
		this.sysRolePermissionRelationModel = sysRolePermissionRelationModel;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	} 
	
	
	
}
