package cx.ath.jbzdak.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by: Jacek Bzdak
 */
public class CleanupOnClose {

   private static final Logger LOGGER = LoggerFactory.getLogger(CleanupOnClose.class);

   private static final Map<String, CleanupOnClose> ON_CLOSE_MAP =
           new ConcurrentHashMap<String, CleanupOnClose>();

   volatile boolean isDisposing;

   Set<Disposable> disposeables
           = new CopyOnWriteArraySet<Disposable>();

   public static CleanupOnClose getByName(String name){
      CleanupOnClose onClose = ON_CLOSE_MAP.get(name);
      if(onClose == null){
         onClose = createByName(name);
      }
      return onClose;
   }

   private static synchronized CleanupOnClose createByName(String name){
      CleanupOnClose onClose = ON_CLOSE_MAP.get(name);
      if (onClose == null){
         onClose = new CleanupOnClose();
         ON_CLOSE_MAP.put(name, onClose);
      }
      return onClose;
   }

   private CleanupOnClose() {
      Runtime.getRuntime().addShutdownHook(new Thread(){
         @Override
         public void run() {
            isDisposing = true;
            for (Disposable d : disposeables){
               try {
                  d.dispose();
               } catch (Exception e) {
                  LOGGER.error("Exception while disposing" + d, e);
               }
            }
         }
      });
   }

   public void register(Disposable disposable){
      if(isDisposing){
         throw new RuntimeException("This instance is disposing");
      }
      disposeables.add(disposable);
   }

   public void unregister(Disposable disposable){
      if(isDisposing){
         return;
      }
      disposeables.remove(disposable);
   }





}
