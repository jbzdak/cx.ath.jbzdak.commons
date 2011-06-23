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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by: Jacek Bzdak
 */
public class ProcessUtils {

   private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

   public static void waitForProcessEnd(final Process process, final boolean redirectIO) throws IOException {
      Lock lock = new ReentrantLock();
      final Condition condition = lock.newCondition();
      while (true){

         LockUtils.lockAndExecute(lock, new Action() {
            public void exec() {
               try {
                  condition.await(500, TimeUnit.MILLISECONDS);
                  if(redirectIO){
                     copy(process.getErrorStream(), System.err);
                     copy(process.getInputStream(), System.err);
                     System.out.flush();
                  }else{
                     discard(process.getInputStream());
                     discard(process.getErrorStream());
                  }
               } catch (InterruptedException e) {
                  return;
               } catch (IOException e){
                  throw new RuntimeException(e);
               }
            }
         });

         try{
            if(process.exitValue()!=0){
               if(redirectIO){
                  copy(process.getErrorStream(), System.err);
                  copy(process.getInputStream(), System.out);
                  System.out.flush();
               }else{
                  discard(process.getInputStream());
                  discard(process.getErrorStream());
               }
               throw new IOException("Process exited abnormally");
            }
            return;
         }catch (IllegalThreadStateException e){
            if(redirectIO){
               copy(process.getErrorStream(), System.err);
               copy(process.getInputStream(), System.out);
            }else{
               discard(process.getInputStream());
               discard(process.getErrorStream());
            }
         }
      }
   }

   public static void discard(InputStream inputStream)throws IOException{
      while ((inputStream.read())!=-1);
   }

   public static long copyLarge(InputStream input, OutputStream output)
           throws IOException {
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      long count = 0;
      int n = 0;
      while (-1 != (n = input.read(buffer))) {
         output.write(buffer, 0, n);
         count += n;
      }
      return count;
   }

   public static int copy(InputStream input, OutputStream output) throws IOException {
      long count = copyLarge(input, output);
      if (count > Integer.MAX_VALUE) {
         return -1;
      }
      return (int) count;
   }

}
