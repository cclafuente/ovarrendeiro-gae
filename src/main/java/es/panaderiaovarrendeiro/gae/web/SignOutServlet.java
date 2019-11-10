package es.panaderiaovarrendeiro.gae.web;


import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;


public class SignOutServlet extends HttpServlet {
    
	private static final long serialVersionUID = 0L;

	private static final Log log = LogFactory.getLog(SignOutServlet.class);
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
    	UserService userService = UserServiceFactory.getUserService();
    	User user = userService.getCurrentUser();
    	SessionInfoUtils.setNoAdmin(req);
    	log.debug("[[- logout con doGet" + user.getNickname());
    	resp.sendRedirect(userService.createLogoutURL("/index.do?logout=true"));
    }
}
