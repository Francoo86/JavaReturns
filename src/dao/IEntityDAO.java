package dao;

import java.util.Collection;

public interface IEntityDAO <T, U> {
    void add(T entity);
    void update(T entity);
    T findByWordName(String wordName);
    void delete(T entityId);
    Collection<T> findAll();
    T findUnique(U criteria);
}
