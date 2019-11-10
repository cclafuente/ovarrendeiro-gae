package es.panaderiaovarrendeiro.gae.web.utils;

import javax.servlet.http.HttpServletRequest;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.web.beans.SessionInfoBean;

public class SessionInfoUtils {

	public static void updateMenu(HttpServletRequest request, Long menu, Long submenu){
		SessionInfoBean sessionInfo = (SessionInfoBean)request.getSession().getAttribute(OVarrendeiroConstants.SESSION_INFO_KEY);
		if (sessionInfo == null){
			sessionInfo = new SessionInfoBean();
		}
		sessionInfo.setActiveSubMenu(submenu);
		sessionInfo.setActiveMenu(menu);
		request.getSession().setAttribute(OVarrendeiroConstants.SESSION_INFO_KEY, sessionInfo);
	}
	
	public static void setAdmin(HttpServletRequest request){
		SessionInfoBean sessionInfo = (SessionInfoBean)request.getSession().getAttribute(OVarrendeiroConstants.SESSION_INFO_KEY);
		if (sessionInfo == null){sessionInfo = new SessionInfoBean();}
		sessionInfo.setAdmin(Boolean.TRUE);
		request.getSession().setAttribute(OVarrendeiroConstants.SESSION_INFO_KEY, sessionInfo);
	}
	
	public static void setNoAdmin(HttpServletRequest request){
		SessionInfoBean sessionInfo = (SessionInfoBean)request.getSession().getAttribute(OVarrendeiroConstants.SESSION_INFO_KEY);
		if (sessionInfo == null){sessionInfo = new SessionInfoBean();}
		sessionInfo.setAdmin(Boolean.FALSE);
		request.getSession().setAttribute(OVarrendeiroConstants.SESSION_INFO_KEY, sessionInfo);
	}

}
