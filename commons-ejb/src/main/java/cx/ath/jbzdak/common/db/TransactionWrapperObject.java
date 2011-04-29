package cx.ath.jbzdak.common.db;

import javax.persistence.EntityManager;

/**
 * Created by: Jacek Bzdak
 */
class TransactionWrapperObject implements ReturnableTransactionExecutor<Void>{

   final TransactionExecutor internal;

   TransactionWrapperObject(TransactionExecutor internal) {
      this.internal = internal;
   }

   public Void execute(EntityManager entityManager) {
      internal.execute(entityManager);
      return null;
   }
}
