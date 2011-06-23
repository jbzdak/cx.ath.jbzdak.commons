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

package cx.ath.jbzdak.common.properties;

import java.io.InvalidObjectException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by: Jacek Bzdak
 */
public class WrappedProperties  extends AbstractExtendedProperties{

   final AbstractExtendedProperties parent;

   final boolean readOnly;

   final String prefix;

   Map<String, String> prefixedMap;

   public WrappedProperties(AbstractExtendedProperties parent, boolean readOnly, String prefix) {
      this.parent = parent;
      this.readOnly = readOnly;
      if(! prefix.endsWith(".")){
         prefix = prefix + ".";
      }
      this.prefix = prefix;
   }

   public WrappedProperties(AbstractExtendedProperties parent, String prefix) {
      this(parent, true, prefix);
   }

   @Override
   protected String getPropertyInternal(String keyName) {
      return parent.getString(prefix + keyName);
   }

   @Override
   protected String setPropertyInternal(String keyName, String value) {
      if(readOnly){
         throw new UnsupportedOperationException("Can't change immutable Properties");
      }

      return parent.setPropertyInternal(prefix + keyName, value);
   }

   @Override
   public Map<String, String> getAsMap() {
      if (prefixedMap == null){
         synchronized (this){
            if(prefixedMap == null){
               Map<String, String> prefixedMap = new ConcurrentHashMap<String, String>();
               for (Map.Entry<String, String> entry : parent.getAsMap().entrySet()) {
                  String key = entry.getKey();
                  int length = prefix.length();
                  if(key.startsWith(prefix)){
                     prefixedMap.put(key.substring(length), entry.getValue());
                  }
                  this.prefixedMap = prefixedMap;
               }
            }
         }
      }
      return prefixedMap;
   }
}
