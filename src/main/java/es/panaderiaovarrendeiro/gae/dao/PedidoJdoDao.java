package es.panaderiaovarrendeiro.gae.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import es.panaderiaovarrendeiro.gae.model.Pedido;

public class PedidoJdoDao extends BaseJdoDao<Pedido> implements PedidoDao{

	public PedidoJdoDao(Class<Pedido> persistentClass) {
		super(persistentClass);
	}
	/* (non-Javadoc)
	 * @see es.panaderiaovarrendeiro.gae.dao.PedidoDao#findByFilter(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public Collection<Pedido> findByFilter(Long customerId, String email,
			Date fechaInicio, Date fechaFin) {
		Collection<Pedido> pedidos = new ArrayList<Pedido>();
		PersistenceManager pm = getPersistenceManager();
	    try {
	      Query query = pm.newQuery(Pedido.class);
	      query.declareImports("import java.util.Date");
	      Map<String, Object> map = new HashMap<String, Object>();
	      if (customerId != null){
	    	  query.declareParameters("Long c_id");
	    	  map.put("c_id", customerId);
	    	  query.setFilter("customerId == c_id");
	    	  log.info("filtrando por c_id " + customerId);
	      }
	      if (fechaInicio != null && fechaFin != null && fechaInicio.getTime() == fechaFin.getTime()){
	    	  query.declareParameters("Date f_inicio");
	    	  map.put("f_inicio", fechaInicio);
	    	  query.setFilter("fechaEntrega == f_inicio");
	      }else if (fechaInicio != null && fechaFin != null){
	    	  query.declareParameters("Date f_inicio");
	    	  query.declareParameters("Date f_fin");
	    	  log.info("filtrando por f_inicio " + fechaInicio + " f_fin " + fechaFin);
	    	  map.put("f_inicio", fechaInicio);
	    	  map.put("f_fin", fechaFin);
//	    	  query.setFilter("fechaEntrega <= f_fin && fechaEntrega >= f_inicio");
	    	  query.setFilter("fechaEntrega >= f_inicio");
	    	  query.setFilter("fechaEntrega <= f_fin");
	      }else if (fechaInicio != null){
	    	  query.declareParameters("Date f_inicio");
	    	  log.info("filtrando por f_inicio " + fechaInicio);
	    	  map.put("f_inicio", fechaInicio);
	    	  query.setFilter("fechaEntrega >= f_inicio");
	      }else if (fechaFin != null){
	    	  query.declareParameters("Date f_fin");
	    	  log.info("filtrando por f_fin " + fechaFin);
	    	  map.put("f_fin", fechaFin);
	    	  query.setFilter("fechaEntrega <= f_fin");
	      }
	      if (email != null){
	    	  query.declareParameters("String c_email");
	    	  map.put("c_email", email);
	    	  query.setFilter("email == c_email");
	    	  log.info("filtrando por c_email " + email);
	      }
	      pedidos = pm.detachCopyAll((Collection<Pedido>)query.executeWithMap(map));
	      return pedidos;
	    }
	    finally {
	      releasePersistenceManager(pm);
	    }
	}

	
}
