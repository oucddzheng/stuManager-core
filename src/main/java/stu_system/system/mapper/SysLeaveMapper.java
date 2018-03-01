package stu_system.system.mapper;


import java.util.List;

import stu_system.core.mapper.BaseMapper;

public interface SysLeaveMapper<T> extends BaseMapper<T> {	
	public List<T> selectLeaveTableAndUserAndDictionary(T t);
	public List<T>selectLeaveTableAndUserAndDictionaryByTeacher(T t);
	public List<T>selectLeaveTableAndUserAndDictionaryForRecordByTeacher(T t);
    
}
