package com.smplfinance.magiclinkapi.usersessions;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "`usersession`")
public class UserSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	String authToken;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false)
	Date created;

	long userId;
}
