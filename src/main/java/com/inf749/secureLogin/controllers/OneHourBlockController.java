package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.OneHourBlockDao;
import com.inf749.secureLogin.models.OneHourBlock;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class OneHourBlockController {
	
	 @Inject
	 private OneHourBlockDao oneHourBlockDao;
	 private final Result result;
	 
	 public OneHourBlockController() {
		 this(null);
	 }
	 
	 @Inject
	 public OneHourBlockController(Result result) {
		this.result = result;
	}
	 
	 @Transactional
	 public void addOneHourBlock(OneHourBlock history, UserSession userSession) {
		 oneHourBlockDao.save(history);
		 
		 result.redirectTo(UserAccountController.class).loginForm();
	 }
	 
	 @Get("/getNumberBlockToday")
	 @Transactional
	 public Integer getNumberOfOneHourBlockedToday(String userName, String ipConnection) {
		 return oneHourBlockDao.getNumberBlockToday(userName, ipConnection);
	 }

	public Integer getOneHourBlockByUser(String userName) {
		return oneHourBlockDao.getOneHourBlockByUser(userName);		
	}
}
