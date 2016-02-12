package com.inf749.secureLogin.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.inf749.secureLogin.models.UserAccount;

public class UserAccountDao {
	
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
}
