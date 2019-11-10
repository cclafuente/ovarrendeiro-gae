package es.panaderiaovarrendeiro.gae.service.products;

import java.util.Collection;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.model.Product;

public interface ProductManager {

	/**
	 * @param productId
	 * @return Product with key productId
	 */
	public Product findById(Long productId);
	
	
   /**
   * @param vehicle a POJO with car details
   * @return a unique id for the car listing
   */
  public Long create(Product product);

  /**
   * Retrieves all entities from the datastore.
   *
   * @param page page number of the recordset to be retrieved
   * @return a list of all Car entities
   */
  public Collection<Product> getAllProducts(int page);
	
  /**
   * @param conds
   * @return
   */
  public Collection<Product> findByMap(Map<String, ?> conds);
  
  public void remove(Product product);
  
  /**
   * @param number
   * @return last updated products
   */
  public Collection<Product> getLastUpdated(int number);
  
}
