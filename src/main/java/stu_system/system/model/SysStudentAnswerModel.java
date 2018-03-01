package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysStudentAnswerModel extends BaseModel {
	/*id, code, student_questionnaire_rel_code, question_code, answer_name, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
    private String stuQuestionnaireRelCode;
    private String questionCode;
    private String answerName;
	public String getStuQuestionnaireRelCode() {
		return stuQuestionnaireRelCode;
	}
	public void setStuQuestionnaireRelCode(String stuQuestionnaireRelCode) {
		this.stuQuestionnaireRelCode = stuQuestionnaireRelCode;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
  }
