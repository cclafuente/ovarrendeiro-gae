package es.panaderiaovarrendeiro.gae.web.facturas;

import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.model.Factura;

public class FacturaListBean{
	
	private Factura factura;
	private Customer customer;
	
	public Factura getFactura() {
		return factura;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
