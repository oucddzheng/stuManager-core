package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;

public interface SysWorkAttendanceMapper<T> extends BaseMapper<T> {
	 List<T> selectAttendenceStatus(T t);
}
