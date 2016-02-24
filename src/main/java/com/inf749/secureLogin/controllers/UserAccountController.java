package com.inf749.secureLogin.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validator;

import com.inf749.secureLogin.daos.UserAccountDao;
import com.inf749.secureLogin.models.UserAccount;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class UserAccountController {
	
	 @Inject
	 private UserAccountDao dao;
	 @Inject
	 private Validator validator;
	 private final UserSession userSession;
	 private final Result result;
		
	 public UserAccountController() {
		this(null, null);
	 }
	 
	@Inject
	public UserAccountController(Result result, UserSession userSession) {
		this.result = result;
		this.userSession = userSession;
	}
	
	@Get("/login")
	public void loginForm() {
	    
	}
	
	@Post("/login")
	public void login(UserAccount userAccount) {
	  UserAccount authenticated = dao.login(userAccount);
	  if (authenticated == null) {
	    validator.add(
	        (Message) new ValidationMessage("Login e/ou senha inválidos",
	             "usuario.login"));
	   }
	   validator.onErrorUsePageOf(UserAccountController.class)
	       .loginForm();

	   userSession.login(authenticated);

	    result.redirectTo(UserAccountController.class).view(authenticated.getUserId(), userSession);
	 }
	
	@Path("/logout")
	public void logout() {
		userSession.logout();
	    result.redirectTo(UserAccountController.class).loginForm();
	}
	
	@Get("/form")
	public void form(){
		
	} 

	 @Get("/")
	 public List<UserAccount> list() {
		 return dao.listAll();
	 }

	 @Post("/add")
	 @Transactional
	 public void add(UserAccount userAccount) {
		 dao.save(userAccount);
		 result.redirectTo(UserAccountController.class).list();
	 }

	 @Get("/view/{id}")
	 public void view(Integer id, UserSession userSession2) {
		 UserAccount user = dao.findById(id);
		 result.include(user);
		// result.include(userSession2);
	 }

	 @Put("/update/{id}")
	 @Transactional
	 public void update(Integer id,UserAccount userAccount) {
		 userAccount.setUserId(id);
		 dao.update(userAccount);
		 result.redirectTo(UserAccountController.class).list();
	 }

	 @Delete("/remove/{id}")
	 @Transactional
	 public void remove(Integer id) {
		 UserAccount userAccount = dao.findById(id);
		 dao.remove(userAccount);
	     result.redirectTo(UserAccountController.class).list();
	 }
}
