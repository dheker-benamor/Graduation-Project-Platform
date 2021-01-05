package iservices;

import javax.ejb.Remote;
import javax.mail.MessagingException;

@Remote
public interface MailSenderInterface {
	public void sendMessage(
			String Host,
			String user,
			String password,
			String port,
			String auth,
			String starttls,
			String recipient,
			String subject,
			String messageBody
	) throws MessagingException;

}
