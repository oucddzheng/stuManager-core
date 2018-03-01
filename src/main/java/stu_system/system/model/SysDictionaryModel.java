package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysDictionaryModel extends BaseModel {
	/*id, code, name, parent_code, type, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
	private String name;
	private String parentCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	
	
}
