package cx.ath.jbzdak.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 6, 2010
 */
public class LockUtils {

   public static void lockAndExecute(Lock lock, Action action){
      lock.lock();
      try{
         action.exec();
      } finally {
         lock.unlock();
      }
   }

   public static <T> T lockAndExecute(Lock lock, ReturnableAction<T> action){
      lock.lock();
      try{
         return action.exec();
      } finally {
         lock.unlock();
      }
   }



   public static boolean tryLockAndExecute(Lock lock, Action action){
      if(!lock.tryLock()){
         return false;
      }
      try{
         action.exec();
      } finally {
         lock.unlock();
      }
      return true;
   }

   public static boolean tryLockAndExecute(Lock lock, long time, TimeUnit timeUnit, Action action) throws InterruptedException{
      if(!lock.tryLock(time, timeUnit)){
         return false;
      }
      try{
         action.exec();
      } finally {
         lock.unlock();
      }
      return true;
   } 

}
