package org.xmlws.registrationservice.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.registrationservice.model.User;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	@Autowired
    private WebClient.Builder webClientBuilder;

	@Async
	public void sendRegistrationNotificaition(User user, HttpServletRequest request)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Sending email...");

		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, false, "utf-8");
		try {
			helper.setTo(user.getEmail());
			helper.setFrom(env.getProperty("spring.mail.username"));
			helper.setSubject("MegaTravel - Account Activation");
			String message = "Hello " + user.getUsername() + ",<br><br>"
					+ "Thank you for registering your MegaTravel account. To finally activate your account please "
					+ "<a href=\"" + makeActivationLink(user.getUsername(), request) + "\">click here.</a><br><br>"
					+ "Regards, <br><br>" + "<i>Your MegaTravel Team</i>";
			helper.setText(message, true);
			javaMailSender.send(mail);
		} catch (MessagingException e) {
			throw new MessagingException();
		}

		System.out.println("Email sent!");
	}

	private String makeActivationLink(String username, HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		url.append(this.getFullRequestURL(request));
		url.append("/verify?token=");
		String token = webClientBuilder.build()
					                   .get()
					                   .uri("http://authentication-service/authentication/token/" + username)
					                   .exchange()
					                   .block()
					                   .bodyToMono(String.class)
					                   .block();
		url.append(token);
		return url.toString();
	}

	private String getFullRequestURL(HttpServletRequest request) {
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if (serverPort != 80) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		return url.toString();
	}
}