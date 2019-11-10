package es.panaderiaovarrendeiro.gae.service.images;

import java.util.Collection;

import es.panaderiaovarrendeiro.gae.model.ImageItem;

public interface ImageManager {

	public Long saveImage(ImageItem image);
	
	public Collection<ImageItem> findByProductId(Long productId);
		
	public ImageItem findById(Long imageId);
	
	public void remove(Long imageId);
	
	public void removeByProductId(Long productId);
	
	public ImageItem findFirstImageProduct(Long productId);
}
