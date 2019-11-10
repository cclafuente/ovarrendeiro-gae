package es.panaderiaovarrendeiro.gae.web.products;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import es.panaderiaovarrendeiro.gae.OVarrendeiroConstants;
import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.beans.ProductBean;
import es.panaderiaovarrendeiro.gae.web.utils.SessionInfoUtils;


public class ProductListController extends AbstractController {

	private Boolean adminList;
	private ProductBeanManager productBeanManager;
	private int tipoBusqueda;
	
	public Boolean getAdminList() {
		return adminList;
	}

	public void setAdminList(Boolean adminList) {
		this.adminList = adminList;
	}

	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
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
		
		Collection<ProductBean> productBeanList = null;
		
		if (adminList){
			SessionInfoUtils.updateMenu(request, OVarrendeiroConstants.menuAdmin, OVarrendeiroConstants.submenuProductos);
			productBeanList = productBeanManager.findAll();
			SessionInfoUtils.setAdmin(request);
		}else{
			Map<String, Object> conds = new HashMap<String, Object>();
			switch (getTipoBusqueda()){
				case 2:{
					SessionInfoUtils.updateMenu(request, 2L, 0L);
					//conds.put("tipo", 1L);
					productBeanList = productBeanManager.findAll();
					break;
					}
				case 3:{
					SessionInfoUtils.updateMenu(request, 3L, 0L);
					conds.put("tipo", 2L);
					productBeanList = productBeanManager.findByMap(conds);
					break;
				}
			}
		}
		
		ModelAndView mav = null;
		if (getAdminList() == true){
			mav = new ModelAndView("product/productAdminList");
		}else{
			mav = new ModelAndView("product/productList");
		}
		mav.addObject("productList", productBeanList);
		return mav;
	}
	

}
