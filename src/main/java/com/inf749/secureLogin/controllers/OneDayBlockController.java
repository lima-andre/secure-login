package com.inf749.secureLogin.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.OneDayBlockDao;
import com.inf749.secureLogin.models.BlackList;
import com.inf749.secureLogin.models.OneDayBlock;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class OneDayBlockController {
	
	 @Inject
	 private OneDayBlockDao oneDayBlockDao;
	 private final Result result;
	 
	 public OneDayBlockController() {
		 this(null);
	 }
	 
	 @Inject
	 public OneDayBlockController(Result result) {
		this.result = result;
	}
	 
	 @Transactional
	 public void addOneDayBlock(OneDayBlock oneDayBlock, UserSession userSession) {
		 oneDayBlockDao.save(oneDayBlock);
		 
		 result.redirectTo(UserAccountController.class).loginForm();
	 }

	public void addOneHourBlock(OneDayBlock oneDayBlock,
			UserSession userSession, BlackList blackList) {
		
		 oneDayBlockDao.save(oneDayBlock);
		 
		 result.redirectTo(BlackListController.class).addBlackList(blackList, userSession);
		
	}
	
}
