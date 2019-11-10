package es.panaderiaovarrendeiro.gae.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.jdo.support.JdoDaoSupport;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 *
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *      &lt;bean id="fooDao" class="com.softtek.sample.dao.hibernate.BaseJdoDao"&gt;
 *          &lt;constructor-arg value="com.softtek.sample.model.Foo"/&gt;
 *          &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class BaseJdoDao<T> extends JdoDaoSupport implements BaseDao<T> {
   
	protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist
     * @param persistentClass the class type you'd like to persist
     */
    public BaseJdoDao(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @SuppressWarnings("unchecked")
	public Collection<T> getAll() {
    	
    	PersistenceManager pm = getPersistenceManager();
	    try {
	      Query query = pm.newQuery(this.persistentClass);
	      return pm.detachCopyAll((Collection<T>)query.execute());
	    }
	    finally {
	      releasePersistenceManager(pm);
	    }
    	
    	
    	
       // return super.getJdoTemplate().find(this.persistentClass);
    }

    @SuppressWarnings("unchecked")
	public T get(Serializable id) {
        return (T) super.getJdoTemplate().getObjectById(this.persistentClass, id);
    }

    public void save(T object) {
    	super.getJdoTemplate().makePersistent(object);
    }

    public void remove(Serializable id) {
    	try{
    		PersistenceManager pm = getJdoTemplate().getPersistenceManagerFactory().getPersistenceManager();	
			T object = (T) getJdoTemplate().getObjectById(this.persistentClass, id);
			pm.makePersistent(object);
			ObjectState state = JDOHelper.getObjectState(object);
			log.info(state.name() + " " + state.toString() + " ordinal " + state.ordinal());
			pm.deletePersistent(object);
		}catch(Exception e){
			log.info("ERROR ELIMINANDO OBJETO: " + e.getLocalizedMessage());
		}
    	
    	
        //super.getJdoTemplate().deletePersistent(get(id));
    }

    public Collection<T> getObjectsClauseEqual(Map<String, ?> conds) {
    	PersistenceManager pm = getPersistenceManager();
        Query query = pm.newQuery(this.persistentClass);
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
          return pm.detachCopyAll((Collection<T>)
              query.executeWithMap(params));
        }
        finally {
          releasePersistenceManager(pm);
        }
      }

	public Collection<T> getLastUpdated(int number) {
		 PersistenceManager pm = getPersistenceManager();
	  	    Query query = pm.newQuery(this.persistentClass);//, "order by lastUpdate limit " + number);
	  	    query.setOrdering("lastUpdate desc");
	  	    query.setRange("0, " + number);
	  	    
	  	    try {
	  	      return pm.detachCopyAll((Collection<T>)
	  	          query.execute());
	  	    }
	  	    finally {
	  	      releasePersistenceManager(pm);
	  	    }
	}
    
    

}
