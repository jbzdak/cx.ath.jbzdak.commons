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
