package es.panaderiaovarrendeiro.gae.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import es.panaderiaovarrendeiro.gae.model.ImageItem;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;


public class ImageController implements Controller {

	private static final Log log = LogFactory.getLog(ImageController.class);
	
	private ImageManager imageManager;
	
	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String imageId = request.getParameter("imageId");
		
		if (StringUtils.hasText(imageId)){
		
			ImageItem image = imageManager.findById(Long.valueOf(imageId));
		
			log.debug(" la imagen ha sido recuperada, el id es " + image.getId());
			log.debug(" la imagen tiene src " + image.getSource().getBytes().length);
			log.debug(" la imagen tiene tipo " + image.getImageType());
			log.debug(" la imagen pertenece al producto " + image.getProductId());
		
			try{
				byte[] imageSource = image.getSource().getBytes();
			
				if (imageSource.length > 0){
					response.setContentType(image.getImageType());
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentLength(image.getSource().getBytes().length);
					ServletOutputStream out = response.getOutputStream();
					out.write(imageSource);
					out.flush();
					out.close();
				}
			}catch(Exception e){
				log.error("Exception------>"+e);
			}
		}
		
		return null;
	}
	
}
