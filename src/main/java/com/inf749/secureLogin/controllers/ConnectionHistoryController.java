package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;

import com.inf749.secureLogin.daos.ConnectionHistoryDao;
import com.inf749.secureLogin.models.ConnectionHistory;

@Controller
@Path("/useraccount")
public class ConnectionHistoryController {
	
	 @Inject
	 private ConnectionHistoryDao historyDao;
		
	 public ConnectionHistoryController() {
		//
	 }
	 
	 @Transactional
	 public void addHistory(ConnectionHistory history) {
		 historyDao.save(history);
	 }
	 
}
