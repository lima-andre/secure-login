package com.inf749.secureLogin.daos;

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
	
	public void remove(ConnectionHistory connectionHistory)
	{
		entityManager.remove(connectionHistory);
	}

	public List<ConnectionHistory> getHistory(Integer userId) {

		Query query = entityManager.createQuery("Select c from ConnectionHistory c where "
                + "userid= :userid");
        query.setParameter("userid", userId);
        
        try{ 
        	List<ConnectionHistory> history = (List<ConnectionHistory>) query.getResultList();
             return history; 
            }catch(Exception e){ 	          
               return null; 
            } 
	}
	
	public PaginatedList paginated(int page, int max)
	   {
	      return new PaginatorQueryHelper().list(entityManager, ConnectionHistory.class, page, max);
	   }
}
