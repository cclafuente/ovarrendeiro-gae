package es.panaderiaovarrendeiro.gae.web.utils;

import java.util.Locale;
import java.util.ResourceBundle;


public class ResourceBundleUtil {

	/**
	 * Recupera del fichero de idiomas correspondiente el valor de la clave
	 * solicitada.
	 * 
	 * @param key
	 * @return String
	 */
	public static String getMessage(String key) {
		try {
			Locale locale = new Locale("es", "ES");
			return ResourceBundle.getBundle("ApplicationResources", locale)
					.getString(key);
		} catch (Exception e) {
			return "??" + key + "??";
		}
	}
	
	
}
