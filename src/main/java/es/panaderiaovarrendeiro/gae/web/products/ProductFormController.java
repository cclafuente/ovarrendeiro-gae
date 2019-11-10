package es.panaderiaovarrendeiro.gae.web.products;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductManager;

public class ProductFormController extends SimpleFormController{

	private ProductManager productManager;
	private ImageManager imageManager;
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	public ProductFormController() {
		setCommandClass(Product.class);
		setCommandName("product");
		setFormView(getCommandName() + "/" + getCommandName() + "Form");
		setSuccessView("redirect:productForm.do");
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
    	return productManager.findById(longid);
    }
   
    
    @Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
    	Product product = (Product)command;
		Long id = productManager.create(product);
		
		if (request.getParameter("remove")!=null){
			productManager.remove(product);
			return new ModelAndView(getSuccessView());
		}
		
		return new ModelAndView(getSuccessView(), "id" , id);
	}
	
    @Override
    protected void initBinder(HttpServletRequest request,
    		ServletRequestDataBinder binder) throws Exception {
    	 	
    	
    		binder.registerCustomEditor(Integer.class, null,
                 new CustomNumberEditor(Integer.class, null, true));
    	 	binder.registerCustomEditor(Float.class, null,
                    new CustomNumberEditor(Float.class, OVarrendeiroConstants.defaultNumberFormat, true));
    	 	binder.registerCustomEditor(Long.class, null,
                 new CustomNumberEditor(Long.class, null, true));
			binder.registerCustomEditor(byte[].class,
                 new ByteArrayMultipartFileEditor());
			
	}

	/*@SuppressWarnings("unchecked")
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referencedObjects = new HashMap<String, Object>();
		referencedObjects.put("makeList", makeManager.findAll());
		return referencedObjects;
	}*/
    
    

}
