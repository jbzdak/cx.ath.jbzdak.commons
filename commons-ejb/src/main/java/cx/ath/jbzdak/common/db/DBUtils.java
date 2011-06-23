/*
 * Copyright for Jacek Bzdak 2011.
 *
 * This file is part of my commons library.
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * It is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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

   public static void executeInTransaction(EntityManagerFactory entityManagerFactory, TransactionExecutor transactionExecutor){
      executeInTransaction(entityManagerFactory, new TransactionWrapperObject(transactionExecutor));
   }

   public static <T> T executeInTransaction(EntityManager entityManager, ReturnableTransactionExecutor<T> executor) {
      boolean inTransaction = entityManager.getTransaction().isActive();
      if(!inTransaction){
         entityManager.getTransaction().begin();
      }
      try{
         T result = executor.execute(entityManager);
         if(!inTransaction){
            entityManager.getTransaction().commit();
         }
         return result;
      }catch (RuntimeException e){
         if(entityManager.getTransaction().isActive()){
            entityManager.getTransaction().rollback();
         }
         throw e;
      }
   }

   public static void executeInTransaction(EntityManager entityManager, TransactionExecutor executor) {
      executeInTransaction(entityManager, new TransactionWrapperObject(executor));
   }



}
