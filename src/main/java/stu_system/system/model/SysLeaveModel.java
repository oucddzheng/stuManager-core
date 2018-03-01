package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysLeaveModel extends BaseModel {
	/*id, code, user_code, why, begin_time, end_time, status, create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
	private String userCode;
	private String why;
	private String beginTime;
	private String endTime;
	private Integer status;
	private String statusName;
	private SysUserModel sysUserModel;
	
	
	
	public SysUserModel getSysUserModel() {
		return sysUserModel;
	}
	public void setSysUserModel(SysUserModel sysUserModel) {
		this.sysUserModel = sysUserModel;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
