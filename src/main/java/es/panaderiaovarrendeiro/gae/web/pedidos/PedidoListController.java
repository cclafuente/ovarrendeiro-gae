package es.panaderiaovarrendeiro.gae.web.pedidos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.service.pedidos.PedidoManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class PedidoListController extends AbstractController{

	protected final Log log = LogFactory.getLog(getClass());
	private String commandName = "pedido";
	private PedidoManager pedidoManager;
	private ProductBeanManager productBeanManager;
	private BaseManager<Customer> customerManager;
	private boolean ajax = false;
	
	public PedidoManager getPedidoManager() {
		return pedidoManager;
	}

	public void setPedidoManager(PedidoManager pedidoManager) {
		this.pedidoManager = pedidoManager;
	}

	public ProductBeanManager getProductBeanManager() {
		return productBeanManager;
	}

	public void setProductBeanManager(ProductBeanManager productBeanManager) {
		this.productBeanManager = productBeanManager;
	}
	
	public BaseManager<Customer> getCustomerManager() {
		return customerManager;
	}

	public void setCustomerManager(BaseManager<Customer> customerManager) {
		this.customerManager = customerManager;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SessionInfoUtils.setAdmin(request);
		
		String viewName = commandName + "/" + commandName + "List";
		if (ajax){
			viewName = viewName + "ajax";
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = null;
		Date fechaFin = null;
		String email = null;
		Long customerId = null;
		if (StringUtils.hasText(request.getParameter("email"))){
			email = request.getParameter("email");
			log.info(" filtramos por email " + email);
		}
		if (StringUtils.hasText(request.getParameter("customerId"))){
			customerId = Long.valueOf(request.getParameter("customerId"));
			log.info(" filtramos por email " + email);
		}
		if (StringUtils.hasText(request.getParameter("fechaInicio"))){
			if (StringUtils.hasText(request.getParameter("fechaInicio"))){
				log.info(" parametero fechaInicio " + request.getParameter("fechaInicio"));
				fechaInicio = df.parse(request.getParameter("fechaInicio"));
				log.info(" fechaInicio " + fechaInicio);
			}
		}
		if (request.getParameter("fechaFin") != null){
			if (StringUtils.hasText(request.getParameter("fechaFin"))){
				log.info(" parametero fechaFin " + request.getParameter("fechaFin"));
				fechaFin = df.parse(request.getParameter("fechaFin"));
				log.info(" fechaFin " + fechaFin);
			}
		}
		mav.addObject(commandName + "List", pedidoManager.findByFilter(customerId, email, fechaInicio, fechaFin));
		mav.addObject("productMap", productBeanManager.getMapaProductos());
		Map<Long, Customer> mapCustomers = new HashMap<Long, Customer>();
		for(Customer customer: customerManager.findAll()){
			mapCustomers.put(customer.getId(), customer);
		}
		mav.addObject("customerMap", mapCustomers);
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuPedidos);
		return mav;
	}
	
}
