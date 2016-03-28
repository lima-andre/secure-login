package com.inf749.secureLogin.daos;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.models.BlackList;
import com.inf749.secureLogin.models.OneHourBlock;
import com.inf749.secureLogin.models.PaginatedList;

@Default
@SessionScoped
public class BlackListDao implements Serializable{
	
	@Inject
	private EntityManager entityManager;
    
	public void save(BlackList blackList) {
		entityManager.persist(blackList);
	}
	
	public OneHourBlock findById(Integer id) {
		return entityManager.find(OneHourBlock.class, id);
	}
	
	public void remove(BlackList blackList)
	{
		entityManager.remove(blackList);
	}

	public List<BlackList> getOneHourBlockByUser(Integer userId) {

		Query query = entityManager.createQuery("Select o from BlackList o where "
                + "userid= :userid");
        query.setParameter("userid", userId);
        
        try{ 
        	List<BlackList> block = (List<BlackList>) query.getResultList();
             return block; 
            }catch(Exception e){ 	          
               return null; 
            } 
	}
	
	public PaginatedList paginated(int page, int max)
	   {
	      return new PaginatorQueryHelper().list(entityManager, OneHourBlock.class, page, max);
	   }

}
