package stu_system.system.model;

import stu_system.core.model.BaseModel;

public class SysCreateQuestionQuestionLibraryRelationModel extends BaseModel {
	/*id, code, create_question_code, question_library_code, 
	create_time, update_time, creator, updater, is_delete, is_effect, orderby, descr*/
    private String CreateQuestionCode;
    private String QuestionLibraryCode;
	public String getCreateQuestionCode() {
		return CreateQuestionCode;
	}
	public void setCreateQuestionCode(String createQuestionCode) {
		CreateQuestionCode = createQuestionCode;
	}
	public String getQuestionLibraryCode() {
		return QuestionLibraryCode;
	}
	public void setQuestionLibraryCode(String questionLibraryCode) {
		QuestionLibraryCode = questionLibraryCode;
	}
  

}
