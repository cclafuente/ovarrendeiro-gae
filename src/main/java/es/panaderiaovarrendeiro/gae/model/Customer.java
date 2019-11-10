package es.panaderiaovarrendeiro.gae.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Customer implements PersistentObject {

	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

	@Persistent
	private String nombre;
	
	@Persistent
	private String identificador;
	
	@Persistent
	private String direccion;
	
	@Persistent
	private String email;
	
	@Persistent
	private Date lastUpdate;
	
	@Persistent
	private String internalPwd;
	
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getEmail() {
		return email;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public String getInternalPwd() {
		return internalPwd;
	}

	public void setInternalPwd(String internalPwd) {
		this.internalPwd = internalPwd;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isNew() {
		return getId() == null;
	}
	
	public String toString(){
		return " id: " + id + " nombre: " + nombre + " identificador: " + identificador + " direccion: " + direccion;
	}

}
