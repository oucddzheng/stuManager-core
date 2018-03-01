package stu_system.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;
import stu_system.core.service.BaseService;
import stu_system.system.mapper.SysLeaveMapper;
@Service("sysLeaveService")
public class SysLeaveService<T> extends BaseService<T> {

	@Autowired
	private SysLeaveMapper sysLeaveMapper;
	@Override
	public BaseMapper<T> getMapper() {
		// TODO Auto-generated method stub
		return sysLeaveMapper;
	}
	
	//�ú�����������ͷ�ҳ�Ĺ���
    public List<T> selectLeaveTableAndUserAndDictionary(T t) throws Exception{
    	((BaseModel) t).setPagerPageIdPageSizeAndPagerOrderField();  
        ((BaseModel) t).getPager().setRowCount(selectCount(t));
        return sysLeaveMapper.selectLeaveTableAndUserAndDictionary(t);
    }
  //�����ζ�ѧ������ٽ�������ʱ��ѯ���༶�е�ѧ����������
    public List<T> selectLeaveTableAndUserAndDictionaryByTeacher(T t) throws Exception{
    	((BaseModel) t).setPagerPageIdPageSizeAndPagerOrderField();  
        ((BaseModel) t).getPager().setRowCount(selectCount(t));
        return sysLeaveMapper.selectLeaveTableAndUserAndDictionaryByTeacher(t);
    }
    
    public List<T> selectLeaveTableAndUserAndDictionaryForRecordByTeacher(T t) throws Exception{
    	((BaseModel) t).setPagerPageIdPageSizeAndPagerOrderField();  
        ((BaseModel) t).getPager().setRowCount(selectCount(t));
        return sysLeaveMapper.selectLeaveTableAndUserAndDictionaryForRecordByTeacher(t);
    }
    
}
