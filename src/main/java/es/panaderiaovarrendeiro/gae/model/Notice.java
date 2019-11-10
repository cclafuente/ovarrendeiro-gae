package es.panaderiaovarrendeiro.gae.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Notice implements PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	
	@Persistent
	private String title;
	
	@Persistent
	private String body;
	
	@Persistent
	private Date lastUpdate;
	
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Long getId() {
		return id;
	}
	public boolean isNew() {
		return getId() == null;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
