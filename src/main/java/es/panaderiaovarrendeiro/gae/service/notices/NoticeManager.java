package es.panaderiaovarrendeiro.gae.service.notices;

import java.util.Collection;
import java.util.Map;

import es.panaderiaovarrendeiro.gae.model.Notice;

public interface NoticeManager {

	/**
	 * @param productId
	 * @return Notice with key noticeId
	 */
	public Notice findById(Long noticeId);
	
	
   /**
   * @param vehicle a POJO with car details
   * @return a unique id for the car listing
   */
  public Long create(Notice product);

  /**
   * Retrieves all entities from the datastore.
   *
   * @param page page number of the recordset to be retrieved
   * @return a list of all Car entities
   */
  public Collection<Notice> getAllNotices(int page);
	
  /**
   * @param conds
   * @return
   */
  public Collection<Notice> findByMap(Map<String, ?> conds);
  
  public void remove(Notice product);
  
  /**
   * @param number
   * @return last updated products
   */
  public Collection<Notice> getLastUpdated(int number);
	
}
