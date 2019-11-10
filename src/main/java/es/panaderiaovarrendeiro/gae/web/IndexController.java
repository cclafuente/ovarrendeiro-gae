package es.panaderiaovarrendeiro.gae.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.service.notices.NoticeManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;


public class IndexController implements Controller {

	private ProductBeanManager productBeanManager;
	private NoticeManager noticeManager;
	
	public ProductBeanManager getProductBeanManager() {
		return productBeanManager;
	}

	public void setProductBeanManager(ProductBeanManager productBeanManager) {
		this.productBeanManager = productBeanManager;
	}
	
	public NoticeManager getNoticeManager() {
		return noticeManager;
	}

	public void setNoticeManager(NoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuInicio, null);
		
		ModelAndView retView = new ModelAndView("index");
		retView.addObject("productList", null);//productBeanManager.findLastUpdated(1));
		retView.addObject("noticeList", noticeManager.getLastUpdated(2));
		return retView;
	}

	
	
}
