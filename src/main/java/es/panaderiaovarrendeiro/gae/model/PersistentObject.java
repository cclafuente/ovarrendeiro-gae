package es.panaderiaovarrendeiro.gae.model;

import java.io.Serializable;

public interface PersistentObject extends Serializable {

	public boolean isNew();
	
	public Long getId();
	
}
