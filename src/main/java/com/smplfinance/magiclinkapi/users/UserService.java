package com.smplfinance.magiclinkapi.users;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.smplfinance.magiclinkapi.usersessions.UserSession;

public interface UserService {

	ResponseEntity<UserSession> generateMagicLink(String email);

	ResponseEntity<Boolean> verifyMagicLink(String email, String token);

	ResponseEntity<User> addUser(User user);

	ResponseEntity<List<User>> listAllUsers();

}
