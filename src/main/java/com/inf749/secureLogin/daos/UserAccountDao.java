package com.inf749.secureLogin.daos;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.inf749.secureLogin.models.UserAccount;

@SessionScoped
public class UserAccountDao implements Serializable{
	
	@Inject
	private EntityManager entityManager;
    
	public List<UserAccount> listAll() {
		return entityManager.createQuery("select u from UserAccount u", UserAccount.class).getResultList();
    }

	public void save(UserAccount userAccount) {
		entityManager.persist(userAccount);		
	}
	
	public UserAccount findById(Integer id) {
		return entityManager.find(UserAccount.class, id);
	}
	
	public void remove(UserAccount userAccount)
	{
		entityManager.remove(userAccount);
	}

	public void update(UserAccount userAccount)
	{
		entityManager.merge(userAccount);
	}

	public UserAccount login(UserAccount user) {

		Query query = entityManager.createQuery("Select u from UserAccount u where "
                + "username= :username and userpwd = :userpwd");
        query.setParameter("username", user.getUserName());
        query.setParameter("userpwd", user.getUserPwd());
        
        try{ 
        	UserAccount userAccount = (UserAccount) query.getSingleResult();
            if (user.getUserName().equalsIgnoreCase(userAccount.getUserName())&&user.getUserPwd().equals(userAccount.getUserPwd())) { 
              return userAccount; 
            }  
            }catch(Exception e){ 	          
               return null; 
            }
        return null; 
	}
}
