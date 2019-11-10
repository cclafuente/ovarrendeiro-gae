package es.panaderiaovarrendeiro.gae.web.customers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class CustomerListController extends AbstractController{

	private String commandName = "customer";
	private BaseManager<Customer> customerManager;
	
	public BaseManager<Customer> getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(BaseManager<Customer> customerManager) {
		this.customerManager = customerManager;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView(commandName + "/" + commandName + "List");
		mav.addObject(commandName + "List", customerManager.findAll());
		
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuClientes);
		
		return mav;
		
		
	}

	
	
	
}
