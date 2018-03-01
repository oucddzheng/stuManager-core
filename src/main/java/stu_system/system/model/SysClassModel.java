package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysClassModel extends BaseModel {
    
	private String className;
	private String teacherCode;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	
}
