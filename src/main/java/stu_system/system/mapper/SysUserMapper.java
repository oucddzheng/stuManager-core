package stu_system.system.mapper;

import java.util.List;

import stu_system.core.mapper.BaseMapper;

public interface SysUserMapper<T> extends BaseMapper<T> {
     public List<T> selectUserAndRole(T t); //user���Role����ж���ѯ
     public List<T> selectUserAndRoleAndClass(T t);
}
