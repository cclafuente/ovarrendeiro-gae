package es.panaderiaovarrendeiro.gae.web.products;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import es.panaderiaovarrendeiro.gae.model.ImageItem;
import es.panaderiaovarrendeiro.gae.model.Product;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;
import es.panaderiaovarrendeiro.gae.service.products.ProductManager;
import es.panaderiaovarrendeiro.gae.web.beans.ProductBean;

public class ProductDetailsController extends MultiActionController{

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

	public ModelAndView view(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long productId = Long.valueOf(request.getParameter("productId"));
		
		Product p = productManager.findById(productId);
		Collection<ImageItem> productImages = imageManager.findByProductId(productId);
		ProductBean pBean = new ProductBean();
		
		pBean.setProduct(p);
		
		pBean.setImageList(productImages);
		
		ModelAndView mav = new ModelAndView("product/productDetails");
		mav.addObject("pBean", pBean);
		
		
		return mav;
	}
	
	public ModelAndView editImages(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long productId = Long.valueOf(request.getParameter("productId"));
		
		Product p = productManager.findById(productId);
		Collection<ImageItem> productImages = imageManager.findByProductId(productId);
		ProductBean pBean = new ProductBean();
		pBean.setProduct(p);
		pBean.setImageList(productImages);
		ModelAndView mav = new ModelAndView("product/productImages");
		mav.addObject("pBean", pBean);
		return mav;
	}
	
	public ModelAndView removeImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long productId = Long.valueOf(request.getParameter("productId"));
		Long imageId = Long.valueOf(request.getParameter("imageId"));
		imageManager.remove(imageId);
		return new ModelAndView("redirect:/productDetails/editImages.do?productId=" + productId);
	}

}
