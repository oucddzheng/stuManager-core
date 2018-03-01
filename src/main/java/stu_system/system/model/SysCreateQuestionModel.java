package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysCreateQuestionModel extends BaseModel {
	
	/*id, code, question_name, user_code, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr
*/
	private String QuestionName ;
	private String UserCode; //这个属性是用来存储创建该问卷老师的code
	private SysQuestionnaireForStuModel  sysQuestionnaireForStuModel = new SysQuestionnaireForStuModel();  //这个属性是用来多表查询时，当学生进行答题时用的。
	
	
	
	
	public SysQuestionnaireForStuModel getSysQuestionnaireForStuModel() {
		return sysQuestionnaireForStuModel;
	}
	public void setSysQuestionnaireForStuModel(SysQuestionnaireForStuModel sysQuestionnaireForStuModel) {
		this.sysQuestionnaireForStuModel = sysQuestionnaireForStuModel;
	}
	public String getQuestionName() {
		return QuestionName;
	}
	public void setQuestionName(String questionName) {
		QuestionName = questionName;
	}
	public String getUserCode() {
		return UserCode;
	}
	public void setUserCode(String userCode) {
		UserCode = userCode;
	}
	
	
}
