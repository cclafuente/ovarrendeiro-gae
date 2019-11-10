package es.panaderiaovarrendeiro.gae.web.products;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.google.appengine.api.datastore.Blob;

import es.panaderiaovarrendeiro.gae.model.ImageItem;
import es.panaderiaovarrendeiro.gae.service.images.ImageManager;


public class ProductImagesController extends AbstractController {

	private static final Log log = LogFactory.getLog(ProductImagesController.class);
	
	ImageManager imageManager;
	
	public ImageManager getImageManager() {
		return imageManager;
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String pidParam = request.getParameter("productId");
		
		log.info(" pidParam " + pidParam);
		
		Long productId = Long.valueOf(pidParam);
		
		log.info(" request.getContentType ******************* " + request.getContentType()  + " productId " + productId);
		
		try{
                
				
			
    			ServletFileUpload upload = new ServletFileUpload();
            	FileItemIterator iter = upload.getItemIterator(request);
            	List<ImageItem> images = new ArrayList<ImageItem>();
            	
                while (iter.hasNext()) {
                	FileItemStream item = iter.next();
                	if (!item.isFormField()){
                		InputStream stream = item.openStream();
                    	byte[] theBytes = null;
                		ImageItem imageItem = new ImageItem();
                		theBytes = IOUtils.toByteArray(stream);
                		imageItem.setSource(new Blob(theBytes));
                		imageItem.setImageType(item.getContentType());
                		imageItem.setProductId(productId);
                		log.debug("theBytes: " + theBytes.length + " stream.available: " + stream.available());
                		images.add(imageItem);
                	}
                    
            	}
                
                for(ImageItem image : images){
                	imageManager.saveImage(image);
                }
               
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
		return new ModelAndView("redirect:/productAdminList.do");
	}
	
}
