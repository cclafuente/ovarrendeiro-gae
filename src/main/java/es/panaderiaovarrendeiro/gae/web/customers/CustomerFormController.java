package es.panaderiaovarrendeiro.gae.web.customers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.service.BaseManager;

public class CustomerFormController extends SimpleFormController{

		private BaseManager<Customer> customerManager;
		
		public BaseManager<Customer> getCustomerManager() {
			return customerManager;
		}

		public void setCustomerManager(BaseManager<Customer> customerManager) {
			this.customerManager = customerManager;
		}

		public CustomerFormController() {
			setCommandClass(Customer.class);
			setCommandName("customer");
			setFormView(getCommandName() + "/" + getCommandName() + "Form");
			setSuccessView("redirect:customerList.do");
		}
		
		 /**
	     * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	     */
	    protected Object formBackingObject(HttpServletRequest request) throws Exception {
	    	Object command = load(request);
	    	if(command == null) {
	            command = createCommand();
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
	    	Long longid = Long.valueOf(id);
	    	return customerManager.findById(longid);
	    }
	   
	    
	    @Override
		protected ModelAndView onSubmit(HttpServletRequest request,
				HttpServletResponse response, Object command, BindException errors)
				throws Exception {
			Long id = null;
	    	if (request.getParameter("remove")!=null){
				customerManager.removeById(id);
				return new ModelAndView(getSuccessView());
			}
			else{
				Customer customer = (Customer)command;
				id = (Long)customerManager.save(customer);
			}
			return new ModelAndView(getSuccessView(), "id" , id);
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
	    }
	 
}
