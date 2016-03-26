package com.inf749.secureLogin.daos;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.models.ConnectionHistory;
import com.inf749.secureLogin.models.PaginatedList;

public class ConnectionHistoryDao {

	@Inject
	private EntityManager entityManager;

	public void save(ConnectionHistory connectionHistory) {
		entityManager.persist(connectionHistory);
	}

	public ConnectionHistory findById(Integer id) {
		return entityManager.find(ConnectionHistory.class, id);
	}

	public void remove(ConnectionHistory connectionHistory) {
		entityManager.remove(connectionHistory);
	}

	public List<ConnectionHistory> getHistory(Integer userId) {

		Query query = entityManager.createQuery("Select c from ConnectionHistory c where "
						+ "userid= :userid");
		query.setParameter("userid", userId);

		try {
			List<ConnectionHistory> history = (List<ConnectionHistory>) query.getResultList();
			return history;
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getNumberOfConnectionFailedToday(String userName, String ipConnection) {
		
		Calendar dateIni = new GregorianCalendar();
		dateIni.set(Calendar.HOUR, -1);
		/*dateIni.set(Calendar.HOUR, 0);
		dateIni.set(Calendar.MINUTE, 0);
		dateIni.set(Calendar.SECOND, 0);*/
		
		Calendar dateEnd = new GregorianCalendar();
		/*dateEnd.set(Calendar.HOUR, 23);
		dateEnd.set(Calendar.MINUTE, 59);
		dateEnd.set(Calendar.SECOND, 59);*/
		
		Query query = entityManager
				.createQuery("select count(*) from ConnectionHistory where validconnection = '0' and username= :userName "
						+ "and ipconnection = :ipConnection and timestampcreation between :dateIni and :dateEnd");
		query.setParameter("userName", userName);		
		query.setParameter("dateIni", dateIni);
		query.setParameter("dateEnd", dateEnd);
		query.setParameter("ipConnection", ipConnection);

		try {
			System.out.println("#####  [ConnectionHistoryDao] - DATE INI : " + dateIni.getTime());
			System.out.println("#####  [ConnectionHistoryDao] - DATE INI : " + dateEnd.getTime());
			
			Number connectionFailed = (Number) query.getSingleResult();
			return connectionFailed.intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public PaginatedList paginated(int page, int max) {
		return new PaginatorQueryHelper().list(entityManager,
				ConnectionHistory.class, page, max);
	}
}
