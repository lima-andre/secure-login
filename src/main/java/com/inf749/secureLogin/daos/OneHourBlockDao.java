package com.inf749.secureLogin.daos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.enums.TREnums;
import com.inf749.secureLogin.models.OneHourBlock;
import com.inf749.secureLogin.models.PaginatedList;

@Default
@SessionScoped
public class OneHourBlockDao implements Serializable{
	
	@Inject
	private EntityManager entityManager;
    
	public void save(OneHourBlock oneHourBlock) {
		entityManager.persist(oneHourBlock);
	}
	
	public OneHourBlock findById(Integer id) {
		return entityManager.find(OneHourBlock.class, id);
	}
	
	public void remove(OneHourBlock oneHourBlock)
	{
		entityManager.remove(oneHourBlock);
	}

	public Integer getOneHourBlockByUser(String userName) {
		
		entityManager.getEntityManagerFactory().getCache().evictAll();
		
		Calendar dateIni = new GregorianCalendar();
		Calendar dateEnd = new GregorianCalendar();
		dateIni.add(Calendar.HOUR, -TREnums.ONEHOUR.getValue());
		
		Query query = entityManager
				.createQuery("select count(*) from OneHourBlock where userName= :userName "
						+ "and timestampcreation between :dateIni and :dateEnd");
		query.setParameter("userName", userName);		
		query.setParameter("dateIni", dateIni);
		query.setParameter("dateEnd", dateEnd);

		try {
			Number connectionFailed = (Number) query.getSingleResult();
			return connectionFailed.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public PaginatedList paginated(int page, int max)
	   {
	      return new PaginatorQueryHelper().list(entityManager, OneHourBlock.class, page, max);
	   }

	public Integer getNumberBlockToday(String userName, String ipConnection) {
		
		Calendar dateIni = new GregorianCalendar();
		Calendar dateEnd = new GregorianCalendar();
		dateIni.add(Calendar.HOUR, -TREnums.ONEDAY.getValue());
		
		Query query = entityManager
				.createQuery("select count(*) from OneHourBlock where username= :userName "
						+ "and timestampcreation between :dateIni and :dateEnd");
		query.setParameter("userName", userName);		
		query.setParameter("dateIni", dateIni);
		query.setParameter("dateEnd", dateEnd);

		try {
			Number connectionFailed = (Number) query.getSingleResult();
			return connectionFailed.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
