package cx.ath.jbzdak.common;

import java.beans.PropertyChangeListener;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 13, 2010
 */
public interface PropertySupported {

   public void addPropertyChangeListener(PropertyChangeListener listener) ;

   public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

   public boolean hasListeners(String propertyName);

   public void removePropertyChangeListener(PropertyChangeListener listener);

   public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
