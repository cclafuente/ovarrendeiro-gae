package es.panaderiaovarrendeiro.gae.dao;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.orm.jdo.support.JdoDaoSupport;

import es.panaderiaovarrendeiro.gae.model.ImageItem;

public class ImageJdoDao extends JdoDaoSupport implements ImageDao {

	public ImageItem findById(Long imageId) {
		return (ImageItem)getJdoTemplate().getObjectById(ImageItem.class, imageId);
	}

	public Collection<ImageItem> findByProductId(Long productId) {
		
		PersistenceManager pm = getPersistenceManager();
		    try {
		      Query query = pm.newQuery(ImageItem.class);
		      query.setFilter("productId == pId");
		      query.declareParameters("Long pId");
		      
		      return pm.detachCopyAll((Collection<ImageItem>)
		          query.execute(productId));
		    }
		    finally {
		      releasePersistenceManager(pm);
		    }
	}

	public Long saveImage(ImageItem image) {
		 /*
	     * Uses Spring's JdoTemplate helper since the entity's state doesn't need
	     * to be changed more than once. JdoTemplate will obtain and release a
	     * PersistenceManager.
	     */
	    getJdoTemplate().makePersistent(image);
	    return image.getId();
	}
	
	public void remove(ImageItem image){
		try{
		
			PersistenceManager pm = getJdoTemplate().getPersistenceManagerFactory().getPersistenceManager();	
			
			pm.currentTransaction().begin();
			image = pm.getObjectById(ImageItem.class, image.getId());
			pm.deletePersistent(image);
			pm.currentTransaction().commit();
	
			//getJdoTemplate().deletePersistent(image);
			System.out.println(" IMAGEN ELIMINADA CORRECTAMENTE.");
		
		}catch(Exception e){
			System.out.println("ERROR ELIMINANDO IMAGEN: " + e.getLocalizedMessage());
		}
		
	}

}
