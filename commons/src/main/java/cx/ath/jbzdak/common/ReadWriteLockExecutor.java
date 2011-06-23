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
