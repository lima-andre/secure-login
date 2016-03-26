package com.inf749.secureLogin.controllers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import com.inf749.secureLogin.models.BlackList;
import com.inf749.secureLogin.models.ConnectionHistory;
import com.inf749.secureLogin.models.OneDayBlock;
import com.inf749.secureLogin.models.OneHourBlock;
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
	 private Validator validator;
	 @Inject
	 private ConnectionHistoryController historyController;
	 @Inject
	 private OneHourBlockController oneHourBlockController;
	 
	 private static UserSession userSession;
	 private final Result result;
	 private static long waitTimeBeforeResponse;
	 private static UserAccount staticUserAccount;
	 
	 private static UserAccountDao staticDao;
	 private final HttpServletRequest request;
		
	 public UserAccountController() {
		this(null, null, null);
	 }
	 
	@Inject
	public UserAccountController(Result result, UserSession userSession, HttpServletRequest request) {
		this.result = result;
		this.userSession = userSession;
		this.request = request;
	}

	@Get("/login")
	public void loginForm() {

	}

	@Post("/login")
	public void login(final UserAccount userAccount) {

		long initialTime = System.currentTimeMillis(); 
		
		waitTimeBeforeResponse = RealTimeHelper.MAX_RESPONSE_TIME * RealTimeHelper.MILLISECONDS;
		//tRealTimeResponse.run();
		
		try {

			staticDao = this.dao;
			staticUserAccount = userAccount;

			//tLogin.run();
			
			TLogin tlogin = new TLogin();
			TRealTimeResponse tRealTimeResponse = new TRealTimeResponse();
			tlogin.run();
			tRealTimeResponse.run();
			
			 //Syncronize to wait until response time constraint
			 synchronized (tRealTimeResponse) {
				 if (userSession == null || userSession.getUser() == null || userSession.getUser().getUserId() == null) {
					 
					 ConnectionHistory history = new ConnectionHistory();
					 history.setIpConnection(request.getRemoteHost());
					 history.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					 history.setUserId(1);
					 history.setUserName(userAccount.getUserName());
					 history.setValidConnection(false);
						
					 long endTime = System.currentTimeMillis();
					
					 System.out.println("TIME ERROR : " + RealTimeHelper.timePassed(initialTime, endTime));  
					 
					 Integer nbBlockedConnections = oneHourBlockController.getNumberBlockToday(userAccount.getUserName(), request.getRemoteHost());
					 
					 Integer nbBadConnections = historyController.getNumberOfConnectionFailedToday(userAccount.getUserName(), request.getRemoteHost());
					 
					 history.setDuration(String.valueOf(RealTimeHelper.timePassed(initialTime, endTime)));
					 
					 if(nbBlockedConnections >= 3){
						 addInBlackListAndReturn(userAccount, history);
					 }					 
					 if(nbBadConnections >= 3){
						 addOneHourBlockAndReturn(userAccount, history);
						 
					 } else {
						 result.include("badUserLogin", "Username or password is not valid");
						 result.redirectTo(ConnectionHistoryController.class).addHistoryNotConnected(history, userSession); 
					 }
					 
					 
				 }
				 else {
					
					  Integer userId = userSession.getUser().getUserId();
						
					  ConnectionHistory history = new ConnectionHistory();
					  history.setIpConnection(request.getRemoteHost());
					  history.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					  history.setUserId(userId);
					  history.setValidConnection(true);
					  
					  
					  
					  long endTime = System.currentTimeMillis();
						  
					  System.out.println("FINAL TIME : " + RealTimeHelper.timePassed(initialTime, endTime));
						  
					  history.setDuration(String.valueOf(RealTimeHelper.timePassed(initialTime, endTime)));
					  
					  result.redirectTo(ConnectionHistoryController.class).addHistoryConnected(history, userSession, userId);
				        
				}
			 }

		} catch (Exception e) {
			e.printStackTrace();

			long endTime = System.currentTimeMillis();

			System.out.println("TIME ERROR : " + RealTimeHelper.timePassed(initialTime, endTime));

			validator.add(new SimpleMessage("bad.user.login", "Got Timeout!", Severity.ERROR));
			validator.onErrorRedirectTo(UserAccountController.class).loginForm();
		}

	}

	private void addInBlackListAndReturn(UserAccount userAccount,
			ConnectionHistory history) {
		
		 OneDayBlock oneDayBlock = new OneDayBlock();
		 oneDayBlock.setIpConnection(request.getRemoteHost());
		 oneDayBlock.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		 oneDayBlock.setUserId(1);
		 oneDayBlock.setUserName(userAccount.getUserName());
		 
		 BlackList blackList = new BlackList();
		 blackList.setIpConnection(request.getRemoteHost());
		 blackList.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		 blackList.setUserId(1);
		 blackList.setUserName(userAccount.getUserName());

		 result.include("badUserLogin", "This User is blocked for one day.");
		 result.redirectTo(ConnectionHistoryController.class).addHistoryAndOneDayBlock(history, userSession, oneDayBlock, blackList);
		
	}

	private void addOneHourBlockAndReturn(final UserAccount userAccount,
			ConnectionHistory history) {
		OneHourBlock oneHourBlock = new OneHourBlock();
		 oneHourBlock.setIpConnection(request.getRemoteHost());
		 oneHourBlock.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		 oneHourBlock.setUserId(1);
		 oneHourBlock.setUserName(userAccount.getUserName());

		 result.include("badUserLogin", "This User is blocked for an hour.");
		 result.redirectTo(ConnectionHistoryController.class).addHistoryAndOneHourBlock(history, userSession, oneHourBlock);
	}

	// Thread to make a new login
	/*public static Runnable tLogin = new Runnable() {
	    public void run() {
           try{
            	userSession.login(staticDao.login(staticUserAccount));	
          } catch (Exception e){}
        }
    };*/
	
	public class TLogin extends Thread{
		public void run () {
			userSession.login(staticDao.login(staticUserAccount));	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
	
	public class TRealTimeResponse extends Thread{
		public void run () {
			try {
				Thread.sleep(waitTimeBeforeResponse);				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		 }
	}
	
    // Thread to wait until response time constraint
	/*public static Runnable tRealTimeResponse = new Runnable() {
	    public void run() {
	       try{
	          Thread.sleep(waitTimeBeforeResponse);
	       } catch (Exception e){}
	    }
	};*/

	@Path("/logout")
	public void logout() {
		userSession.logout();
		result.redirectTo(UserAccountController.class).loginForm();
	}

	@Get("/form")
	public void form() {

	}

	@Get("/")
	public List<UserAccount> list() {
		return dao.listAll();
	}

	@Post("/add")
	@Transactional
	public void add(UserAccount userAccount) {
		dao.save(userAccount);
		 result.redirectTo(UserAccountController.class).loginForm();
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
	public void update(Integer id, UserAccount userAccount) {
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
