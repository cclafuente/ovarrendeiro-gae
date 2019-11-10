package es.panaderiaovarrendeiro.gae.service.images;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;

import es.panaderiaovarrendeiro.gae.dao.ImageDao;
import es.panaderiaovarrendeiro.gae.model.ImageItem;


public class ImageManagerImpl implements ImageManager{

	private static final Log log = LogFactory.getLog(ImageManagerImpl.class);
	
	private static Cache imagesCache;
	private static Cache productImagesCache;
	
	static{ 
		try {
			 Map props = new HashMap();
		     props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		     CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	         imagesCache = cacheFactory.createCache(props);
	         productImagesCache = cacheFactory.createCache(props);
	     } catch (CacheException e) {
	         log.error(" error inicializando cache " , e);
	     }catch (Exception ex){
	    	 log.error(" error general incializando clase ", ex);
	     }
    }
	
	
	private ImageDao imageDao;
	
	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public ImageItem findById(Long imageId) {
		ImageItem image = null;
		if (imagesCache.containsKey(imageId)){
			image = (ImageItem)imagesCache.get(imageId);
		}else{
			image = imageDao.findById(imageId);
			imagesCache.put(imageId, image);
		}
		return image;
	}

	public Collection<ImageItem> findByProductId(Long productId) {
		Collection<ImageItem> collection = null;
		if (productImagesCache.containsKey(productId)){
			collection = (Collection<ImageItem>)productImagesCache.get(productId);
			if (collection.isEmpty()){
				collection = imageDao.findByProductId(productId);
				productImagesCache.put(productId, collection);
			}
		}else{
			collection = imageDao.findByProductId(productId);
			productImagesCache.put(productId, collection);
		}
		return collection;
	}

	public Long saveImage(ImageItem image) {
		Long returnedImage = imageDao.saveImage(image);
		resetCache();
		return returnedImage;
	}

	public void remove(Long imageId) {
		imageDao.remove(findById(imageId));
		resetCache();
	}

	public void removeByProductId(Long productId) {
		Collection<ImageItem> removeCollection = findByProductId(productId);
		for(ImageItem imageToRemove : removeCollection){
			imageDao.remove(imageToRemove);
		}
		resetCache();
	}
	
	@SuppressWarnings("unchecked")
	public ImageItem findFirstImageProduct(Long productId){
		ImageItem imgProduct = null;
		if (productImagesCache.containsKey(productId)){
			imgProduct = ((Collection<ImageItem>)productImagesCache.get(productId)).iterator().next();
		}else{
			Collection<ImageItem> allImages = null;
			
			allImages = imageDao.findByProductId(productId);
			if (allImages != null){
				imgProduct = allImages.iterator().next();
				productImagesCache.put(productId, allImages);
					for(ImageItem image : allImages){
						imagesCache.put(image.getId(), image);
					}
				}
		}
		return imgProduct;
	}
	
	private void resetCache(){
		imagesCache.clear();
		productImagesCache.clear();
	}
	
}
