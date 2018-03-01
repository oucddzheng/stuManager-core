package stu_system.core.service;

import java.util.List;

import stu_system.core.mapper.BaseMapper;
import stu_system.core.model.BaseModel;




public abstract class BaseService<T> {

    public abstract BaseMapper<T> getMapper();
    //第一个方法插入一条记录
    public void insert(T t) throws Exception {
        getMapper().insert(t);
    }
    //第二个方法，根据id删除 记录
    public void delete(Integer... ids) throws Exception {
        if (ids == null || ids.length < 1) {
            return;
        }
        for (Object id : ids) {
            getMapper().delete(id);
        }
    }
    //第三个方法 根据对象（条件）删除记录
    public void deleteModel(T t) throws Exception {
        getMapper().deleteModel(t);
    }
    //第四个方法  根据对象来更新记录
    public  void update(T t) throws Exception {
        getMapper().update(t);
    }
    //第五个方法 根据对象来更新记录
    public void updateActive(T t) throws Exception {
        getMapper().updateActive(t);
    }
    //第六个方法根据id查询
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
     * 该方法是用来做分页查询用的，具有排序和分页的功能
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
     * 该方法是用来做查询所用记录用的，该查询方法具有排序的功能，
     */
    public List<T> selectAll(T t) throws Exception {
    	((BaseModel) t).setPagerOrderField();
        return getMapper().selectAll(t);
    }

}
