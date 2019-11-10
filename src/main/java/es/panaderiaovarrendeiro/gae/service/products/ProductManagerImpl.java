package es.panaderiaovarrendeiro.gae.service.products;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;

import es.panaderiaovarrendeiro.gae.dao.ProductDao;
import es.panaderiaovarrendeiro.gae.model.Product;


public class ProductManagerImpl implements ProductManager{

	private static final Log log = LogFactory.getLog(ProductManagerImpl.class);
	
	private static Cache productsCache;
	
	static{ 
		try {
			 Map props = new HashMap();
		     props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		     CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
		     productsCache = cacheFactory.createCache(props);
	     } catch (CacheException e) {
	         log.error(" error inicializando cache " , e);
	     }catch (Exception ex){
	    	 log.error(" error general incializando clase ", ex);
	     }
    }
	
	private ProductDao productDao;
	
	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public Long create(Product product) {
		product.setLastUpdate(new Date());
		Long createdP = productDao.create(product);
		resetCache();
		return createdP;
	}

	public Product findById(Long productId) {
		return productDao.findById(productId);
	}

	public Collection<Product> getAllProducts(int page) {
		Collection<Product> allProducts = null;
		if (productsCache.containsKey("allProducts")){
			allProducts = (Collection<Product>)productsCache.get("allProducts");
		}else{
			allProducts = productDao.getAllProducts(page);
			productsCache.put("allProducts", allProducts);
		}
		log.debug(" ejecutado metodo con " + allProducts.size() + " resultados ");
		return allProducts;
	}
	
	public Collection<Product> findByMap(Map<String, ?> conds) {
		return productDao.getObjectsClauseEqual(conds);
	}

	public void remove(Product product) {
		log.info(" eliminando ... " + product.toString());
		productDao.remove(product);
		resetCache();
	}

	public Collection<Product> getLastUpdated(int number) {
		Collection<Product> lastProducts = null;
		if (productsCache.containsKey("lastProducts")){
			lastProducts = (Collection<Product>)productsCache.get("lastProducts");
		}else{
			lastProducts = productDao.getLastUpdated(number);
		}
		log.debug(" ejecutado metodo con " + lastProducts.size() + " resultados ");
		return lastProducts;
	}
	
	private void resetCache(){
		productsCache.clear();
	}
}
