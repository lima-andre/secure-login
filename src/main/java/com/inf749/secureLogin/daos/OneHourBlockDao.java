package com.inf749.secureLogin.daos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	public List<OneHourBlock> getOneHourBlockByUser(Integer userId) {

		Query query = entityManager.createQuery("Select o from OneHourBlock o where "
                + "userid= :userid");
        query.setParameter("userid", userId);
        
        try{ 
        	List<OneHourBlock> block = (List<OneHourBlock>) query.getResultList();
             return block; 
            }catch(Exception e){ 	          
               return null; 
            } 
	}
	
	public PaginatedList paginated(int page, int max)
	   {
	      return new PaginatorQueryHelper().list(entityManager, OneHourBlock.class, page, max);
	   }

	public Integer getNumberBlockToday(String userName, String ipConnection) {
		
		Calendar dateIni = new GregorianCalendar();
		dateIni.set(Calendar.HOUR, -24);
		/*dateIni.set(Calendar.HOUR, 0);
		dateIni.set(Calendar.MINUTE, 0);
		dateIni.set(Calendar.SECOND, 0);*/
		
		Calendar dateEnd = new GregorianCalendar();
		/*dateEnd.set(Calendar.HOUR, 23);
		dateEnd.set(Calendar.MINUTE, 59);
		dateEnd.set(Calendar.SECOND, 59);*/
		
		Query query = entityManager
				.createQuery("select count(*) from OneHourBlock where username= :userName "
						+ "and ipconnection = :ipConnection and timestampcreation between :dateIni and :dateEnd");
		query.setParameter("userName", userName);		
		query.setParameter("dateIni", dateIni);
		query.setParameter("dateEnd", dateEnd);
		query.setParameter("ipConnection", ipConnection);

		try {
			
			System.out.println("#####  [OneHourBlockDao] - DATE INI : " + dateIni.getTime());
			System.out.println("#####  [OneHourBlockDao] - DATE INI : " + dateEnd.getTime());
			
			Number connectionFailed = (Number) query.getSingleResult();
			return connectionFailed.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
}
