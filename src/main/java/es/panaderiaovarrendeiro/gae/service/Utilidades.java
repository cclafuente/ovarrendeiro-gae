package es.panaderiaovarrendeiro.gae.service;

import es.panaderiaovarrendeiro.gae.model.FacturaLinea;
import es.panaderiaovarrendeiro.gae.model.PedidoLinea;
import es.panaderiaovarrendeiro.gae.model.Product;

public class Utilidades {

	/**
	 * @param fl
	 * @param product2
	 * @return
	 */
	public static Float calcularTotalLinea(FacturaLinea fl){
		return ((fl.getPrecio() * fl.getCantidad()) + (fl.getPrecio() * fl.getCantidad() * (fl.getIva() * 0.01f )));
	}
	
	/**
	 * @param fl
	 * @param product2
	 * @return
	 */
	public static Float calcularTotalLinea(PedidoLinea pl){
		return ((pl.getPrecio() * pl.getCantidad()) + (pl.getPrecio() * pl.getCantidad() * (pl.getIva() * 0.01f )));
	}
	
	/**
	 * @param p
	 * @param precioTotal
	 * @return segun el producto y el precio total, calculamos la cantidad para indicar en la linea de factura
	 */
	public static Float calcularCantidadSegunPrecioTotal(Product p, Float precioTotal){
		Float cantidadCalculada = 0f;
		cantidadCalculada = precioTotal / (p.getPrice() + p.getPrice() * 0.01f * p.getIva());
		return cantidadCalculada;
	}
}
