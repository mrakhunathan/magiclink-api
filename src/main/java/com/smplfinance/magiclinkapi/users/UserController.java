package com.smplfinance.magiclinkapi.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smplfinance.magiclinkapi.usersessions.UserSession;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "*")
@Log4j2
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping(value = "/")
	public String home() {
		return "Application Loaded";
	}

	@PostMapping(value = "api/v1/user")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		log.debug("Saving User..");
		if (user.getEmail() == null || user.getEmail().isBlank() || user.getFirstName() == null
				|| user.getFirstName().isBlank() || user.getLastName() == null || user.getLastName().isBlank()) {
			return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
		}
		user.setEmail(user.getEmail().toLowerCase());
		return userService.addUser(user);
	}

	@PostMapping(value = "api/v1/user-magic-link")
	public ResponseEntity<UserSession> generateMagicLink(@RequestBody String email) {
		log.debug("Generating Magic Link..");
		if (email == null || email.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
		}
		return userService.generateMagicLink(email);
	}

	@PostMapping(value = "api/v1/verify-magic-link/user/{email}/token/{token}")
	public ResponseEntity<Boolean> generateMagicLink(@PathVariable String email, @PathVariable String token) {
		log.debug("Verifying Magic Link..");
		if (email == null || email.isEmpty() || token == null || token.isBlank()) {
			return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
		}
		return userService.verifyMagicLink(email, token);
	}

	@GetMapping(value = "api/v1/users")
	public ResponseEntity<List<User>> listUsers() {
		log.debug("Listing all Users..");
		return userService.listAllUsers();
	}
}
