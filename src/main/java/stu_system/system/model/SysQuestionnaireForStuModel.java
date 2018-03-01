package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysQuestionnaireForStuModel extends BaseModel {

	/*di, code, user_code, create_question_code, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
    private String userCode;
    private String createQuestionCode;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getCreateQuestionCode() {
		return createQuestionCode;
	}
	public void setCreateQuestionCode(String createQuestionCode) {
		this.createQuestionCode = createQuestionCode;
	}
}
