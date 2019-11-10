package es.panaderiaovarrendeiro.gae.web.beans;

import java.io.Serializable;

public class LabelValueBean implements Serializable{

	private static final long serialVersionUID = -5376428340614931225L;

	public Long value;
	
	public String label;

	public Long getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LabelValueBean(Long value, String label) {
		super();
		this.value = value;
		this.label = label;
	}
	
}
