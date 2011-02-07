package cx.ath.jbzdak.common;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 1/30/11
 */
public interface TransactionExecutor<T> {

   public T execute(EntityManager entityManager);
}
