package br.ifpb.monteiro.scream.util.mail;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.faces.bean.RequestScoped;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

/**
 * Classe responsável pelo envio de e-mails
 * 
 * @author Gilvonaldo
 *
 */
@RequestScoped
public class Mailer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String assunto;

	private String mensagem;
	
	private static final String remetente = "mentoring@unipe.br";

	private String destinatario;

	/**
	 * 
	 * @param assunto
	 * @param mensagem
	 * @param destinatario
	 */
	public void Mail(String assunto, String mensagem, String destinatario) {
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.destinatario = destinatario;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	/*
	 * para saber mais sobre a interface de programa\ufffd\ufffdo (API) JavaMail
	 * acesse https://java.net/projects/javamail/pages/Home
	 */
	public void enviar() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		
		
		Authenticator auth = new Authenticator() {
			public javax.mail.PasswordAuthentication getPasswordAuthentication() {
				
				String login, password;
				
				Properties prop = new Properties();
				
				//user = prop.getProperty("mail.username");
				login = prop.getProperty("prop.server.login");
				password = prop.getProperty("prop.server.password");

				
				return new javax.mail.PasswordAuthentication(login, password);// usar
																						// login
																						// e
																						// senha
																						// da
																						// sua
																						// conta
																						// Gmail                      
			}
		};

		Session session = Session.getDefaultInstance(props, auth);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(remetente, "Unip\u00ea - Mentoring"));
			msg.addRecipient(RecipientType.TO, new InternetAddress(destinatario));
			msg.setSubject(assunto);

			Multipart multipart = new MimeMultipart();
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mensagem, "text/html; charset=utf-8");
			htmlPart.setDisposition(BodyPart.INLINE);
			multipart.addBodyPart(htmlPart);
			msg.setContent(multipart);

			Transport.send(msg);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}