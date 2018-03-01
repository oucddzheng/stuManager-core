package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysUserMapper;

@Service("sysUserService")
public class SysUserService<T> extends BaseService<T> {
//	public class SysUserService<T> extends BaseService<Object>{

	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public BaseMapper getMapper() {
		// TODO Auto-generated method stub
		return sysUserMapper;
	}
	 public List<T> selectUserAndRole(T t) throws Exception {
	    	((BaseModel) t).setPagerOrderField();
	        return ((SysUserMapper) getMapper()).selectUserAndRole(t);
	    }
	 //班主任对学生的请假进行审批时查询本班级中的学生的请假情况
	 public List<T> selectUserAndRoleAndClass(T t) throws Exception {
		    ((BaseModel) t).setPagerPageIdPageSizeAndPagerOrderField();  
	        ((BaseModel) t).getPager().setRowCount(selectCount(t));
	        return ((SysUserMapper) getMapper()).selectUserAndRoleAndClass(t);
	    }
}
