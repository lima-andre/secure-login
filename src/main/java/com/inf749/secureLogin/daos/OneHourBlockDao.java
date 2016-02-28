package com.inf749.secureLogin.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.models.OneHourBlock;
import com.inf749.secureLogin.models.PaginatedList;

public class OneHourBlockDao {
	
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
}
