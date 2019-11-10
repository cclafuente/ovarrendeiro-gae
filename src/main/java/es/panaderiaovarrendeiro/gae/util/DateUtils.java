package es.panaderiaovarrendeiro.gae.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

	/**
	 * Retorna la fecha sin horas.
	 * 
	 * @param fechaIn
	 * @return fechaOut
	 */
	public static Date fechaSinHoras(Date fecha) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeInMillis(fecha.getTime());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * suma el numero de dias a la fecha, tanto positivos como negativos
	 */
	public static Date addDays(Date fecha, int dias) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(fecha.getTime());
		calendar.add(Calendar.DAY_OF_WEEK, dias);
		return calendar.getTime();
	}
}
