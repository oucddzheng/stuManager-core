package stu_system.core.service;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;




public abstract class BaseService<T> {

    public abstract BaseMapper<T> getMapper();
    //��һ����������һ����¼
    public void insert(T t) throws Exception {
        getMapper().insert(t);
    }
    //�ڶ�������������idɾ�� ��¼
    public void delete(Integer... ids) throws Exception {
        if (ids == null || ids.length < 1) {
            return;
        }
        for (Object id : ids) {
            getMapper().delete(id);
        }
    }
    //���������� ���ݶ���������ɾ����¼
    public void deleteModel(T t) throws Exception {
        getMapper().deleteModel(t);
    }
    //���ĸ�����  ���ݶ��������¼�¼
    public  void update(T t) throws Exception {
        getMapper().update(t);
    }
    //��������� ���ݶ��������¼�¼
    public void updateActive(T t) throws Exception {
        getMapper().updateActive(t);
    }
    //��������������id��ѯ
    public T selectId(Object id) throws Exception {
        return getMapper().selectId(id);
    }

    public int selectCount(T t) throws Exception {
        return getMapper().selectCount(t);
    }
     
    
    /**
     * @param model
     * @return
     * @throws Exception
     * �÷�������������ҳ��ѯ�õģ���������ͷ�ҳ�Ĺ���
     */
    public List<T> selectModel(T t) throws Exception {
        ((BaseModel) t).setPagerPageIdPageSizeAndPagerOrderField();  
        ((BaseModel) t).getPager().setRowCount(selectCount(t));
        return getMapper().selectModel(t);
    }
    /**
     * @param t
     * @return
     * @throws Exception
     * �÷�������������ѯ���ü�¼�õģ��ò�ѯ������������Ĺ��ܣ�
     */
    public List<T> selectAll(T t) throws Exception {
    	((BaseModel) t).setPagerOrderField();
        return getMapper().selectAll(t);
    }

}
