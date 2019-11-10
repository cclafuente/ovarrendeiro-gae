package es.panaderiaovarrendeiro.gae.web.beans;

import java.io.Serializable;
import java.util.Collection;

import es.panaderiaovarrendeiro.gae.model.ImageItem;
import es.panaderiaovarrendeiro.gae.model.Product;

public class ProductBean implements Serializable{

	private static final long serialVersionUID = -310603257546595948L;

	private Product product;
	
	private String temporada;
	
	private String tipo;
	
	private Collection<ImageItem> imageList;
	
	public Product getProduct() {
		return product;
	}

	public String getTemporada() {
		return temporada;
	}

	public String getTipo() {
		return tipo;
	}

	public Collection<ImageItem> getImageList() {
		return imageList;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public void setTipo(String nombreTipo) {
		this.tipo = nombreTipo;
	}

	public void setImageList(Collection<ImageItem> imageList) {
		this.imageList = imageList;
	}

		
}
