package es.panaderiaovarrendeiro.gae.webservice;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.panaderiaovarrendeiro.gae.model.Factura;
import es.panaderiaovarrendeiro.gae.model.FacturaLinea;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManagerImpl;
import es.panaderiaovarrendeiro.gae.service.facturas.FacturaManager;

public class FacturaResource extends ServerResource {

	private static final Log log = LogFactory.getLog(FacturaManagerImpl.class);
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Get
    public Representation findAll()
    {
        Representation response = null;
        try
        {
        	Map<String, Object> atributos = getContext().getAttributes();
        	
        	ServletContext context = (ServletContext)atributos.get("org.restlet.ext.servlet.ServletContext");
        	
        	WebApplicationContext springContext = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        	
        	FacturaManager facturaManager = (FacturaManager)springContext.getBean("facturaManager");
        	
        	Collection<Factura> facturaList = facturaManager.findAll();
        	
        	StringBuilder strBuilder = new StringBuilder();
        	strBuilder.append("<listaFacturas>");
        	
        	for(Factura cust : facturaList){
        		strBuilder.append(WebServiceUtils.convertBeanToXML(cust));
        	}
        	strBuilder.append("</listaFacturas>");
        	
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
	
	@Post
	public Representation process(Representation entity)
	{
		Representation response = null;
		
		try{
			InputStream is = entity.getStream();
			
			/*Reader reader = new InputStreamReader(is);*/
			Factura factura = new Factura();
			List<FacturaLinea> lineas = new ArrayList<FacturaLinea>();
			factura.setLineasFactura(lineas);
			
			log.info("entrada: " + is.toString() + " available size " + is.available() + " texto " + entity.getText());
			
			// 	Create a builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			// Create the builder and parse the file
			Document doc = factory.newDocumentBuilder().parse(is);
			
			NodeList nl = doc.getChildNodes();
			
			for(int i=0; i<nl.getLength();i++){
				Node nd = nl.item(i);
				NamedNodeMap nnm = nd.getAttributes();
				
				String customerId = nnm.getNamedItem("customerId").getNodeValue();
				factura.setCustomerId(Long.valueOf(customerId));
				String total = nnm.getNamedItem("total").getNodeValue();
				factura.setTotal(Float.valueOf(total));
				String fechaFmt = nnm.getNamedItem("fecha").getNodeValue();
				Date dateFmt = df.parse(fechaFmt);
				factura.setFecha(dateFmt);
				factura.setPagado(false);
				
				Node nodoFacturaLineas = nd.getFirstChild();
				int numLinea = 0;
				NodeList facturaLs = nodoFacturaLineas.getChildNodes();
				if (facturaLs.getLength() > 0){
					for(int zz=0; zz<facturaLs.getLength(); zz++){
						Node child3 = facturaLs.item(zz);
						NamedNodeMap nnmp2 = child3.getAttributes();
						String totalLinea = nnmp2.getNamedItem("total").getNodeValue();
						String productId = nnmp2.getNamedItem("productId").getNodeValue();
						String cantidad = nnmp2.getNamedItem("cantidad").getNodeValue();
						String iva = nnmp2.getNamedItem("iva").getNodeValue();
						String precio = nnmp2.getNamedItem("precio").getNodeValue();
						FacturaLinea fl = new FacturaLinea();
						fl.setPrecio(Float.valueOf(precio));
						fl.setCantidad(Float.valueOf(cantidad));
						fl.setProductId(Long.valueOf(productId));
						fl.setTotal(Float.valueOf(totalLinea));
						fl.setIva(Integer.valueOf(iva));
						fl.setNumeroLinea(++numLinea);
						fl.setFactura(factura);
						lineas.add(fl);
					}
				}
			}
			
			log.error(" factura a guardar -> " + factura.toString());
			FacturaManager facturaManager = getContextManager();
			
			factura.setLastModifiedBy("RESTLET");
			
			facturaManager.save(factura);
			
			
			response = new StringRepresentation("" + factura.getId().getId() + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}
	
	
	private FacturaManager getContextManager(){
		Map<String, Object> atributos = getContext().getAttributes();
    	ServletContext context = (ServletContext)atributos.get("org.restlet.ext.servlet.ServletContext");
    	WebApplicationContext springContext = (WebApplicationContext)context.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    	FacturaManager facturaManager = (FacturaManager)springContext.getBean("facturaManager");
    	return facturaManager;
	}
}
