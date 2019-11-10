package es.panaderiaovarrendeiro.gae.dao;

import java.util.Collection;
import java.util.Date;

import es.panaderiaovarrendeiro.gae.model.Factura;

public interface FacturaDao extends BaseDao<Factura> {

	/**
	 * @param customerId
	 * @param fechaInicio
	 * @param fechaFin
	 * @param soloPendientes muestra las facturas pendientes de pago
	 * @return
	 */
	public Collection<Factura> findPendientesPorCustomerId(Long customerId, Date fechaInicio, Date fechaFin, Boolean soloPendientes);
	
}
