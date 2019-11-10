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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.model.Customer;
import es.panaderiaovarrendeiro.gae.model.Pedido;
import es.panaderiaovarrendeiro.gae.model.PedidoLinea;
import es.panaderiaovarrendeiro.gae.service.BaseManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;

public class PedidoFormCustomerController extends SimpleFormController{

	private static final Log log = LogFactory.getLog(PedidoFormCustomerController.class);

	private BaseManager<Pedido> pedidoManager;
	private ProductBeanManager productBeanManager;
	private BaseManager<Customer> customerManager;
	
	public BaseManager<Pedido> getPedidoManager() {
		return pedidoManager;
	}

	public void setPedidoManager(BaseManager<Pedido> pedidoManager) {
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

	public PedidoFormCustomerController() {
		setCommandClass(Pedido.class);
		setCommandName("pedido");
		setFormView(getCommandName() + "/" + getCommandName() + "FormCustomer");
		setSuccessView("redirect:" + getCommandName() + "FormCustomer.do");
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
    	Key key = KeyFactory.createKey(Pedido.class.getSimpleName(), Long.valueOf(id));
    	return pedidoManager.findById(key);
    }
   
    
    @Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Key id = null;
		
		String aditionalParam = "";
		
		log.debug(" onSubmit correctamente llamado ");
		if (request.getParameter("remove")!=null){
    		//TODO por una vez, vamos a eliminarlos todos
			Key key = KeyFactory.createKey(Pedido.class.getSimpleName(), Long.valueOf(request.getParameter("longSavedKey")));
    		pedidoManager.removeById(key);
    		return new ModelAndView(getSuccessView() + "?removed=true");
		}
		else{
			log.debug(" cargando factura de command ");
			Pedido pedido = (Pedido)command;
			if (StringUtils.hasText(request.getParameter("longSavedKey"))){
				//buscamos la factura por key
				log.error(request.getParameter("longSavedKey"));
					Key key = KeyFactory.createKey(Pedido.class.getSimpleName(), Long.valueOf(request.getParameter("longSavedKey")));
					pedido = pedidoManager.findById(key);
					pedido.getLineasPedido().clear();
					//pedidoManager.removeById(savedPedido.getId());
					int numLineas = Integer.valueOf(request.getParameter("numLineas"));
					for(int i= 1; i <= numLineas; i++){
						crearPedidoLinea(request, i, pedido);
					}
					aditionalParam = "edited=true";
			}else{
				int numLineas = Integer.valueOf(request.getParameter("numLineas"));
				for(int i= 0; i <= numLineas; i++){
					crearPedidoLinea(request, i, pedido);
				}
				aditionalParam = "added=true";
			}
			log.warn(" factura to String " + pedido.toString());
			log.warn(" cliente to String " + pedido.getCustomerId());
			//customer.getFacturas().add(factura);
			//customerManager.save(customer);//ojo con guardar el parent, hay que cambiar esto, valido solo para lo que es
			//id = (Serializable)facturaManager.save(factura);
			id = (Key)pedidoManager.save(pedido);
		}
		return new ModelAndView(getSuccessView() + "?" + aditionalParam, "id" , id.getId());
	}
	
    @Override
    protected void initBinder(HttpServletRequest request,
    		ServletRequestDataBinder binder) throws Exception {
    	 	binder.registerCustomEditor(Integer.class, null,
                 new CustomNumberEditor(Integer.class, null, true));
    	 	binder.registerCustomEditor(Long.class, null,
                 new CustomNumberEditor(Long.class, null, true));
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
			SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuPedidos, 0L);
	}
    
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
    	Map refObjects = super.referenceData(request);
    	if (refObjects == null){
    		refObjects = new HashMap();
    	}
    	
    	refObjects.put("productList", productBeanManager.findAll());
    	refObjects.put("customerList", customerManager.findAll());
    	return refObjects;
    }
    
    
    private static void crearPedidoLinea(HttpServletRequest request, int numLinea, Pedido pedido){
    	try{
    	log.warn(" creando num linea de request ::: " + numLinea);
    	PedidoLinea fl = new PedidoLinea();
    	if (StringUtils.hasText(request.getParameter("cant_" + numLinea))){
    		fl.setNumeroLinea(numLinea);
    		fl.setCantidad(Float.valueOf(request.getParameter("cant_" + numLinea)));
    		fl.setPrecio(Float.valueOf(request.getParameter("precio_" + numLinea)));
    		fl.setIva(Integer.valueOf(request.getParameter("iva_" + numLinea)));
    		fl.setTotal(Float.valueOf(request.getParameter("subtotal_" + numLinea)));
    		fl.setProductId(Long.valueOf(request.getParameter("prod_" + numLinea)));
    		fl.setPedido(pedido);
    		pedido.getLineasPedido().add(fl);
    		log.warn(" linea pedido generada " + fl.toString());
    	}
    	}catch(Exception e){
    		log.error(" error creando linea " + numLinea, e);
    	}
    }
}