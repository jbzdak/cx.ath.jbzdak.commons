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

import edu.umd.cs.findbugs.annotations.NonNull;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Works kind of like java.beans.PropertyChangeSupport, with some exceptions: it allows you to reclieve
 * property changed events even if property did not change, method @{link removeListener(PropertyChangeListener} removes 
 * listener added via @{link #addListener (String, PropertyChangeListener}, and it ignores whole PropertyChangeListenerProxy
 * stuff.
 * 
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-28
 */
public class PropertyChangeSupport implements Serializable{

   private static final long serialVersionUID = 42L;

   private transient Set<PropertyChangeListener> listenerSet
           = new CopyOnWriteArraySet<PropertyChangeListener>();

   private final ReadWriteLock lock = new ReentrantReadWriteLock();

   private final ConcurrentHashMap<String, PropertyChangeSupport> namedListeners
           = new ConcurrentHashMap<String, PropertyChangeSupport>();

   private final Object source;

   private final boolean ignoreEventsWithSameValues;

   private static boolean equals(Object object1, Object object2) {
      if (object1 == object2) {
         return true;
      }
      if ((object1 == null) || (object2 == null)) {
         return false;
      }
      return object1.equals(object2);
   }

   public PropertyChangeSupport(PropertyChangeSupport copyFrom){
      source = copyFrom.source;
      ignoreEventsWithSameValues = copyFrom.ignoreEventsWithSameValues;
      try {
         copyFrom.lock.readLock().lock();
         listenerSet.addAll(copyFrom.listenerSet);
         for(Map.Entry<String, PropertyChangeSupport> entry : copyFrom.namedListeners.entrySet()){
            namedListeners.put(entry.getKey(), new PropertyChangeSupport(entry.getValue()));
         }
      } finally {
         copyFrom.lock.readLock().unlock();
      }

   }

   public PropertyChangeSupport(Object source) {
      this(source, true);
   }

   public PropertyChangeSupport(Object source, boolean ignoreEventsWithSameValues) {
      this.source = source;
      this.ignoreEventsWithSameValues = ignoreEventsWithSameValues;
   }

   public boolean addPropertyChangeListener(@NonNull PropertyChangeListener listener){
      try {
         lock.writeLock().lock();
         return listenerSet.add(listener);
      } finally {
         lock.writeLock().unlock();
      }

   }

   public boolean addPropertyChangeListener(@NonNull String propertyName, @NonNull PropertyChangeListener propertyChangeListener){
      try {
         lock.writeLock().lock();
         if (namedListeners.get(propertyName) == null) {
            namedListeners.put(propertyName, new PropertyChangeSupport(source));
         }
         return namedListeners.get(propertyName).addPropertyChangeListener(propertyChangeListener);
      } finally {
         lock.writeLock().unlock();
      }

   }

   public boolean removePropertyChangeListener(@NonNull PropertyChangeListener listener){
      try {
         lock.writeLock().lock();
         boolean changed;
         changed = listenerSet.remove(listener);
         for (PropertyChangeSupport set : namedListeners.values()) {
            changed |= set.removePropertyChangeListener(listener);
         }
         return changed;
      } finally {
         lock.writeLock().unlock();
      }
   }

   public boolean removePropertyChangeListener(@NonNull String propertyName, @NonNull PropertyChangeListener listener){
      try {
         lock.writeLock().lock();
         return namedListeners.get(propertyName).removePropertyChangeListener(listener);
      } finally {
         lock.writeLock().unlock();
      }
   }

   public List<PropertyChangeListener> getPropertyChangeListeners() {
      try {
         lock.readLock().lock();
         List<PropertyChangeListener> result = new ArrayList<PropertyChangeListener>();
         result.addAll(listenerSet);
         for (PropertyChangeSupport set : namedListeners.values()) {
            result.addAll(set.listenerSet);
         }
         return result;
      } finally {
         lock.readLock().unlock();
      }

   }

   public List<PropertyChangeListener> getPropertyChangeListeners(@NonNull String propertyName) {
      try {
         lock.readLock().lock();
         PropertyChangeSupport results = namedListeners.get(propertyName);
         if (results == null) {
            return new ArrayList<PropertyChangeListener>();
         }
         return results.getPropertyChangeListeners();
      } finally {
         lock.readLock().unlock();
      }
   }

   public boolean hasListeners(@NonNull String propertyName){
      try {
         lock.readLock().lock();
         PropertyChangeSupport results = namedListeners.get(propertyName);
         return results != null && !results.namedListeners.isEmpty();
      } finally {
         lock.readLock().unlock();
      }
   }

   public void firePropertyChange(PropertyChangeEvent evt){
      if(ignoreEventsWithSameValues && equals(evt.getNewValue(), evt.getOldValue())){
         return;
      }
      try {
         lock.readLock().lock();
         for (PropertyChangeListener listener : listenerSet) {
            listener.propertyChange(evt);
         }
         PropertyChangeSupport named = namedListeners.get(evt.getPropertyName());
         if (named != null) {
            named.firePropertyChange(evt);
         }
      } finally {
         lock.readLock().unlock();
      }

   }

   public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue){
      firePropertyChange(new IndexedPropertyChangeEvent(source, propertyName, oldValue, newValue, index));
   }

   public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
      firePropertyChange(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      try {
         lock.readLock().lock();
         s.defaultWriteObject();
         for (PropertyChangeListener listener : listenerSet) {
            if (listener instanceof Serializable) {
               Serializable o = (Serializable) listener;
               s.writeObject(o);
            }
         }
         s.writeObject(null);
      } finally {
         lock.readLock().unlock();
      }

   }

   private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
      s.defaultReadObject();
      listenerSet = new CopyOnWriteArraySet<PropertyChangeListener>();
      Object listenerOrNull;
      while (null != (listenerOrNull = s.readObject())) {
         addPropertyChangeListener((PropertyChangeListener)listenerOrNull);
      }
   }
}
