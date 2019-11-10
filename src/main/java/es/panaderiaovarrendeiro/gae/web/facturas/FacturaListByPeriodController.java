package es.panaderiaovarrendeiro.gae.web.facturas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.util.DateUtils;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class FacturaListByPeriodController extends AbstractController{

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
		DateFormat formatoKey = new SimpleDateFormat("ddMMyyyy");
		List<Date> fechas = new ArrayList<Date>();
		Date currentDate = new Date();
		currentDate = DateUtils.fechaSinHoras(currentDate);
		Long customerId = null;
		Date fechaInicio = currentDate;
		Date fechaFin = currentDate;
		Customer selectedCustomer = null;
		Float totalPendiente = 0f;
		Map<String, Factura> mapaClientesFechaFacturas = new HashMap<String, Factura>();
		Map<Long, Float> totalesPorCliente = new HashMap<Long, Float>();
		Collection<Factura> savedFacts = new ArrayList<Factura>();
		
		ModelAndView mav = new ModelAndView(commandName + "/" + commandName + "ListByPeriod");
		
		boolean soloPendientes = false;
		
		if (request.getParameter("pendientes") != null){
			soloPendientes = true;
		}
		
		if (StringUtils.hasText(request.getParameter("fechaInicio"))){
			if (StringUtils.hasText(request.getParameter("fechaInicio"))){
				log.info(" parametero fechaInicio " + request.getParameter("fechaInicio"));
				fechaInicio = df.parse(request.getParameter("fechaInicio"));
				log.info(" fechaInicio " + fechaInicio);
			}
		}
		
		if (StringUtils.hasText(request.getParameter("fechaFin"))){
			if (StringUtils.hasText(request.getParameter("fechaFin"))){
				log.info(" parametero fechaFin " + request.getParameter("fechaFin"));
				fechaFin = df.parse(request.getParameter("fechaFin"));
				log.info(" fechaFin " + fechaFin);
			}
		}
		
		currentDate = fechaInicio;
		if (fechaInicio.getTime() <= fechaFin.getTime()){
			while(currentDate.getTime() <= fechaFin.getTime()){
				fechas.add(currentDate);
				currentDate = DateUtils.addDays(currentDate, 1);
			}
		}
		
		if (StringUtils.hasText(request.getParameter("customerId"))){
			customerId = Long.valueOf(request.getParameter("customerId")); 
			selectedCustomer = customerManager.findById(customerId);
		}
		//for(Date fecha : fechas){
			savedFacts.addAll(facturaManager.findPendientesPorCustomerId(customerId, fechaInicio, fechaFin, soloPendientes));
		//}
		
		System.out.println(" lista de facturas recuperadas " + savedFacts.size());
		log.info(" lista de facturas recuperadas " + savedFacts.size());
		
		for(Factura factura : savedFacts){
			if (totalesPorCliente.containsKey(factura.getCustomerId())){
				Float anterior = totalesPorCliente.get(factura.getCustomerId());
				anterior = anterior + factura.getTotal();
				totalesPorCliente.put(factura.getCustomerId(), anterior);
			}else{
				totalesPorCliente.put(factura.getCustomerId(), factura.getTotal());
			}
			mapaClientesFechaFacturas.put(factura.getCustomerId()  + "_" + formatoKey.format(factura.getFecha()), factura);
			log.info(" mapaClientes add factura " + factura.getCustomerId()  + "_" + formatoKey.format(factura.getFecha()) + " factura " + factura.toString());
			totalPendiente = totalPendiente + factura.getTotal();
		}
		mav.addObject("totalPendiente", totalPendiente);
		mav.addObject("allCustomers", customerManager.findAll());
		mav.addObject("selectedCustomer", selectedCustomer);
		mav.addObject("fechas", fechas);
		mav.addObject("mapaClientesFechaFacturas", mapaClientesFechaFacturas);
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuFacturas);
		SessionInfoUtils.setAdmin(request);
		return mav;
	}
}