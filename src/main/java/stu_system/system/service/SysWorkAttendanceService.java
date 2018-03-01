package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysWorkAttendanceMapper;
@Service("/sysWorkAttendanceService")
public class SysWorkAttendanceService<T> extends BaseService<T> {

	@Autowired
	private SysWorkAttendanceMapper sysWorkAttendanceMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysWorkAttendanceMapper;
	}
	public List<T> selectAttendenceStatus(T t) throws Exception {
    	((BaseModel) t).setPagerOrderField();
        return ((SysWorkAttendanceMapper)getMapper()).selectAttendenceStatus(t);
    } 
}
