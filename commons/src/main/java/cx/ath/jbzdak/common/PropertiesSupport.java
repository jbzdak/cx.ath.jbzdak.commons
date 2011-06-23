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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-11
 */
public class PropertiesSupport {

   private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesSupport.class);

   public static void addPropertyChangeListener(Object addTo, String propertyName, PropertyChangeListener listener){
      try {
         Method addPCL = addTo.getClass().getMethod("addPropertyChangeListener", String.class, PropertyChangeListener.class);
         addPCL.invoke(addTo, propertyName, listener);
      } catch (NoSuchMethodException e) {
         LOGGER.trace("Exception while trying to add PropertyChangeListener to its normal though", e);
      } catch (IllegalAccessException e) {
         LOGGER.trace("Exception while trying to add PropertyChangeListener to its normal though", e);
      } catch (InvocationTargetException e) {
         if (e.getTargetException() instanceof RuntimeException) {
            throw (RuntimeException) e.getTargetException();
         }
         throw new RuntimeException(e.getCause());
      }
   }

   public static void addPropertyChangeListener(Object addTo, PropertyChangeListener listener){
      try {
         Method addPCL = addTo.getClass().getMethod("addPropertyChangeListener", PropertyChangeListener.class);
         addPCL.invoke(addTo, listener);
      } catch (NoSuchMethodException e) {
         LOGGER.trace("Exception while trying to add PropertyChangeListener to its normal though", e);
      } catch (IllegalAccessException e) {
         LOGGER.trace("Exception while trying to add PropertyChangeListener to its normal though", e);
      } catch (InvocationTargetException e) {
         if (e.getTargetException() instanceof RuntimeException) {
            throw (RuntimeException) e.getTargetException();
         }
         throw new RuntimeException(e.getCause());
      }
   }
}
