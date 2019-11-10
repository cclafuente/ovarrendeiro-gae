package es.panaderiaovarrendeiro.gae.web.facturas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.panaderiaovarrendeiro.gae.model.FacturaLinea;

public class FacturaUtil {

	public List<FacturaLinea> crearLineas(Integer year, Integer month, Integer tipoFactura){
		List<FacturaLinea> lineasMes = new ArrayList<FacturaLinea>();
		
		Calendar date = Calendar.getInstance();
		date.set(year, month, 1, 0, 0, 0);
		int lineNumber = 1;
		while(date.get(Calendar.MONTH) == month){
			FacturaLinea fl = new FacturaLinea();
			fl.setNumeroLinea(lineNumber);
			lineNumber ++;
			lineasMes.add(fl);
			date.add(Calendar.DATE, 1);
		}
		return lineasMes;
	}
	
	
	
}
