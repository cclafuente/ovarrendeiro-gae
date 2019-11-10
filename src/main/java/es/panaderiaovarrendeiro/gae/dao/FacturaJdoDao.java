package es.panaderiaovarrendeiro.gae.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.panaderiaovarrendeiro.gae.model.Factura;

public class FacturaJdoDao extends BaseJdoDao<Factura> implements FacturaDao {

	protected final Log log = LogFactory.getLog(getClass());
	
	public FacturaJdoDao(Class<Factura> persistentClass) {
		super(persistentClass);
	}

	/*
	@SuppressWarnings("unchecked")
	public Collection<Factura> findPendientesPorCustomerId(Long customerId,
			Date fechaInicio, Date fechaFin, Boolean soloPendientes) {
		Collection<Factura> facturas = new ArrayList<Factura>();
		PersistenceManager pm = getPersistenceManager();
	    try {
	      Query query = pm.newQuery(Factura.class);
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
	    	  query.setFilter("fecha == f_inicio");
	      }else if (fechaInicio != null && fechaFin != null){
	    	  query.declareParameters("Date f_inicio");
	    	  query.declareParameters("Date f_fin");
	    	  log.info("filtrando por f_inicio " + fechaInicio + " f_fin " + fechaFin);
	    	  map.put("f_inicio", fechaInicio);
	    	  map.put("f_fin", fechaFin);
	    	  query.setFilter(" (fecha >= f_inicio) && (fecha <= f_fin) ");
	    	  //query.setFilter("fecha >= f_inicio");
	    	  //query.setFilter("fecha <= f_fin");
	      }else if (fechaInicio != null){
	    	  query.declareParameters("Date f_inicio");
	    	  log.info("filtrando por f_inicio " + fechaInicio);
	    	  map.put("f_inicio", fechaInicio);
	    	  query.setFilter("fecha >= f_inicio");
	      }else if (fechaFin != null){
	    	  query.declareParameters("Date f_fin");
	    	  log.info("filtrando por f_fin " + fechaFin);
	    	  map.put("f_fin", fechaFin);
	    	  query.setFilter("fecha <= f_fin");
	      }
	      if (soloPendientes){
	    	  query.declareParameters("Boolean f_pagadofalse");
	    	  log.info("filtrando por f_pagadofalse ");
	    	  map.put("f_pagadofalse", false);
	    	  query.setFilter("pagado == false");
	      }
	      facturas = pm.detachCopyAll((Collection<Factura>)query.executeWithMap(map));
	      return facturas;
	    }
	    finally {
	      releasePersistenceManager(pm);
	    }
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Factura> findPendientesPorCustomerId(Long customerId,
			Date fechaInicio, Date fechaFin, Boolean soloPendientes) {
		Collection<Factura> facturas = new ArrayList<Factura>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		  Query query = new Query("Factura");
	      
	      Map<String, Object> map = new HashMap<String, Object>();
	      log.info("filtrando por f_inicio " + OVarrendeiroConstants.dateFormatGQL.format(fechaInicio) + " f_fin " + OVarrendeiroConstants.dateFormatGQL.format(fechaFin));
	      map.put("f_inicio", fechaInicio);
	      map.put("f_fin", fechaFin);
	      query.setFilter(CompositeFilterOperator.and(
		    	       new FilterPredicate("fecha", Query.FilterOperator.GREATER_THAN, fechaInicio),
		    	       new FilterPredicate("date", Query.FilterOperator.LESS_THAN, fechaFin)));
	    	  
	    	  facturas = (Collection<Factura>)datastore.prepare(query).asList(
	    			    FetchOptions.Builder.withDefaults());
	    	  
	    	  return facturas;
	}*/

	
	@SuppressWarnings("unchecked")
	public Collection<Factura> findPendientesPorCustomerId(Long customerId,
			Date fechaInicio, Date fechaFin, Boolean soloPendientes) {
		
		List<Factura> facturaList = new ArrayList<Factura>();
		String queryStr = "select from " + Factura.class.getName();
		
		if (fechaInicio != null && fechaFin != null && customerId == null && soloPendientes == false){
			queryStr = queryStr + " where fecha >= :p1 && fecha <= :p2 ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(fechaInicio, fechaFin);
		}else if (fechaInicio != null && fechaFin != null && customerId == null && soloPendientes == true){
			queryStr = queryStr + " where fecha >= :p1 && fecha <= :p2 && pagado == false ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(fechaInicio, fechaFin);
		}else if (fechaInicio != null && fechaFin != null && customerId != null && soloPendientes == false){
			queryStr = queryStr + " where fecha >= :p1 && fecha <= :p2 && customerId == :customerId ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(fechaInicio, fechaFin, customerId);
		}else if (fechaInicio != null && fechaFin != null && customerId != null && soloPendientes == true){
			queryStr = queryStr + " where fecha >= :p1 && fecha <= :p2 && customerId == :customerId && pagado == false ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(fechaInicio, fechaFin, customerId);
		}else if (fechaInicio == null && fechaFin == null && customerId == null && soloPendientes == true){
			queryStr = queryStr + " where pagado == false ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute();
		}else if (fechaInicio == null && fechaFin == null && customerId != null && soloPendientes == true){
			queryStr = queryStr + " where customerId == :customerId && pagado == false ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(customerId);
		}else if (fechaInicio == null && fechaFin == null && customerId != null && soloPendientes == false){
			queryStr = queryStr + " where customerId == :customerId ";
			facturaList = (List<Factura>)getPersistenceManager().newQuery(queryStr).execute(customerId);
		}

		log.warn(" consulta lanzada " + queryStr);
		log.warn(" lista de facturas " + facturaList.size());
		
		return facturaList;
	}

	
	/*
	@SuppressWarnings("unchecked")
	public Collection<Factura> findPendientesPorCustomerId(Long customerId,
			Date fechaInicio, Date fechaFin, Boolean soloPendientes) {
		Collection<Factura> facturas = new ArrayList<Factura>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		  Query query = new Query("Factura");
	      
	      Map<String, Object> map = new HashMap<String, Object>();
	      log.info("filtrando por f_inicio " + OVarrendeiroConstants.dateFormatGQL.format(fechaInicio) + " f_fin " + OVarrendeiroConstants.dateFormatGQL.format(fechaFin));
	      map.put("f_inicio", fechaInicio);
	      map.put("f_fin", fechaFin);
	      query.setFilter(CompositeFilterOperator.and(
		    	       new FilterPredicate("fecha", Query.FilterOperator.GREATER_THAN, fechaInicio),
		    	       new FilterPredicate("date", Query.FilterOperator.LESS_THAN, fechaFin)));
	    	  
	    	  facturas = (Collection<Factura>)datastore.prepare(query).asList(
	    			    FetchOptions.Builder.withDefaults());
	    	  
	    	  return facturas;
	}*/

	

}
