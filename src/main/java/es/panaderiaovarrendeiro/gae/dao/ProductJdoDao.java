package es.panaderiaovarrendeiro.gae.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.orm.jdo.support.JdoDaoSupport;

import es.panaderiaovarrendeiro.gae.model.Product;

/**
 * A JDO implementation object for VehicleDao.
 *
 * @author zoso@google.com (Anirudh Dewani)
 */
public class ProductJdoDao extends JdoDaoSupport implements ProductDao {

  public Product findById(Long productId) {
	  return (Product)getJdoTemplate().getObjectById(Product.class, productId);
	}


  public Long create(Product vehicle) {

    /*
     * Uses Spring's JdoTemplate helper since the entity's state doesn't need
     * to be changed more than once. JdoTemplate will obtain and release a
     * PersistenceManager.
     */
    getJdoTemplate().makePersistent(vehicle);
    return vehicle.getId();
  }

 
  public Collection<Product> getAllProducts(int page) {
    PersistenceManager pm = getPersistenceManager();
    try {
      page = (page == 0) ? 1 : page;
      Query query = pm.newQuery(Product.class);
      //query.setFilter("status == pAvl");
      //query.declareParameters("String pAvl");
      
      return pm.detachCopyAll((Collection<Product>)
          query.execute());
    }
    finally {
      releasePersistenceManager(pm);
    }
  }

  public Collection<Product> getObjectsClauseEqual(Map<String, ?> conds) {
  	PersistenceManager pm = getPersistenceManager();
      Query query = pm.newQuery(Product.class);
      StringBuilder filters = new StringBuilder(36);
      StringBuilder declaredParams = new StringBuilder(36);
      Map<String, Object> params = new HashMap<String, Object>();

      Set<String> keys = conds.keySet();
      
      for(String key : keys){
      	if (filters.length() > 0) {
              filters.append(" && ");
              declaredParams.append(" ,");
          }
      	filters.append(key + " == " + "p" + key);
      	declaredParams.append("int " + "p" + key);
      	params.put("p" + key, conds.get(key));
      }
      
      query.declareParameters(declaredParams.toString());
      query.setFilter(filters.toString());
      
      try {
        return pm.detachCopyAll((Collection<Product>)
            query.executeWithMap(params));
      }
      finally {
        releasePersistenceManager(pm);
      }
    }
  

  public void remove(Product product) {
		try{
			
			PersistenceManager pm = getJdoTemplate().getPersistenceManagerFactory().getPersistenceManager();	
			
			pm.currentTransaction().begin();
			product = pm.getObjectById(Product.class, product.getId());
			pm.deletePersistent(product);
			pm.currentTransaction().commit();
	
			//getJdoTemplate().deletePersistent(image);
			System.out.println(" PRODUCTO ELIMINADO CORRECTAMENTE ");
		
		}catch(Exception e){
			System.out.println(" ERROR ELIMINANDO PRODUCTO: " + e.getLocalizedMessage());
		}
  	}


  	public Collection<Product> getLastUpdated(int number) {
  		 PersistenceManager pm = getPersistenceManager();
  	    Query query = pm.newQuery(Product.class);//, "order by lastUpdate limit " + number);
  	    query.setOrdering("lastUpdate desc");
  	    query.setRange("0, " + number);
  	    
  	    try {
  	      return pm.detachCopyAll((Collection<Product>)
  	          query.execute());
  	    }
  	    finally {
  	      releasePersistenceManager(pm);
  	    }
  	}
  
  
}

