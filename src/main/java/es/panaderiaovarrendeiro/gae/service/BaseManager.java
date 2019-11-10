package es.panaderiaovarrendeiro.gae.service;

import java.io.Serializable;
import java.util.Collection;

public interface BaseManager<T> {

	public Serializable save(T persistentObject);
	
	public void removeById(Serializable id);

	public Collection<T> findAll();
	
	public T findById(Serializable id);
}
