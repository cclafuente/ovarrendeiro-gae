package es.panaderiaovarrendeiro.gae.web.notices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.service.notices.NoticeManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class NoticeListController extends AbstractController{

	private NoticeManager noticeManager;
	
	public NoticeManager getNoticeManager() {
		return noticeManager;
	}

	public void setNoticeManager(NoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView("notice/noticeList");
		mav.addObject("noticeList", noticeManager.getAllNotices(0));
		
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuNoticias);
		
		return mav;
	}

	
	
	
}
