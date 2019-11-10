package es.panaderiaovarrendeiro.gae.webservice;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.betwixt.expression.Context;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.DefaultObjectStringConverter;

public class WebServiceUtils {

	public static String convertBeanToXML(Object obj) {
		String retString = null;
		StringWriter outputWriter = null;
		try {
			// Start by preparing the writer
			// We'll write to a string
			outputWriter = new StringWriter();

			// Create a BeanWriter which writes to our prepared stream
			BeanWriter beanWriter = new BeanWriter(outputWriter);

			// Configure betwixt
			// For more details see java docs or later in the main documentation
			beanWriter.getXMLIntrospector().getConfiguration()
					.setAttributesForPrimitives(true);
			beanWriter.getBindingConfiguration().setMapIDs(false);
			beanWriter.getBindingConfiguration().setObjectStringConverter(new
					CustomObjectStringConverter());
			beanWriter.setWriteEmptyElements(true);
			beanWriter.enablePrettyPrint();

			// If the base element is not passed in, Betwixt will guess
			beanWriter.write(obj.getClass().getSimpleName(), obj);

			outputWriter.close();
			
			retString = outputWriter.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return retString;
	}
	
	/**
	 * Converter betwixt.
	 * 
	 * @author Inditex.
	 * 
	 */
	private static class CustomObjectStringConverter extends
			DefaultObjectStringConverter {
		/** serial version. */
		private static final long serialVersionUID = -2004575868918275747L;
		/** formato para los tipos de fecha. */
		private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
		//private DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.apache.commons.betwixt.strategy.DefaultObjectStringConverter#objectToString(java.lang.Object,
		 *      java.lang.Class, java.lang.String,
		 *      org.apache.commons.betwixt.expression.Context)
		 */
		@SuppressWarnings("unchecked")
		public String objectToString(Object objeto, Class clase,
				String flavour, Context contexto) {
			if (objeto != null) {
				if (objeto instanceof Date) {
					return dateFormat.format((Date) objeto);
				} 
			}
			return super.objectToString(objeto, clase, flavour, contexto);
		}
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.apache.commons.betwixt.strategy.DefaultObjectStringConverter#stringToObject(java.lang.String,
		 *      java.lang.Class, java.lang.String,
		 *      org.apache.commons.betwixt.expression.Context)
		 */
		@SuppressWarnings("unchecked")
		public Object stringToObject(String dato, Class clase, String flavour,
				Context contexto) {
			try {
				if (Date.class.equals(clase)) {
					return dateFormat.parse(dato.trim());
				}
			} catch (Exception e) {
				System.err.println("Error de parseo parseando a " + clase
						+ "el string=" + dato);
				return null;
			}
			return super.stringToObject(dato, clase, flavour, contexto);
		}
	}
	
}
