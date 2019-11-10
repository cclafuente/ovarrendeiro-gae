package es.panaderiaovarrendeiro.gae.web.products;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import es.panaderiaovarrendeiro.gae.model.ImageItem;
import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductManager;
import es.panaderiaovarrendeiro.gae.web.beans.ProductBean;


public class ProductLyteController implements Controller {

	private ProductManager productManager;
	private ImageManager imageManager;
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			Long productId = Long.valueOf(request.getParameter("productId"));
			Product p = productManager.findById(productId);
			Collection<ImageItem> productImages = imageManager.findByProductId(productId);
			ProductBean pBean = new ProductBean();
			pBean.setProduct(p);
			pBean.setImageList(productImages);
			ModelAndView retMav = null;
			if (StringUtils.hasText(request.getParameter("gotoimages"))){
				retMav = new ModelAndView("product/productImages");
			}else{
				retMav = new ModelAndView("product/productLyte");
			}
			retMav.addObject("pBean", pBean);
			return retMav;
	}

}
