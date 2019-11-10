package es.panaderiaovarrendeiro.gae.model;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Product implements PersistentObject {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
  	
  	@Persistent
	private String name;
    
  	@Persistent
  	private Float price;
  	
  	@Persistent
  	private Long tipo;
  	
  	@Persistent
  	private Long temporada;
  	
  	@Persistent
  	private Integer iva;
  	
  	@Persistent
  	private Date lastUpdate;
  	
  	@Persistent
  	private String comentario;
  	
  	@Persistent
  	private Boolean reservable;
  	
  	
  	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getPrice() {
		return price;
	}

	public Long getTipo() {
		return tipo;
	}

	public Long getTemporada() {
		return temporada;
	}

	public Integer getIva() {
		return iva;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public String getComentario() {
		return comentario;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public void setTemporada(Long temporada) {
		this.temporada = temporada;
	}

	public void setIva(Integer iva) {
		this.iva = iva;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Boolean getReservable() {
		return reservable;
	}

	public void setReservable(Boolean reservable) {
		this.reservable = reservable;
	}

	public boolean isNew() {
		return (getId() == null);
	}
}
