package com.smplfinance.magiclinkapi.mails;

import com.smplfinance.magiclinkapi.usersessions.UserSession;

public interface MailSenderService {

	void sendMagicLink(UserSession userSession);

}
