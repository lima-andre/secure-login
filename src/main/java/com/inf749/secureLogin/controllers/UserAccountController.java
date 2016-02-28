package com.inf749.secureLogin.controllers;

import java.sql.Timestamp;
import java.util.Calendar;
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
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.inf749.secureLogin.daos.ConnectionHistoryDao;
import com.inf749.secureLogin.daos.UserAccountDao;
import com.inf749.secureLogin.helpers.RealTimeHelper;
import com.inf749.secureLogin.models.ConnectionHistory;
import com.inf749.secureLogin.models.UserAccount;
import com.inf749.secureLogin.models.UserSession;

@Controller
@Path("/useraccount")
public class UserAccountController {
	
	 @Inject
	 private UserAccountDao dao;
	 @Inject
	 private ConnectionHistoryDao historyDao;
	 @Inject
	 private ConnectionHistoryController connectionHistory;
	 @Inject
	 private Validator validator;
	 private static UserSession userSession;
	 private final Result result;
	 private static long waitTimeBeforeResponse;
	 private static UserAccount staticUserAccount;
	 
	 private static Object monitor = new Object();
	 
	 private static UserAccountDao staticDao;
		
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
	public void login(final UserAccount userAccount) {
		long initialTime = System.currentTimeMillis(); 
		
		try {
			
			staticDao = this.dao;
			staticUserAccount = userAccount;
			
			 tLogin.run();
			
			 //loginWithTimeOut(dao, userAccount, initialTime);
			
			 if (userSession == null || userSession.getUser() == null || userSession.getUser().getUserId() == null) {
					
				long endTime = System.currentTimeMillis();
				
				long timePassed = RealTimeHelper.timePassed(initialTime, endTime);
				
				System.out.println("TIME ERROR : " + timePassed);  
				
				//Verify if need to run thread to wait until response time constraint
				if(timePassed < RealTimeHelper.MAX_RESPONSE_TIME){
					waitTimeBeforeResponse = (RealTimeHelper.MAX_RESPONSE_TIME - timePassed) * RealTimeHelper.MILLISECONDS;
				  tRealTimeResponse.run();
				}
				
				//Syncronize to wait until response time constraint
				 synchronized (tRealTimeResponse) {
					 result.include("badUserLogin", "Username or password is not valid");
					 result.redirectTo(UserAccountController.class).loginForm();
				 }
				}
				else {
				
				  Integer userId = userSession.getUser().getUserId();
					
				  ConnectionHistory history = new ConnectionHistory();
				  history.setIpConnection("1234"+userSession.getUser().getUserId());
				  history.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				  history.setUserId(userId);
				  history.setValidConnection(true);
				
				  connectionHistory.addHistory(history);
				  
				  long endTime = System.currentTimeMillis();
				  
				  long timePassed = RealTimeHelper.timePassed(initialTime, endTime);
				  
				  System.out.println("INTERMEDIATE TIME : " + timePassed);
				  
				  //Verify if need to run thread to wait until response time constraint
				  if(timePassed < RealTimeHelper.MAX_RESPONSE_TIME){
					  waitTimeBeforeResponse = (RealTimeHelper.MAX_RESPONSE_TIME - timePassed) * RealTimeHelper.MILLISECONDS;
					  tRealTimeResponse.run();
				  }
				  
				  //Syncronize to wait until response time constraint
				  synchronized (tRealTimeResponse) {
					  endTime = System.currentTimeMillis();
					  
					  System.out.println("FINAL TIME : " + RealTimeHelper.timePassed(initialTime, endTime));
					  
					  result.redirectTo(UserAccountController.class).view(userId, userSession);
				  }
			        
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			
			long endTime = System.currentTimeMillis();
			  
			System.out.println("TIME ERROR : " + RealTimeHelper.timePassed(initialTime, endTime));  
			
			validator.add(new SimpleMessage("bad.user.login", "Got Timeout!", Severity.ERROR));
			validator.onErrorRedirectTo(UserAccountController.class)
			  .loginForm();
			
		}
	   
	 }
	
  /*private static UserAccount loginWithTimeOut(final UserAccountDao dao, final UserAccount userAccount, final long startTime) throws Exception {
		
		UserAccount user = null;
		
		try {
			user = TimeoutForMethodsHelper.runWithTimeout(new Callable<UserAccount>() {
				@Override
				public UserAccount call() throws Exception {
					return dao.login(userAccount);
				}
				
			}, RealTimeHelper.MAX_RESPONSE_TIME, TimeUnit.SECONDS); 
	       
	    }
	    catch (TimeoutException e) {
	      log(startTime, "got timeout!");
	    }
		
		return user;
	}

	  private static void log(long startTime, String msg) {
	    long elapsedSeconds = (System.currentTimeMillis() - startTime);
	    System.out.format("%1$5sms [%2$16s] %3$s\n", elapsedSeconds, Thread.currentThread().getName(), msg);
	  }*/
	
	  public static Runnable tLogin = new Runnable() {
		    
	        public void run() {
	            try{
	            	userSession.login(staticDao.login(staticUserAccount));	
	            } catch (Exception e){}

	        }
	    };
	    
	    public static Runnable tRealTimeResponse = new Runnable() {
	        public void run() {
	            try{
	               Thread.sleep(waitTimeBeforeResponse);
	            } catch (Exception e){}

	        }
	    };
	    
	    public static Runnable tMonitorLogin = new Runnable() {
		    
	        public void run() {
	            try{
	            	
	            	/*while(){
	            	}*/
	            
	            } catch (Exception e){}

	        }
	    };
	    
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
	 public void view(Integer id, UserSession userSession) {
		 UserAccount user = dao.findById(id);
		 List<ConnectionHistory> history = historyDao.getHistory(id);
		 
		 result.include("user", user);
		 result.include("userSession", userSession);
		 result.include("history", history);
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
