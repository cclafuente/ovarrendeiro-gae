package es.panaderiaovarrendeiro.gae.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class ImageItem implements PersistentObject {

	  private static final long serialVersionUID = 1L;

	  	@PrimaryKey
	    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	    private Long id;
	  	
	  	@Persistent
		private String imageType;
	    
	  	@Persistent
	  	private Blob source;
	  	
	  	@Persistent
	  	private Long productId;
		
	    public Long getId() {
			return id;
		}
	    
	    public void setId(Long id) {
			this.id = id;
		}

		public String getImageType() {
			return imageType;
		}

		public void setImageType(String imageType) {
			this.imageType = imageType;
		}

		public Blob getSource() {
			return source;
		}

		public void setSource(Blob source) {
			this.source = source;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		@Override
		public String toString() {
			return " id " + getId() + " type " + getImageType() + " length " + getSource().getBytes().length;
		}

		public boolean isNew() {
			return (getId() == null);
		}

		
		
}
