package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysRolePermissionRelationModel extends BaseModel {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	//���ݿ��е��ֶ�
	/*id, code, role_code, menu_code, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
	 
	private String  roleCode;
	private String  menuCode;
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	
	
	

	

}
