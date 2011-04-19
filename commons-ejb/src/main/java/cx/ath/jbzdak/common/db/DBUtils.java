package cx.ath.jbzdak.common.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by: Jacek Bzdak
 */
public class DBUtils {

   public static <T> T executeInTransaction(EntityManagerFactory entityManagerFactory, ReturnableTransactionExecutor<T> executor) {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      T result;
      try{
         entityManager.getTransaction().begin();
         result = executor.execute(entityManager);
         entityManager.getTransaction().commit();
         return result;
      } catch (RuntimeException e){
         if(entityManager.getTransaction().isActive()){
            entityManager.getTransaction().rollback();
         }
         throw e;
      } finally {
         entityManager.close();
      }
   }


   public void executeInTransaction(EntityManagerFactory entityManagerFactory, TransactionExecutor transactionExecutor){
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try{
         entityManager.getTransaction().begin();
         transactionExecutor.execute(entityManager);
         entityManager.getTransaction().commit();
      } catch (RuntimeException e){
         if(entityManager.getTransaction().isActive()){
            entityManager.getTransaction().rollback();
         }

      } finally {
         entityManager.close();
      }
   }
}
