package es.panaderiaovarrendeiro.gae.service.facturas;

import java.util.Collection;
import java.util.Date;

import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.service.BaseManager;

public interface FacturaManager extends BaseManager<Factura> {

	/**
	 * @param customerId (si es null, no se filtra)
	 * @param fechaInicio (si es null, no se filtra)
	 * @param fechaFin (si es null, no se filtra)
	 * @param pendientesPago (si es true, busca solo las que no estan pagadas)
	 * @return
	 */
	public Collection<Factura> findPendientesPorCustomerId(Long customerId, Date fechaInicio, Date fechaFin, Boolean pendientesPago);

	/**
	 * @param idFactura
	 * @return factura por codigo 
	 */
	public Factura findByLongId(Long idFactura);
}
