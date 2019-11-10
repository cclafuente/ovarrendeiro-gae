package es.panaderiaovarrendeiro.gae.service.products;

import java.util.Collection;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.web.beans.LabelValueBean;
import es.panaderiaovarrendeiro.gae.web.beans.ProductBean;

public interface ProductBeanManager {

	public ProductBean findProductBeanById(Long productId);
	
	public Collection<ProductBean> findLastUpdated(int number);
	
	public Collection<ProductBean> findAll();
	
	public void inicializarMapaTemporadas(Collection<LabelValueBean> temporadas);
	
	public void inicializarMapaTipos(Collection<LabelValueBean> tipos);
	
	public Map<Long, String> getMapaTemporadas();
	
	public Map<Long, String> getMapaTipos();
	
	public Map<Long, Product> getMapaProductos();
	
	public Collection<ProductBean> findByMap(Map<String, ?> conds);
}
