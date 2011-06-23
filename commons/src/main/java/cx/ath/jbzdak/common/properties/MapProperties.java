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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Jacek Bzdak
 */
public class MapProperties extends AbstractExtendedProperties{


   private final Map<String, String> internal;

   public MapProperties(Map<String, String> internal) {
      this.internal = internal;
   }

   @Override
   protected String getPropertyInternal(String keyName) {
      return internal.get(keyName);
   }

   @Override
   protected String setPropertyInternal(String keyName, String value) {
      return internal.put(keyName, value);
   }

   @Override
   public Map<String, String> getAsMap() {
      return internal;
   }
}
