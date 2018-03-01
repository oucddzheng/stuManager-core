package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysAasignmentModel extends BaseModel {

	/*在数据库中的字段
	 * id, code, teacher_code, home_work_name, grade_standard, type, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
     private String time;
	 private String teacherCode;
     private String homeworkName;
     private String gradeStandard;
     private String typeName;
     
     //作业的完成情况
     private SysAssignmentConditionModel sysAssignmentConditionModel = new SysAssignmentConditionModel();
     
     
     
     
     
	public SysAssignmentConditionModel getSysAssignmentConditionModel() {
		return sysAssignmentConditionModel;
	}
	public void setSysAssignmentConditionModel(SysAssignmentConditionModel sysAssignmentConditionModel) {
		this.sysAssignmentConditionModel = sysAssignmentConditionModel;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	
	public String getHomeworkName() {
		return homeworkName;
	}
	public void setHomeworkName(String homeworkName) {
		this.homeworkName = homeworkName;
	}
	public String getGradeStandard() {
		return gradeStandard;
	}
	public void setGradeStandard(String gradeStandard) {
		this.gradeStandard = gradeStandard;
	}
 
}
