package es.panaderiaovarrendeiro.gae.web.listener;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.panaderiaovarrendeiro.gae.service.products.ProductBeanManager;
import es.panaderiaovarrendeiro.gae.web.beans.LabelValueBean;

public class StartupListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);
	
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		
		ProductBeanManager productBeanManager = (ProductBeanManager)ctx.getBean("productBeanManager");
		
		Collection<LabelValueBean> listaTemporadas = inicializarTemporadas();
		Collection<LabelValueBean> listaTipos = inicializarTipos();
		productBeanManager.inicializarMapaTemporadas(listaTemporadas);
		productBeanManager.inicializarMapaTipos(listaTipos);
		
		context.setAttribute("mapaTipos", productBeanManager.getMapaTipos());
		context.setAttribute("mapaTemporadas", productBeanManager.getMapaTipos());
		context.setAttribute("temporadaList", listaTemporadas);
		context.setAttribute("tipoList", listaTipos);
		context.setAttribute("mesesList", inicializarMeses());
		context.setAttribute("yearList", initYears());
		context.setAttribute("mapaProductos", productBeanManager.getMapaProductos());
		
		System.out.println(ctx.getDisplayName() + " initialized ...");		
	}
	
	private Collection<LabelValueBean> inicializarTemporadas(){
		
		Collection <LabelValueBean> temporadaList = new ArrayList<LabelValueBean>();
		
		temporadaList.add(new LabelValueBean(0L, "Todas"));
		temporadaList.add(new LabelValueBean(1L, "Navidade"));
		temporadaList.add(new LabelValueBean(2L, "Carnaval"));
		temporadaList.add(new LabelValueBean(3L, "Veran"));
		temporadaList.add(new LabelValueBean(4L, "Semana Santa"));
		
		return temporadaList;
	}
	
	private Collection<LabelValueBean> inicializarTipos(){
		
		Collection <LabelValueBean> tiposList = new ArrayList<LabelValueBean>();
		
		tiposList.add(new LabelValueBean(1L, "Panaderia"));
		tiposList.add(new LabelValueBean(2L, "Reposteria"));
		
		return tiposList;
	}
	
	private Collection<LabelValueBean> inicializarMeses(){
		Collection <LabelValueBean> mesesList = new ArrayList<LabelValueBean>();
		Long initIdx = 0L;
		mesesList.add(new LabelValueBean(initIdx, "Enero"));
		mesesList.add(new LabelValueBean(initIdx++, "Febrero"));
		mesesList.add(new LabelValueBean(initIdx++, "Marzo"));
		mesesList.add(new LabelValueBean(initIdx++, "Abril"));
		mesesList.add(new LabelValueBean(initIdx++, "Mayo"));
		mesesList.add(new LabelValueBean(initIdx++, "Junio"));
		mesesList.add(new LabelValueBean(initIdx++, "Julio"));
		mesesList.add(new LabelValueBean(initIdx++, "Agosto"));
		mesesList.add(new LabelValueBean(initIdx++, "Septiempre"));
		mesesList.add(new LabelValueBean(initIdx++, "Octubre"));
		mesesList.add(new LabelValueBean(initIdx++, "Noviembre"));
		mesesList.add(new LabelValueBean(initIdx++, "Diciembre"));
		return mesesList;
	}
	
	private Collection<LabelValueBean> initYears(){
		
		Collection<LabelValueBean> anyosList = new ArrayList<LabelValueBean>();
		
		
		
		return anyosList;
	}
	
}
