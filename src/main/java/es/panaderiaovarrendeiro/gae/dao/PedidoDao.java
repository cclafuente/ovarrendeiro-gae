package es.panaderiaovarrendeiro.gae.dao;

import java.util.Collection;
import java.util.Date;

import es.panaderiaovarrendeiro.gae.model.Pedido;

public interface PedidoDao extends BaseDao<Pedido> {

	/**
	 * @param customerId (si es null, no se filtra)
	 * @param email (si es null, no se filtra)
	 * @param fechaInicio (si es null, no se filtra)
	 * @param fechaFin (si es null, no se filtra)
	 * @return lista de pedidos por los filtros determinados
	 */
	public Collection<Pedido> findByFilter(Long customerId, String email, Date fechaInicio, Date fechaFin);
}
