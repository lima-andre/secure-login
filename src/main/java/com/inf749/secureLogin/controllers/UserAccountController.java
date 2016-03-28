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

import com.inf749.secureLogin.daos.ConnectionHistoryDao;
import com.inf749.secureLogin.daos.UserAccountDao;
import com.inf749.secureLogin.enums.TREnums;
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
	private ConnectionHistoryController historyController;
	@Inject
	private OneHourBlockController oneHourBlockController;
	@Inject
	private OneDayBlockController oneDayBlockController;

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
	public UserAccountController(Result result, UserSession userSession,
			HttpServletRequest request) {
		this.result = result;
		this.userSession = userSession;
		this.request = request;
	}

	@Get("/login")
	public void loginForm() {

	}

	@Post("/login")
	public void login(final UserAccount userAccount) {
		
		TRealTimeResponse tRealTimeResponse = new TRealTimeResponse();
		long initialTime = System.currentTimeMillis();

		Integer userId = this.dao.getUserIdByUserName(userAccount);

		Boolean isBlockedForOneDay = RealTimeHelper.isOneDayBlocked(oneDayBlockController.getOneDayBlockByUser(userAccount.getUserName()));
		Boolean isBlockedForOneHour = RealTimeHelper.isOneHourBlocked(oneHourBlockController.getOneHourBlockByUser(userAccount.getUserName()));
		
		if (isBlockedForOneDay) {
			verifyTimeToWaitBeforeReturn(tRealTimeResponse, initialTime);
			
			result.include("badUserLogin", "This User is blocked for one day.");
			result.redirectTo(UserAccountController.class).loginForm();
		} else if (isBlockedForOneHour) {
			verifyTimeToWaitBeforeReturn(tRealTimeResponse, initialTime);
			
			result.include("badUserLogin", "This User is blocked for one hour.");
			result.redirectTo(UserAccountController.class).loginForm();
		} else {

			staticDao = this.dao;
			staticUserAccount = userAccount;

			TLogin tlogin = new TLogin();

			tlogin.run();

			if (userSession == null || userSession.getUser() == null || userSession.getUser().getUserId() == null) {

				ConnectionHistory history = createHistoryObjectForBadConnection(userAccount, userId);

				Integer nbOneHourBlockedConnections = oneHourBlockController.getNumberOfOneHourBlockedToday(userAccount.getUserName(), request.getRemoteHost());

				Integer nbBadConnections = historyController.getNumberOfConnectionFailedToday(userAccount.getUserName(), request.getRemoteHost());
				
				verifyTimeToWaitBeforeReturn(tRealTimeResponse, initialTime);
				
				long endTime = System.currentTimeMillis();
				history.setDuration(String.valueOf(RealTimeHelper.timePassed(initialTime, endTime)));
				
				if (nbOneHourBlockedConnections == 3 ) {
					addOneDayBlockAndReturn(history);
				} else if (nbBadConnections >= 2 && (nbBadConnections + 1) % 3 == 0) {
					addOneHourBlockAndReturn(history);

				} else {
					result.include("badUserLogin","Username or password is not valid");
					result.redirectTo(ConnectionHistoryController.class).addHistoryNotConnected(history);
				}

			} else {

				ConnectionHistory history = createHistoryObjectForCorrectConnection(userAccount, userId);
				
				verifyTimeToWaitBeforeReturn(tRealTimeResponse, initialTime);
				
				long endTime = System.currentTimeMillis();
					
				history.setDuration(String.valueOf(RealTimeHelper.timePassed(initialTime, endTime)));
					
				result.redirectTo(ConnectionHistoryController.class).addHistoryConnected(history, userSession, userId);
			}
		}

	}
	
	private void verifyTimeToWaitBeforeReturn(TRealTimeResponse tRealTimeResponse, long initialTime) {
		long endTime = System.currentTimeMillis();
		
		waitTimeBeforeResponse = TREnums.RESPONSETIME.getValue();
		
		if(RealTimeHelper.timePassed(initialTime, endTime) < waitTimeBeforeResponse){
			waitTimeBeforeResponse += (waitTimeBeforeResponse - RealTimeHelper.timePassed(initialTime, endTime)) * TREnums.MILLISECONDS.getValue();
			
			tRealTimeResponse.run();			
		}		
	}

	private ConnectionHistory createHistoryObjectForCorrectConnection(final UserAccount userAccount, final Integer userId) {

		ConnectionHistory history = new ConnectionHistory();
		history.setIpConnection(request.getRemoteHost());
		history.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		history.setUserId(userId);
		history.setUserName(userAccount.getUserName());
		history.setValidConnection(true);

		return history;
	}

	private ConnectionHistory createHistoryObjectForBadConnection(final UserAccount userAccount, final Integer userId) {

		ConnectionHistory history = new ConnectionHistory();
		history.setIpConnection(request.getRemoteHost());
		history.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		history.setUserId(userId);
		history.setUserName(userAccount.getUserName());
		history.setValidConnection(false);

		return history;
	}

	private void addOneDayBlockAndReturn(final ConnectionHistory history) {

		OneDayBlock oneDayBlock = new OneDayBlock();
		oneDayBlock.setIpConnection(request.getRemoteHost());
		oneDayBlock.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		oneDayBlock.setUserId(history.getUserId());
		oneDayBlock.setUserName(history.getUserName());
		
		BlackList blackList = new BlackList();
		blackList.setIpConnection(request.getRemoteHost());
		blackList.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		blackList.setUserId(history.getUserId());
		blackList.setUserName(history.getUserName());

		 result.include("badUserLogin", "This User will be blocked for one day.");
		 result.redirectTo(ConnectionHistoryController.class).addHistoryAndOneDayBlock(history, oneDayBlock, blackList);

	}

	private void addOneHourBlockAndReturn(final ConnectionHistory history) {
		OneHourBlock oneHourBlock = new OneHourBlock();
		oneHourBlock.setIpConnection(request.getRemoteHost());
		oneHourBlock.setTimestampCreation(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		oneHourBlock.setUserId(history.getUserId());
		oneHourBlock.setUserName(history.getUserName());

		result.include("badUserLogin", "This User will be blocked for one hour.");
		result.redirectTo(ConnectionHistoryController.class).addHistoryAndOneHourBlock(history, userSession, oneHourBlock);
	}

	public class TLogin extends Thread {
		public void run() {
			userSession.login(staticDao.login(staticUserAccount));
		}
	}

	public class TRealTimeResponse extends Thread {
		public void run() {
			try {
				Thread.sleep(waitTimeBeforeResponse);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

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
