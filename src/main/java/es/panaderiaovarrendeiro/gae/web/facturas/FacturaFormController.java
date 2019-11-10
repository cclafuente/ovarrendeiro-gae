package es.panaderiaovarrendeiro.gae.web.facturas;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.model.FacturaLinea;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManager;
import es.panaderiaovarrendeiro.gae.util.DateUtils;

public class FacturaFormController extends SimpleFormController{

		private static final Log log = LogFactory.getLog(FacturaFormController.class);
	
		private BaseManager<Customer> customerManager;
		private FacturaManager facturaManager;
		
		public FacturaManager getFacturaManager() {
			return facturaManager;
		}

		public void setFacturaManager(FacturaManager facturaManager) {
			this.facturaManager = facturaManager;
		}
		
		public BaseManager<Customer> getCustomerManager() {
			return customerManager;
		}

		public void setCustomerManager(BaseManager<Customer> customerManager) {
			this.customerManager = customerManager;
		}

		public FacturaFormController() {
			setCommandClass(Factura.class);
			setCommandName("factura");
			setFormView(getCommandName() + "/" + getCommandName() + "Form");
			setSuccessView("redirect:" + getCommandName() + "List.do");
		}
		
		 /**
	     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	     */
	    protected Object formBackingObject(HttpServletRequest request) throws Exception {
	    	Object command = load(request);
	    	if(command == null) {
	            command = createCommand();
	            log.debug(" creando factura ");
	            ((Factura)command).setFecha(DateUtils.fechaSinHoras(new Date()));
	        }
	        return command;
	    }

	    protected Object load(HttpServletRequest request) throws Exception {
	    	Object command = null;
	        String id = request.getParameter("id");
	        if (StringUtils.hasText(id)) {
	            command =  load(id);
	        }
	        return command;
	    }

	    protected Object load(String id) throws Exception {
	    	return facturaManager.findByLongId(Long.valueOf(id));
	    }
	   
	    
	    @Override
		protected ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			Key id = null;
			
			log.debug(" onSubmit correctamente llamado ");
			
	    	if (request.getParameter("remove")!=null){
	    		Key key = KeyFactory.createKey(Factura.class.getSimpleName(), Long.valueOf(request.getParameter("longSavedKey")));
	    		facturaManager.removeById(key);
				return new ModelAndView(getSuccessView());
			}
			else{
				log.debug(" cargando factura de command ");
				Factura factura = (Factura)command;
				if (StringUtils.hasText(request.getParameter("longSavedKey"))){
					//buscamos la factura por key
					log.error(request.getParameter("longSavedKey"));
						Factura savedFactura = facturaManager.findByLongId(Long.valueOf(request.getParameter("longSavedKey")));
						facturaManager.removeById(savedFactura.getId());
						int numLineas = Integer.valueOf(request.getParameter("numLineas"));
						for(int i= 0; i < numLineas; i++){
							FacturaLinea fl = crearFacturaLinea(request, i);
							if (fl.getNumeroLinea() != null){
								fl.setFactura(factura);
								factura.getLineasFactura().add(fl);
							}
						}
				}else{
					int numLineas = Integer.valueOf(request.getParameter("numLineas"));
					for(int i= 0; i < numLineas; i++){
						FacturaLinea fl = crearFacturaLinea(request, i);
						if (fl.getNumeroLinea() != null){
							fl.setFactura(factura);
							factura.getLineasFactura().add(fl);
						}
					}
				}
				log.debug(" factura to String " + factura.toString());
				log.debug(" cliente to String " + factura.getCustomerId());
				//customer.getFacturas().add(factura);
				//customerManager.save(customer);//ojo con guardar el parent, hay que cambiar esto, valido solo para lo que es
				//id = (Serializable)facturaManager.save(factura);
				id = (Key)facturaManager.save(factura);
			}
			return new ModelAndView(getSuccessView(), "id" , id.getId());
		}
		
	    @Override
	    protected void initBinder(HttpServletRequest request,
	    		ServletRequestDataBinder binder) throws Exception {
	    	 	binder.registerCustomEditor(Integer.class, null,
	                 new CustomNumberEditor(Integer.class, null, true));
	    	 	binder.registerCustomEditor(Long.class, null,
	                 new CustomNumberEditor(Long.class, null, true));
				binder.registerCustomEditor(byte[].class,
	                 new ByteArrayMultipartFileEditor());
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
				binder.registerCustomEditor(Customer.class,
				        new PropertyEditorSupport() {
				            @Override
				            public void setAsText(String text) {
				                Customer customer = customerManager.findById(Long.valueOf(text));
				                setValue(customer);
				            }
				        });

		}
	    
	    @Override
	    protected Map referenceData(HttpServletRequest request) throws Exception {
	    	Map refObjects = super.referenceData(request);
	    	if (refObjects == null){
	    		refObjects = new HashMap();
	    	}
	    	refObjects.put("customerList", customerManager.findAll());
	    	return refObjects;
	    }
	    
	    
	    private static FacturaLinea crearFacturaLinea(HttpServletRequest request, int numLinea){
	    	log.debug(" creando num linea de request ::: " + numLinea);
	    	FacturaLinea fl = new FacturaLinea();
	    	if (StringUtils.hasText(request.getParameter("cant_" + numLinea))){
	    		fl.setNumeroLinea(numLinea);
	    		fl.setCantidad(Float.valueOf(request.getParameter("cant_" + numLinea)));
	    		fl.setPrecio(Float.valueOf(request.getParameter("precioUnidad_" + numLinea)));
	    		fl.setIva(Integer.valueOf(request.getParameter("iva_" + numLinea)));
	    		fl.setTotal(Float.valueOf(request.getParameter("subtotal_" + numLinea)));
	    		fl.setProductId(Long.valueOf(request.getParameter("prod_" + numLinea)));
	    	}
	    	return fl;
	    }
}
