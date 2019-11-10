package es.panaderiaovarrendeiro.gae.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ClearSessionServlet extends HttpServlet {

	private static final long serialVersionUID = 0L;

	private static final Log log = LogFactory.getLog(ClearSessionServlet.class);
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws IOException {
		 
		 DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 	Query query = new Query("_ah_SESSION");
		 	query.addSort("_expires", SortDirection.ASCENDING);
		 	
			PreparedQuery results = datastore.prepare(query);

			log.warn("Deleting " + results.countEntities() + " sessions from data store");
			int cleared = 0;
			try {
				for (Entity session : results.asIterable()) {				
					datastore.delete(session.getKey());
					cleared++;
					if (cleared > 500){
						break;
					}
				}
			} catch (Throwable e) {
				log.error(e.getMessage());
			}
	    }
}
