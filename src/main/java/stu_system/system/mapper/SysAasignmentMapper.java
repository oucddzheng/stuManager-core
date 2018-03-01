package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;

public interface SysAasignmentMapper<T> extends BaseMapper<T> {
	
	List<T> selectAasignmentAndDictionary(T t);

}
