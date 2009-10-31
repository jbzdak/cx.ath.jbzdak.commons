package cx.ath.jbzdak.common;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-28
 */
public abstract class ReadWriteLockExecutor {

   protected abstract void doWriteLock();

   protected abstract void doReadLock();

   public static void execute(ReadWriteLock lock, ReadWriteLockExecutor executor){
      lock.writeLock().lock();
      try{
         executor.doWriteLock();
      } catch (RuntimeException e){
         lock.writeLock().unlock();
         throw e;
      } catch (Error e){
         lock.writeLock().unlock();
         throw e;
      }
      try {
         lock.readLock().lock();
         lock.writeLock().unlock();
         executor.doReadLock();
      } finally {
         lock.readLock().unlock();
      }



   }
}
