package es.panaderiaovarrendeiro.gae.web.facturas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.model.FacturaLinea;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManager;
import es.panaderiaovarrendeiro.gae.util.DateUtils;

public class FacturaFormAjaxController extends SimpleFormController{

	private static final Log log = LogFactory.getLog(FacturaFormController.class);

	private FacturaManager facturaManager;
	
	public FacturaManager getFacturaManager() {
		return facturaManager;
	}

	public void setFacturaManager(FacturaManager facturaManager) {
		this.facturaManager = facturaManager;
	}
	
	public FacturaFormAjaxController() {
		setCommandClass(Factura.class);
		setCommandName("factura");
		setFormView(getCommandName() + "/" + getCommandName() + "FormAjax");
		setFormView(getCommandName() + "/" + getCommandName() + "FormAjax");
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
		Factura factura = (Factura)command;
		factura.setPagado(false);
		FacturaLinea facturaLinea = new FacturaLinea();
		facturaLinea.setIva(0);
		facturaLinea.setPrecio(factura.getTotal());
		facturaLinea.setNumeroLinea(1);
		facturaLinea.setCantidad(1F);
		facturaLinea.setProductId(null);
		facturaLinea.setTotal(factura.getTotal());
		factura.getLineasFactura().add(facturaLinea);
		facturaLinea.setFactura(factura);
		Key key = (Key)facturaManager.save(factura);
		log.warn(" factura guardada: " + factura.toString());
		return new ModelAndView(getSuccessView(), "id" , key.getId());
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
	}
    
    
}