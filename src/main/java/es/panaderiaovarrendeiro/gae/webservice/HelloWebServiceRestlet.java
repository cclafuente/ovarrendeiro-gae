package es.panaderiaovarrendeiro.gae.webservice;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloWebServiceRestlet extends ServerResource
{
    @Get
    public Representation helloWorld()
    {
        Representation response = null;
        try
        {
            String username = (String) getRequest().getAttributes().get("handle");
            if (null != username)
            {
                setStatus(Status.SUCCESS_OK);
                response = new StringRepresentation("Hi " + username.toLowerCase() + ", hello from the cloud.");
            }
            else
                throw new Exception("handle was null");
        }
        catch (Exception e)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            setStatus(Status.SERVER_ERROR_INTERNAL);
            response = new StringRepresentation("ERROR : " + sw.toString());
        }
        return response;
    }
}