package es.panaderiaovarrendeiro.gae.web;

import javax.servlet.http.HttpServletRequest;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.web.beans.SessionInfoBean;

public class SessionInfoUtils {

	public static void updateMenu(HttpServletRequest request, Long menu){
		SessionInfoBean sessionInfo = (SessionInfoBean)request.getAttribute(OVarrendeiroConstants.SESSION_INFO_KEY);
		
		if (sessionInfo == null){
			sessionInfo = new SessionInfoBean();
		}
		
		sessionInfo.setActiveMenu(menu);
		request.setAttribute(OVarrendeiroConstants.SESSION_INFO_KEY, sessionInfo);
	}
}
