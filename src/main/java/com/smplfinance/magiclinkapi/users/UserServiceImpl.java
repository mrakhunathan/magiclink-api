package com.smplfinance.magiclinkapi.users;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smplfinance.magiclinkapi.mails.MailSenderService;
import com.smplfinance.magiclinkapi.usersessions.UserSession;
import com.smplfinance.magiclinkapi.usersessions.UserSessionRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserSessionRepository userSessionRepository;

	@Autowired
	MailSenderService mailSenderService;

	@Override
	public ResponseEntity<User> addUser(User user) {
		try {
			if (userRepository.findByEmailIgnoreCase(user.getEmail()) == null) {
				return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<User>> listAllUsers() {
		try {
			return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Boolean> verifyMagicLink(String email, String token) {
		try {
			User user = userRepository.findByEmailIgnoreCase(email);
			UserSession userSession = userSessionRepository.findByUserIdAndAuthToken(user.getId(), token);
			if (userSession != null) {
				return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<UserSession> generateMagicLink(String email) {
		try {
			User user = userRepository.findByEmailIgnoreCase(email);
			if (user != null) {
				UserSession userSession = userSessionRepository.findByUserId(user.getId());
				if (userSession != null) {
					userSession.setAuthToken(UUID.randomUUID().toString());
					userSession = userSessionRepository.save(userSession);
				} else {
					userSession = userSessionRepository
							.save(new UserSession(-1, UUID.randomUUID().toString(), new Date(), user.getId()));
				}
				mailSenderService.sendMagicLink(userSession);
				return new ResponseEntity<>(userSession, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
