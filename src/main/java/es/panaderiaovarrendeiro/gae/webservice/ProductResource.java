package es.panaderiaovarrendeiro.gae.webservice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.springframework.web.context.WebApplicationContext;

import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.service.products.ProductManager;


public class ProductResource extends ServerResource {

	@Get
    public Representation findAll()
    {
        Representation response = null;
        try
        {
        	Map<String, Object> atributos = getContext().getAttributes();
        	
        	ServletContext context = (ServletContext)atributos.get("org.restlet.ext.servlet.ServletContext");
        	
        	WebApplicationContext springContext = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        	
        	ProductManager produtManager = (ProductManager)springContext.getBean("productManager");
        	
        	Collection<Product> customerList = produtManager.getAllProducts(1);
        	
        	StringBuilder strBuilder = new StringBuilder();
        	strBuilder.append("<listaProductos>");
        	
        	for(Product cust : customerList){
        		strBuilder.append(WebServiceUtils.convertBeanToXML(cust));
        	}
        	strBuilder.append("</listaProductos>");
        	
            response = new StringRepresentation(strBuilder.toString());
        	
        }
        catch (Exception e)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            setStatus(Status.SERVER_ERROR_INTERNAL);
            response = new StringRepresentation("ERROR : " + sw.toString());
        }
        return response;
    }
}
