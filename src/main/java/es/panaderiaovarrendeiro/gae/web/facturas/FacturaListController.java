package es.panaderiaovarrendeiro.gae.web.facturas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class FacturaListController extends AbstractController{

	protected final Log log = LogFactory.getLog(getClass());
	private String commandName = "factura";
	private FacturaManager facturaManager;
	private ProductBeanManager productBeanManager;
	private BaseManager<Customer> customerManager;
	
	public FacturaManager getFacturaManager() {
		return facturaManager;
	}

	public void setFacturaManager(FacturaManager facturaManager) {
		this.facturaManager = facturaManager;
	}
	
	public String getCommandName() {
		return commandName;
	}

	public BaseManager<Customer> getCustomerManager() {
		return customerManager;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public void setCustomerManager(BaseManager<Customer> customerManager) {
		this.customerManager = customerManager;
	}
	
	public ProductBeanManager getProductBeanManager() {
		return productBeanManager;
	}

	public void setProductBeanManager(ProductBeanManager productBeanManager) {
		this.productBeanManager = productBeanManager;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Long customerId = null;
		Date fechaInicio = null;
		Date fechaFin = null;
		Customer selectedCustomer = null;
		Float totalPendiente = 0f;
		
		Collection<Factura> savedFacts = new ArrayList<Factura>();
		
		ModelAndView mav = new ModelAndView(commandName + "/" + commandName + "List");
		
		List<FacturaListBean> listaFacturas = new ArrayList<FacturaListBean>(); 
		
		boolean soloPendientes = false;
		
		if (request.getParameter("pendientes") != null){
			soloPendientes = true;
		}
		
		if (StringUtils.hasText(request.getParameter("customerId"))){
			customerId = Long.valueOf(request.getParameter("customerId")); 
			if (StringUtils.hasText(request.getParameter("fechaInicio"))){
				if (StringUtils.hasText(request.getParameter("fechaInicio"))){
					log.info(" parametero fechaInicio " + request.getParameter("fechaInicio"));
					fechaInicio = df.parse(request.getParameter("fechaInicio"));
					log.info(" fechaInicio " + fechaInicio);
				}
			}
			selectedCustomer = customerManager.findById(customerId);
			if (request.getParameter("fechaFin") != null){
				if (StringUtils.hasText(request.getParameter("fechaFin"))){
					log.info(" parametero fechaFin " + request.getParameter("fechaFin"));
					fechaFin = df.parse(request.getParameter("fechaFin"));
					log.info(" fechaFin " + fechaFin);
				}
			}
		}
		savedFacts = facturaManager.findPendientesPorCustomerId(customerId, fechaInicio, fechaFin, soloPendientes);
				
		for(Factura factura : savedFacts){
			FacturaListBean flb = new FacturaListBean();
			flb.setCustomer(customerManager.findById(factura.getCustomerId()));
			flb.setFactura(factura);
			totalPendiente = totalPendiente + factura.getTotal();
			listaFacturas.add(flb);
		}
		
		mav.addObject(commandName + "List", listaFacturas);
		mav.addObject("totalPendiente", totalPendiente);
		mav.addObject("productMap", productBeanManager.getMapaProductos());
		mav.addObject("allCustomers", customerManager.findAll());
		mav.addObject("selectedCustomer", selectedCustomer);
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuFacturas);
		SessionInfoUtils.setAdmin(request);
		return mav;
	}

}
