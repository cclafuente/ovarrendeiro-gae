package es.panaderiaovarrendeiro.gae.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Pedido {

	private static final long serialVersionUID = 1L;
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key id;

	@Persistent
	private Long customerId;
	
	@Persistent
	private Date lastUpdate;
	
	@Persistent
	private Integer tipo;
	
	@Persistent 
	private Boolean confirmado;
	
	@Persistent
	private Integer numero;
	
	@Persistent
	private Date fecha;
	
	@Persistent 
	private Float total;
	
	@Persistent
	private String email;
	
	@Persistent
	private Date fechaEntrega;
	
	@Persistent
	private String lastModifiedBy;
	
	@Persistent(mappedBy = "pedido", defaultFetchGroup = "true")
	@Element(dependent = "true")
	List<PedidoLinea> lineasPedido = new ArrayList<PedidoLinea>();
	
	public Long getCustomerId() {
		return customerId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public Integer getTipo() {
		return tipo;
	}

	public Integer getNumero() {
		return numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public Key getId() {
		return id;
	}

	public void setId(Key facturaId) {
		this.id = facturaId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isNew() {
		return getId() == null;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Boolean getConfirmado() {
		return confirmado;
	}

	public void setConfirmado(Boolean confirmado) {
		this.confirmado = confirmado;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public List<PedidoLinea> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(List<PedidoLinea> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String toString(){
		return " clave cliente " + getCustomerId() + " email " + getEmail() + " total " + getTotal() + " " + getNumero() + " key " + getId();
	}
}
