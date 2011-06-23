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

package cx.ath.jbzdak.common;

import java.util.concurrent.TimeUnit;
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

         while(!shutDown){
            try {
               this.interrupt();
               shutdownCondition.await(100, TimeUnit.MILLISECONDS);
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
