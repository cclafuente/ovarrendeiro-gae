package es.panaderiaovarrendeiro.gae.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.dao.ImageDao;
import es.panaderiaovarrendeiro.gae.model.ImageItem;


public class ImageManagerImpl implements ImageManager{

	private static Map<Long, ImageItem> imagesMap = new HashMap<Long, ImageItem>();
	private static Map<Long, Collection<ImageItem>> productImagesMap = new HashMap<Long, Collection<ImageItem>>();
	
	private ImageDao imageDao;
	
	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public ImageItem findById(Long imageId) {
		
		ImageItem image = null;
		
		if (imagesMap.containsKey(imageId)){
			image = imagesMap.get(imageId);
		}else{
			image = imageDao.findById(imageId);
			imagesMap.put(imageId, image);
		}
		return image;
	}

	public Collection<ImageItem> findByProductId(Long productId) {
		return imageDao.findByProductId(productId);
	}

	public Long saveImage(ImageItem image) {
		return imageDao.saveImage(image);
	}

	public void remove(Long imageId) {
		if (imagesMap.containsKey(imageId)){
			imagesMap.remove(imagesMap.get(imageId));
		}
		imageDao.remove(findById(imageId));
	}

	public void removeByProductId(Long productId) {
		Collection<ImageItem> removeCollection = findByProductId(productId);
		
		for(ImageItem imageToRemove : removeCollection){
			imageDao.remove(imageToRemove);
		}
	}
	
	public ImageItem findFirstImageProduct(Long productId){
		ImageItem imgProduct = null;
		
		if (productImagesMap.containsKey(productId)){
			imgProduct = productImagesMap.get(productId).iterator().next();
		}else{
			Collection<ImageItem> allImages = null;
			
			allImages = imageDao.findByProductId(productId);
			if (allImages != null){
				imgProduct = allImages.iterator().next();
				productImagesMap.put(productId, allImages);
					for(ImageItem image : allImages){
						imagesMap.put(image.getId(), image);
					}
				}
		}
		return imgProduct;
	}
	

}
