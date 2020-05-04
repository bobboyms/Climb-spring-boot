package br.com.climb.climbspring;

import br.com.climb.core.interfaces.ResultIterator;

public interface ClimbRepository<T, ID> {

    void save(T object);
    void update(T object);
    void delete(T object);
//    void delete(Class object, String where);
    T findOne(ID id);
    ResultIterator find();
//    ResultIterator find(String where);

}
