package com.smplfinance.magiclinkapi.users;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {
	User findByEmailIgnoreCase(String email);
}
