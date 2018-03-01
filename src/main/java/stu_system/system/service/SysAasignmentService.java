package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysAasignmentMapper;
@Service("sysAasignmentService")
public class SysAasignmentService<T> extends BaseService<T> {

	@Autowired
	private SysAasignmentMapper sysAasignmentMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysAasignmentMapper;
	}
	public List<T> selectAasignmentAndDictionary(T t) throws Exception {
    	((BaseModel) t).setPagerOrderField();
        return sysAasignmentMapper.selectAasignmentAndDictionary(t);
    }
}
