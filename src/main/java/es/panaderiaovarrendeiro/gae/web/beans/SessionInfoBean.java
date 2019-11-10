package es.panaderiaovarrendeiro.gae.web.beans;

import java.io.Serializable;

public class SessionInfoBean implements Serializable{

	private static final long serialVersionUID = -7457387491429090221L;
	
	private Long activeMenu;
	private Long activeSubMenu;
	private Boolean admin;
	
	public Long getActiveMenu() {
		return activeMenu;
	}

	public void setActiveMenu(Long activeMenu) {
		this.activeMenu = activeMenu;
	}

	public Long getActiveSubMenu() {
		return activeSubMenu;
	}

	public void setActiveSubMenu(Long activeSubmenu) {
		this.activeSubMenu = activeSubmenu;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
}
