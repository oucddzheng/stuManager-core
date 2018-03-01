package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysAssignmentConditionModel extends BaseModel {
	
	
/*	id, code, student_code, assignment_code, oneself_score, oneself_grade, teacher_score, teacher_grade, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr
*/
	private String studentCode;
	private String assignmentCode;
	private Double oneselfScore;
	private Integer oneselfGrade;
	private String oneselfGradeName;	
	private Double teacherScore;
	private Integer teacherGrade;
	private String teacherGradeName;
	
	SysUserModel sysUserModel = new SysUserModel();
	
	
	public SysUserModel getSysUserModel() {
		return sysUserModel;
	}
	public void setSysUserModel(SysUserModel sysUserModel) {
		this.sysUserModel = sysUserModel;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getAssignmentCode() {
		return assignmentCode;
	}
	public void setAssignmentCode(String assignmentCode) {
		this.assignmentCode = assignmentCode;
	}
	public Double getOneselfScore() {
		return oneselfScore;
	}
	public void setOneselfScore(Double oneselfScore) {
		this.oneselfScore = oneselfScore;
	}
	public Integer getOneselfGrade() {
		return oneselfGrade;
	}
	public void setOneselfGrade(Integer oneselfGrade) {
		this.oneselfGrade = oneselfGrade;
	}
	public String getOneselfGradeName() {
		return oneselfGradeName;
	}
	public void setOneselfGradeName(String oneselfGradeName) {
		this.oneselfGradeName = oneselfGradeName;
	}
	public Double getTeacherScore() {
		return teacherScore;
	}
	public void setTeacherScore(Double teacherScore) {
		this.teacherScore = teacherScore;
	}
	public Integer getTeacherGrade() {
		return teacherGrade;
	}
	public void setTeacherGrade(Integer teacherGrade) {
		this.teacherGrade = teacherGrade;
	}
	public String getTeacherGradeName() {
		return teacherGradeName;
	}
	public void setTeacherGradeName(String teacherGradeName) {
		this.teacherGradeName = teacherGradeName;
	}
	
	
	
	
}
