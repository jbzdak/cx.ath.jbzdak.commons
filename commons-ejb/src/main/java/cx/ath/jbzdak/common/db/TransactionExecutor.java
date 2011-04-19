package cx.ath.jbzdak.common.db;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 2/28/11
 */
public interface TransactionExecutor {

   public void execute(EntityManager entityManager);

}
