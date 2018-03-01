package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysCreateQuestionModel extends BaseModel {
	
	/*id, code, question_name, user_code, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr
*/
	private String QuestionName ;
	private String UserCode; //��������������洢�������ʾ���ʦ��code
	private SysQuestionnaireForStuModel  sysQuestionnaireForStuModel = new SysQuestionnaireForStuModel();  //�����������������ѯʱ����ѧ�����д���ʱ�õġ�
	
	
	
	
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
