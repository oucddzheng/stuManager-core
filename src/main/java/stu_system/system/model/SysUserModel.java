package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysUserModel extends BaseModel {
   private String userAccount;
   private String userPassword;
   private String userTrueName;
   private String userTelephone;
   private String roleCode;
   private String classCode;
   private SysRoleModel sysRoleModel; 
   private SysClassModel sysClassModel;
   //下面这个属性是用来存该用户对应的答题的
   
   


public SysClassModel getSysClassModel() {
	return sysClassModel;
}
public void setSysClassModel(SysClassModel sysClassModel) {
	this.sysClassModel = sysClassModel;
}
public SysRoleModel getSysRoleModel() {
	return sysRoleModel;
}
public void setSysRoleModel(SysRoleModel sysRoleModel) {
	this.sysRoleModel = sysRoleModel;
}
public String getUserAccount() {
	return userAccount;
}
public void setUserAccount(String userAccount) {
	this.userAccount = userAccount;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public String getUserTrueName() {
	return userTrueName;
}
public void setUserTrueName(String userTrueName) {
	this.userTrueName = userTrueName;
}
public String getUserTelephone() {
	return userTelephone;
}
public void setUserTelephone(String userTelephone) {
	this.userTelephone = userTelephone;
}
public String getRoleCode() {
	return roleCode;
}
public void setRoleCode(String roleCode) {
	this.roleCode = roleCode;
}
public String getClassCode() {
	return classCode;
}
public void setClassCode(String classCode) {
	this.classCode = classCode;
}
   
   
}
