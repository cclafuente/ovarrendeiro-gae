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

public class FacturaPaymentAjaxController extends AbstractController{

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
			
			ModelAndView mav = new ModelAndView(commandName + "/" + commandName + "PaymentAjax");
			
			System.out.println(" factura payment ajax " + request.getParameter("facturaIds"));
			
			if (StringUtils.hasLength(request.getParameter("facturaIds"))){
				String [] facturasStr = request.getParameterValues("facturaIds");
				for(String facturaId : facturasStr){
					Factura pendiente = facturaManager.findByLongId(Long.valueOf(facturaId));
					pendiente.setPagado(true);
					pendiente.setLastUpdate(new Date());
					facturaManager.save(pendiente);
				}
				mav.addObject("facturasStr", facturasStr);
			}
			return mav;
		}
	
	
}
