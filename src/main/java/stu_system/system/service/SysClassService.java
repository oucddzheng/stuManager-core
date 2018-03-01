package stu_system.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysClassMapper;
@Service("sysClassService")
public class SysClassService<T> extends BaseService<T> {

	@Autowired
	private SysClassMapper sysClassMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysClassMapper;
	}

}
