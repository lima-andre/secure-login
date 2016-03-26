package com.inf749.secureLogin.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.inject.Inject;
import com.inf749.secureLogin.models.PaginatedList;

import com.inf749.secureLogin.models.Product;

public class ProductDao
{

   @Inject
   private EntityManager manager;

   public List<Product> all()
   {
      return manager.createQuery("select p from Product p", Product.class).getResultList();
   }

   public void save(Product product)
   {
      manager.persist(product);
   }

   public Product findById(Integer id)
   {
      return manager.find(Product.class, id);
   }

   public void remove(Product product)
   {
      manager.remove(product);
   }

   public void update(Product product)
   {
      manager.merge(product);
   }

   public PaginatedList paginated(int page, int max)
   {
      return new PaginatorQueryHelper().list(manager, Product.class, page, max);
   }
}
