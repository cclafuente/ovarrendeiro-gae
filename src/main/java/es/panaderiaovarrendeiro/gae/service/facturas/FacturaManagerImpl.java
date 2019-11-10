package es.panaderiaovarrendeiro.gae.service.facturas;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import es.panaderiaovarrendeiro.gae.dao.BaseDao;
import es.panaderiaovarrendeiro.gae.dao.FacturaDao;
import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.model.FacturaLinea;

public class FacturaManagerImpl implements FacturaManager{

	private static final Log log = LogFactory.getLog(FacturaManagerImpl.class);
	
	private FacturaDao facturaDao;
	private BaseDao<FacturaLinea> facturaLineaDao;
	
	public FacturaDao getFacturaDao() {
		return facturaDao;
	}

	public void setFacturaDao(FacturaDao facturaDao) {
		this.facturaDao = facturaDao;
	}
	
	public BaseDao<FacturaLinea> getFacturaLineaDao() {
		return facturaLineaDao;
	}

	public void setFacturaLineaDao(BaseDao<FacturaLinea> facturaLineaDao) {
		this.facturaLineaDao = facturaLineaDao;
	}

	public Collection<Factura> findAll() {
		return facturaDao.getAll();
	}

	public Factura findById(Serializable id) {
		Factura recuperada = facturaDao.get(id);
		log.debug(" recuperando " + recuperada.getLineasFactura().size() + " lineas ");
		return recuperada;
	}
	
	public Factura findByLongId(Long idFactura){
		Key key = KeyFactory.createKey(Factura.class.getSimpleName(), idFactura);
		Factura recuperada = facturaDao.get(key);
		log.debug(" recuperando " + recuperada.getLineasFactura().size() + " lineas ");
		return recuperada;	
	}

	public void removeById(Serializable id) {
		Factura recuperada = facturaDao.get(id);
		log.debug(" recuperando " + recuperada.getLineasFactura().size() + " lineas ");
		for(FacturaLinea linea : recuperada.getLineasFactura()){
			log.debug(" eliminamos linea " + linea.getId());
			facturaLineaDao.remove(linea.getId());
		}
		log.debug(" eliminamos factura " + id);
		facturaDao.remove(id);
	}

	public Serializable save(Factura persistentObject) {
		facturaDao.save(persistentObject);
		log.debug(" guardando " + persistentObject.getLineasFactura().size() + " lineas factura ");
		return persistentObject.getId();
	}

	public Collection<Factura> findPendientesPorCustomerId(Long customerId, Date fechaInicio, Date fechaFin, Boolean soloPendientes) {
		Collection<Factura> facturasCliente = facturaDao.findPendientesPorCustomerId(customerId, fechaInicio, fechaFin, soloPendientes);
		return facturasCliente;
	}

}
