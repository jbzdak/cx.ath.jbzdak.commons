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

   public static void waitForProcessEnd(Process process, boolean redirectIO) throws IOException {
      Lock lock = new ReentrantLock();
      final Condition condition = lock.newCondition();
      while (true){

         LockUtils.lockAndExecute(lock, new Action() {
            public void exec() {
               try {
                  condition.await(500, TimeUnit.MILLISECONDS);
               } catch (InterruptedException e) {
                  return;
               }
            }
         });

         try{
            if(process.exitValue()!=0){
               if(redirectIO){
                  copy(process.getErrorStream(), System.err);
                  copy(process.getInputStream(), System.out);
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
