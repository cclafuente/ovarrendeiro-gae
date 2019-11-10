package es.panaderiaovarrendeiro.gae.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class FacturaLinea {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	
	@Persistent
	private Integer numeroLinea;
	
	@Persistent
	private Long productId;
	
	@Persistent
	private Float precio;
	
	@Persistent
	private Integer iva;
	
	@Persistent
	private Float cantidad;
	
	@Persistent
	private Float total;
	
	@Persistent
	private Factura factura;
	
	public Key getId() {
		return id;
	}
	public Integer getNumeroLinea() {
		return numeroLinea;
	}
	public Float getPrecio() {
		return precio;
	}
	public Integer getIva() {
		return iva;
	}
	public Float getCantidad() {
		return cantidad;
	}
	public Float getTotal() {
		return total;
	}
	public void setId(Key id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public Factura getFactura() {
		return factura;
	}
	public void setNumeroLinea(Integer numeroLinea) {
		this.numeroLinea = numeroLinea;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public void setIva(Integer iva) {
		this.iva = iva;
	}
	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	public String toString(){
		String retString = "[precio = " + getPrecio() + " productId=" + getProductId() + " key= " + getId() + " cantidad=" + getCantidad();
		return retString;
	}
	
}
