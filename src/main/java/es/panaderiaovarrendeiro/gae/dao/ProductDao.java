package es.panaderiaovarrendeiro.gae.dao;

import java.util.Collection;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.model.Product;

/**
 * A data access object specification for Car entity.
 *
 * @author zoso@google.com (Anirudh Dewani)
 */
public interface ProductDao {

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

  public void remove(Product product);
  
  public Collection<Product> getLastUpdated(int number);
  
  /**
   * @param conds
   * @return lista de productos
   */
  public Collection<Product> getObjectsClauseEqual(Map<String, ?> conds);
}

