package com.smplfinance.magiclinkapi.mails;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smplfinance.magiclinkapi.users.User;
import com.smplfinance.magiclinkapi.users.UserRepository;
import com.smplfinance.magiclinkapi.usersessions.UserSession;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MailSenderServiceImpl implements MailSenderService {
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	Environment environment;

	@Autowired
	UserRepository userRepository;

	@Override
	public void sendMagicLink(UserSession userSession) {
		CompletableFuture.runAsync(() -> {
			try {
				StringBuilder builder = new StringBuilder();
				User user = userRepository.findById(userSession.getUserId()).get();
				builder.append(environment.getProperty("application.mail.baseurl"))
						.append("/api/v1/verify-magic-link/user/").append(user.getEmail()).append("/token/")
						.append(userSession.getAuthToken());

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom(environment.getProperty("spring.mail.username"));
				msg.setTo(user.getEmail());
				msg.setSubject("Your SMPL Magic Link");
				msg.setText("Your Magic Link to login is " + builder.toString());
				javaMailSender.send(msg);
				log.info("Mail Sent...");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		});
	}
}
