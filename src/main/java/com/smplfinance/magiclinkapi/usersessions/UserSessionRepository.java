package com.smplfinance.magiclinkapi.usersessions;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Serializable> {

	UserSession findByUserId(long userId);

	UserSession findByUserIdAndAuthToken(long email, String token);

}
