package es.panaderiaovarrendeiro.gae.dao;

import java.util.Collection;

import es.panaderiaovarrendeiro.gae.model.ImageItem;

public interface ImageDao {

	public Long saveImage(ImageItem image);
		
	public Collection<ImageItem> findByProductId(Long productId);
		
	public ImageItem findById(Long imageId);
	
	public void remove(ImageItem imageItem);

}
