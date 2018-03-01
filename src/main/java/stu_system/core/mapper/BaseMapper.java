package stu_system.core.mapper;

import java.util.List;

public interface BaseMapper<T> {

    void insert(T t);

    void delete(Object id);

    void deleteModel(T t);

    void update(T t);

    void updateActive(T t);

    T selectId(Object id);

    int selectCount(T t);

    List<T> selectModel(T t);

    List<T> selectAll(T t);

}
