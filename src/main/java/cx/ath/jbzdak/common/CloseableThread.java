package cx.ath.jbzdak.common;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-01
 */
public abstract class CloseableThread extends Thread{

   private final ReentrantLock shutdownLock = new ReentrantLock();

   private final Condition shutdownCondition = shutdownLock.newCondition();

   volatile  boolean needsShutDown;

   volatile boolean shutDown;

   public void shutdown(){
      shutdownLock.lock();
      try{
         needsShutDown = true;
         interrupt();
         while(!shutDown){
            try {
               shutdownCondition.await();
            } catch (InterruptedException e) {
               //ignore
            }
         }
      }finally {
         shutdownLock.unlock();
      }
   }

   /**
    * This metod may be interrupted. 
    */
   protected abstract void executeOneIteration();

   protected void beforeShutDown() {}

   @Override
   public void run() {
      while(!needsShutDown){
         executeOneIteration();
      }
      beforeShutDown();
      shutDown = true;
      shutdownLock.lock();
      try {
         shutdownCondition.signalAll();
      } finally {
         shutdownLock.unlock();
      }
   }
}
