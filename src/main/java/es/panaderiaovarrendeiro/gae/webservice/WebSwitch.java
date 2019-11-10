package es.panaderiaovarrendeiro.gae.webservice;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class WebSwitch extends Application
{
    @Override
    public Restlet createInboundRoot()
    {
        Router router = new Router(getContext());
        router.attach("/web/{handle}", HelloWebServiceRestlet.class);
        router.attach("/customers/{handle}", CustomerResource.class);
        router.attach("/products/{handle}", ProductResource.class);
        router.attach("/facturas/{handle}", FacturaResource.class);
        return router;
    }
}
