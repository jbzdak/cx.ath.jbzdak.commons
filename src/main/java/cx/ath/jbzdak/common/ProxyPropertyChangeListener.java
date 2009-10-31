package cx.ath.jbzdak.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-29
 */
public class ProxyPropertyChangeListener {

   public static PropertyChangeListener createProxy(Object proxyTo, String... watchedProperties){
      return new ProxyPropertyChangeListener1(proxyTo, watchedProperties);
   }

   private static class ProxyPropertyChangeListener1 implements PropertyChangeListener, Serializable{
      private final Object proxyTo;

      private final Method firePropertyChange;

      private final Set<String> watchedProperties;

      public ProxyPropertyChangeListener1(Object proxyTo, String... watchedProperties) {
         this.proxyTo = proxyTo;
         if(watchedProperties.length!=0){
            this.watchedProperties = new HashSet<String>(Arrays.asList(watchedProperties));
         }else{
            this.watchedProperties = null;
         }
         try {
            firePropertyChange = proxyTo.getClass().getMethod("firePropertyChange", PropertyChangeEvent.class);
         } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
         }
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if(watchedProperties== null || watchedProperties.contains(evt.getPropertyName())){
            try {
               firePropertyChange.invoke(proxyTo, evt);
            } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
               throw new RuntimeException(e);
            }
         }
      }
   }

}
