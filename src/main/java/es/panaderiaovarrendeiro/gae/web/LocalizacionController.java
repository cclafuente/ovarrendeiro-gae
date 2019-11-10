package es.panaderiaovarrendeiro.gae.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;


public class LocalizacionController implements Controller {

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//inicializamos makeManager
		//makeManager.findAll();
		
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuLocalizacion, null);
		
		ModelAndView retView = new ModelAndView("localizacion");
		return retView;
	}

	
	
}
