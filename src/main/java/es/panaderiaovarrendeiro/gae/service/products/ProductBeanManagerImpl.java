package es.panaderiaovarrendeiro.gae.service.products;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;
import es.panaderiaovarrendeiro.gae.web.beans.LabelValueBean;
import es.panaderiaovarrendeiro.gae.web.beans.ProductBean;

public class ProductBeanManagerImpl implements ProductBeanManager {

	private static final Log log = LogFactory.getLog(ProductBeanManagerImpl.class);
	
	private ProductManager productManager;
	private ImageManager imageManager;
	private Map<Long, String> mapaTemporadas = new HashMap<Long, String>();
	private Map<Long, String> mapaTipos = new HashMap<Long, String>();
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public ProductBean findProductBeanById(Long productId) {
		Product p = productManager.findById(productId);
		ProductBean pBean = fillBean(p);
		return pBean;
	}

	public Collection<ProductBean> findAll() {
		Collection<Product> allProducts = productManager.getAllProducts(1);
		return fillBeans(allProducts);
	}
	
	public Map<Long, Product> getMapaProductos(){
		Collection<Product> allProducts = productManager.getAllProducts(1);
		Map<Long, Product> mapaProds = new HashMap<Long, Product>();
		for(Product p : allProducts){
			mapaProds.put(p.getId(), p);
		}
		return mapaProds;
	}

	public Collection<ProductBean> findLastUpdated(int number) {
		Collection<Product> productList = productManager.getLastUpdated(3);
		return fillBeans(productList);
	}

	public void inicializarMapaTemporadas(Collection<LabelValueBean> temporadas) {
		for(LabelValueBean temporada : temporadas){
			mapaTemporadas.put(temporada.getValue(), temporada.getLabel());
		}
	}

	public void inicializarMapaTipos(Collection<LabelValueBean> tipos) {
		for(LabelValueBean tipo : tipos){
			mapaTipos.put(tipo.getValue(), tipo.getLabel());
		}
	}

	public Map<Long, String> getMapaTemporadas() {
		return mapaTemporadas;
	}

	public Map<Long, String> getMapaTipos() {
		return mapaTipos;
	}

	public void setMapaTemporadas(Map<Long, String> mapaTemporadas) {
		this.mapaTemporadas = mapaTemporadas;
	}

	public void setMapaTipos(Map<Long, String> mapaTipos) {
		this.mapaTipos = mapaTipos;
	}
	
	public Collection<ProductBean> findByMap(Map<String, ?> conds) {
		Collection<Product> productList = productManager.findByMap(conds);
		return fillBeans(productList);
	}

	private Collection<ProductBean> fillBeans(Collection<Product> productList){
		Collection<ProductBean> beans = new ArrayList<ProductBean>();
		for(Product product : productList){
			ProductBean pBean = fillBean(product);
			beans.add(pBean);
		}
		return beans;
	}
	
	private ProductBean fillBean(Product product){
		ProductBean pBean = new ProductBean();
		pBean.setProduct(product);
		pBean.setTemporada(mapaTemporadas.get(product.getTemporada()));
		
		pBean.setTipo(mapaTipos.get(product.getTipo()));
		log.debug(" temporada " + pBean.getTemporada() + " tipo " + pBean.getTipo());
		pBean.setImageList(imageManager.findByProductId(product.getId()));
		return pBean;
	}
}
