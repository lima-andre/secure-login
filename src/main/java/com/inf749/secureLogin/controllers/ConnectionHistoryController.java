package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.ConnectionHistoryDao;
import com.inf749.secureLogin.models.ConnectionHistory;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class ConnectionHistoryController {
	
	 @Inject
	 private ConnectionHistoryDao historyDao;
	 private final Result result;
	 
	 public ConnectionHistoryController() {
		 this(null);
	 }
	 
	 @Inject
	 public ConnectionHistoryController(Result result) {
		this.result = result;
	}
	 
	 @Get("/addHistoryConnected")
	 @Transactional
	 public void addHistoryConnected(ConnectionHistory history, UserSession userSession, Integer userId) {
		 historyDao.save(history);
		 
		 result.redirectTo(UserAccountController.class).view(userId, userSession);
	 }
	 
	 @Get("/addHistoryNotConnected")
	 @Transactional
	 public void addHistoryNotConnected(ConnectionHistory history, UserSession userSession) {
		 historyDao.save(history);
		 
		 result.redirectTo(UserAccountController.class).loginForm();
	 }
	 
}
