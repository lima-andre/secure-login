package com.inf749.secureLogin.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;

import com.inf749.secureLogin.daos.UserAccountDao;
import com.inf749.secureLogin.models.Product;
import com.inf749.secureLogin.models.UserAccount;

@Controller
@Path("/useraccount")
public class UserAccountController {
	
	 @Inject
	 private UserAccountDao dao;	 
	 private final Result result;

	/**
	 * @deprecated CDI eyes only
	 */
	protected UserAccountController() {
		this(null);
	}
		
	@Inject
	public UserAccountController(Result result) {
		this.result = result;
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
	 public UserAccount view(Integer id) {
		 return dao.findById(id);
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
