package es.panaderiaovarrendeiro.gae.web.pedidos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.service.pedidos.PedidoManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;
import es.panaderiaovarrendeiro.gae.model.*;
import es.panaderiaovarrendeiro.gae.service.*;
import java.util.*;

public class PedidoProductListController extends AbstractController{

	protected final Log log = LogFactory.getLog(getClass());
	private String commandName = "pedido";
	private PedidoManager pedidoManager;
	private ProductBeanManager productBeanManager;
	private BaseManager<Customer> customerManager;
	private boolean ajax = false;
	
	public PedidoManager getPedidoManager() {
		return pedidoManager;
	}

	public void setPedidoManager(PedidoManager pedidoManager) {
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

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String viewName = commandName + "/" + commandName + "ProductList";
		if (ajax){
			viewName = viewName + "ajax";
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = null;
		Date fechaFin = null;
		String email = null;
		if (StringUtils.hasText(request.getParameter("email"))){
			email = request.getParameter("email");
			log.info(" filtramos por email " + email);
		}
		if (StringUtils.hasText(request.getParameter("fechaInicio"))){
			if (StringUtils.hasText(request.getParameter("fechaInicio"))){
				log.info(" parametero fechaInicio " + request.getParameter("fechaInicio"));
				fechaInicio = df.parse(request.getParameter("fechaInicio"));
				log.info(" fechaInicio " + fechaInicio);
			}
		}
		if (request.getParameter("fechaFin") != null){
			if (StringUtils.hasText(request.getParameter("fechaFin"))){
				log.info(" parametero fechaFin " + request.getParameter("fechaFin"));
				fechaFin = df.parse(request.getParameter("fechaFin"));
				log.info(" fechaFin " + fechaFin);
			}
		}
		
		Collection<Pedido> pedidoList = pedidoManager.findByFilter(null, email, fechaInicio, fechaFin); 
		Map<Long, Float> totalesProducto = new HashMap<Long, Float>();
		Map<Long, Map<Long, Float>> totalesPorPedido = new HashMap<Long, Map<Long, Float>>();
		for(Pedido pedido : pedidoList){
			Map<Long, Float> cantidadProducto = new HashMap<Long, Float>();
			for(PedidoLinea lineaPedido : pedido.getLineasPedido()){
				if (totalesProducto.containsKey(lineaPedido.getProductId())){
					Float cantidadAnterior = totalesProducto.get(lineaPedido.getProductId());
					totalesProducto.put(lineaPedido.getProductId(), cantidadAnterior + lineaPedido.getCantidad());
				}else{
					totalesProducto.put(lineaPedido.getProductId(), lineaPedido.getCantidad());
				}
				cantidadProducto.put(lineaPedido.getProductId(), lineaPedido.getCantidad());
			}
			totalesPorPedido.put(pedido.getId().getId(), cantidadProducto);
		}
		mav.addObject("totalesProducto", totalesProducto);
		mav.addObject("totalesPorPedido", totalesPorPedido);
		mav.addObject("pedidoList", pedidoList);
		mav.addObject("productMap", productBeanManager.getMapaProductos());
		mav.addObject("productList", productBeanManager.findAll());
		Map<Long, Customer> mapCustomers = new HashMap<Long, Customer>();
		for(Customer customer: customerManager.findAll()){
			mapCustomers.put(customer.getId(), customer);
		}
		mav.addObject("customerMap", mapCustomers);
		SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuPedidos);
		return mav;
	}
	
}
