package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.ConnectionHistoryDao;
import com.inf749.secureLogin.models.BlackList;
import com.inf749.secureLogin.models.ConnectionHistory;
import com.inf749.secureLogin.models.OneDayBlock;
import com.inf749.secureLogin.models.OneHourBlock;
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
	 
	 @Get("/getNumberOfConnectionFailedToday")
	 @Transactional
	 public Integer getNumberOfConnectionFailedToday(String userName, String ipConnection) {
		 return historyDao.getNumberOfConnectionFailedToday(userName, ipConnection);
	 }
	 
	 @Get("/addHistoryAndOneHourBlock")
	 @Transactional 
	 public void addHistoryAndOneHourBlock(ConnectionHistory history,
			UserSession userSession, OneHourBlock oneHourBlock) {
		
		 historyDao.save(history);
		 
		 result.redirectTo(OneHourBlockController.class).addOneHourBlock(oneHourBlock, userSession);
		
	}
	 
	 @Get("/addHistoryNotConnected")
	 @Transactional
	 public void addHistoryNotConnected(ConnectionHistory history) {
		 historyDao.save(history);
		 
		 result.redirectTo(UserAccountController.class).loginForm();
	 }

	 public void addHistoryAndOneDayBlock(ConnectionHistory history, OneDayBlock oneDayBlock, BlackList blackList) {
			
		historyDao.save(history);
			 
		result.redirectTo(OneDayBlockController.class).addOneDayBlock(oneDayBlock, blackList);
			
	}

	 
}
