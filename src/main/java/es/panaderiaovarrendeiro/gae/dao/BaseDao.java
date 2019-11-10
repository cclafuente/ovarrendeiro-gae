package es.panaderiaovarrendeiro.gae.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface BaseDao <T> {

    Collection<T> getAll();

    T get(Serializable id);

    void save(T object);

    void remove(Serializable id);
    
    public Collection<T> getObjectsClauseEqual(Map<String, ?> conds);
    
    Collection<T> getLastUpdated(int number);
}
