package com.inf749.secureLogin.daos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.enums.TREnums;
import com.inf749.secureLogin.models.OneDayBlock;
import com.inf749.secureLogin.models.OneHourBlock;

@Default
@SessionScoped
public class OneDayBlockDao implements Serializable{
	
	@Inject
	private EntityManager entityManager;
    
	public void save(OneDayBlock oneDayBlock) {
		entityManager.persist(oneDayBlock);
	}
	
	public OneHourBlock findById(Integer id) {
		return entityManager.find(OneHourBlock.class, id);
	}
	
	public void remove(OneDayBlock oneDayBlock)
	{
		entityManager.remove(oneDayBlock);
	}

	public Integer getOneDayBlockByUser(String userName) {
		
		Calendar dateIni = new GregorianCalendar();
		Calendar dateEnd = new GregorianCalendar();
		dateIni.add(Calendar.HOUR, -TREnums.ONEDAY.getValue());
		
		Query query = entityManager
				.createQuery("select count(*) from OneDayBlock where userName= :userName "
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
