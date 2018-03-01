package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.system.model.SysCreateQuestionModel;
import stu_system.system.model.SysQuestionLibraryModel;
import stu_system.system.model.SysStudentQuestionnaireRelModel;

public interface SysStudentAnswerMapper<T> extends BaseMapper<T> {
       List<SysQuestionLibraryModel> selectQuestionLibraryAndStudentAnswer(SysStudentQuestionnaireRelModel SysStudentQuestionnaireRelModel);
       List<SysQuestionLibraryModel> selectQuestionAnswerForAnalysis(SysCreateQuestionModel sysCreateQuestionModel);
}
