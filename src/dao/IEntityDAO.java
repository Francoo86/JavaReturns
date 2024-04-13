package dao;

import java.util.Collection;

public interface IEntityDAO <T> {
    void add(T entity);
    Collection<T> getList();
    void update(T entity);
    T findById(T entityId);
    void delete(T entityId);
}
