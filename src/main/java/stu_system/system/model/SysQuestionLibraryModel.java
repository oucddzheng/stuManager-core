package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysQuestionLibraryModel extends BaseModel {
	/*id, code, user_code, question_title, question_option, question_answer, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
	private String userCode;
	private String questionTitle;
	private String questionOption;
	private String questionAnswer;
	private String answerName;
	
	public String getAnswerName() {
		return answerName;
	}
	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}
	//学生回答问题的答案
	private SysStudentAnswerModel SysStudentAnswerModel = new SysStudentAnswerModel();
	
	private Integer number;  //该属性是用来存放选某个答案的个数
	
	
	
	
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public SysStudentAnswerModel getSysStudentAnswerModel() {
		return SysStudentAnswerModel;
	}
	public void setSysStudentAnswerModel(SysStudentAnswerModel sysStudentAnswerModel) {
		SysStudentAnswerModel = sysStudentAnswerModel;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getQuestionOption() {
		return questionOption;
	}
	public void setQuestionOption(String questionOption) {
		this.questionOption = questionOption;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	
	
}
