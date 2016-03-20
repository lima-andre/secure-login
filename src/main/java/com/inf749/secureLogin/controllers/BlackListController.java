package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.BlackListDao;
import com.inf749.secureLogin.daos.OneHourBlockDao;
import com.inf749.secureLogin.models.BlackList;
import com.inf749.secureLogin.models.OneHourBlock;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class BlackListController {
	
	 @Inject
	 private BlackListDao blackListDao;
	 private final Result result;
	 
	 public BlackListController() {
		 this(null);
	 }
	 
	 @Inject
	 public BlackListController(Result result) {
		this.result = result;
	}
	 
	 @Transactional
	 public void addBlackList(BlackList blackList, UserSession userSession) {
		 blackListDao.save(blackList);
		 
		 result.redirectTo(UserAccountController.class).loginForm();
	 }
	 
	 @Get("/isUserInBlackList")
	 @Transactional
	 public Boolean isUserInBlackList(String userName, String ipConnection) {
		 
		 //return oneHourBlockDao.getNumberBlockToday(userName, ipConnection);
		 
		 return false;
	 }
}
