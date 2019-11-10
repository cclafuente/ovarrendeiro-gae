package es.panaderiaovarrendeiro.gae.service.pedidos;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.panaderiaovarrendeiro.gae.dao.PedidoDao;
import es.panaderiaovarrendeiro.gae.model.Pedido;

public class PedidoManagerImpl implements PedidoManager {

	private static final Log log = LogFactory.getLog(PedidoManagerImpl.class);
	
	private PedidoDao pedidoDao;
	
	public PedidoDao getPedidoDao() {
		return pedidoDao;
	}

	public void setPedidoDao(PedidoDao pedidoDao) {
		this.pedidoDao = pedidoDao;
	}

	public Collection<Pedido> findAll() {
		return pedidoDao.getAll();
	}

	public Pedido findById(Serializable id) {
		return pedidoDao.get(id);
	}

	public void removeById(Serializable id) {
		pedidoDao.remove(id);
	}

	public Serializable save(Pedido persistentObject) {
		pedidoDao.save(persistentObject);
		
        if (persistentObject.getCustomerId() == null){
    		Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            String msgBody = "";
            try {
            	   Message msg = new MimeMessage(session);
            	   msg.setFrom(new InternetAddress("info@panaderiaovarrendeiro.es", "Panaderia O Varrendeiro"));
            	   msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(persistentObject.getEmail(), "Estimado usuario de www.panaderiaovarrendeiro.es"));
            	   msg.addRecipient(Message.RecipientType.TO,
            				new InternetAddress("cclafuente@gmail.com", "Administrador de www.panaderiaovarrendeiro.es"));
            	   msg.setSubject(" Realizou vostede o pedido " + (persistentObject.getId()!= null ? persistentObject.getId().getId() : persistentObject.toString()));
            
            	   msgBody = msgBody + "\r\n" + msg.getSubject() + "\r\n Gracias por usar www.panaderiaovarrendeiro.es. \r\n";
            	   	msgBody = msgBody + "\r\n Para visualizar o seu pedido www.panaderiaovarrendeiro.es/pedidoForm.do?id=" + persistentObject.getId().getId();
            	   	msgBody = msgBody + "\r\n O valor do seu pedido e de " + persistentObject.getTotal() + "€.";
            	   	msgBody = msgBody + "\r\n ------------------------------------------------------------------------------------- ";
            	   	msgBody = msgBody + "\r\n Antes de imprimir este e-mail piense bien si es necesario hacerlo. " + 
            	   	"\r\n ------------------------------------------------------------------------------------- " +
            	   	"\r\n Este mensaje, y en su caso, cualquier fichero anexo al mismo, puede contener información confidencial o legalmente protegida LOPD 15/1999 de 13 de Diciembre), siendo para uso exclusivo del destinatario. No hay renuncia a la confidencialidad o secreto profesional por cualquier transmisión defectuosa o errónea, y queda expresamente prohibida su divulgación, copia o distribución a terceros sin la autorización expresa del remitente. Si ha recibido este mensaje por error o no desea recibir información comercial, se ruega lo notifique al remitente enviando un mensaje al correo electrónico, con el asunto (dar de baja o no deseo recibir información comercial)," +
            	   	"info@panaderiaovarrnediro.es y proceda inmediatamente al borrado del mensaje original y de todas sus copias. Gracias por su colaboración. ";
            	   	msg.setText(msgBody);
            	   	Transport.send(msg);
            	   	log.warn(" el mensaje fue enviado a " + persistentObject.getEmail());
            } catch (Exception e){
               log.error(" error enviando mail " + e);
            }
        }
        //Message mailMessage = MailService.Message("info@panaderiaovarrendeiro.es", persistentObject.getEmail(), "Gracias por usar www.panaderiaovarrendeiro.es", "realizou vostede o pedido número " + persistentObject.getId().getId()); 
		return persistentObject.getId();
	}
	
	
	/* (non-Javadoc)
	 * @see es.panaderiaovarrendeiro.gae.service.pedidos.PedidoManager#findByFilter(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	public Collection<Pedido> findByFilter(Long customerId, String email, Date fechaInicio, Date fechaFin){
		return pedidoDao.findByFilter(customerId, email, fechaInicio, fechaFin);
	}

}
