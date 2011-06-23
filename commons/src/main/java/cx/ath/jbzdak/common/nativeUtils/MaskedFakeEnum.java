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

package cx.ath.jbzdak.common.nativeUtils;

import cx.ath.jbzdak.common.fakeEnum.FakeEnum;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Created by: Jacek Bzdak
 */
public class MaskedFakeEnum<T extends MaskedEnum> extends FakeEnum<T>{

//   private final NavigableMap<Integer, T> collectedMap;


   public Set<T> decomposite(int value){
      Set<T> tSet = new HashSet<T>();
      for (T t : values()) {
         Integer key = t.constant;
         if((value & key) == key){
            tSet.add(t);
         }
      }
      return tSet;
   }

   public MaskedFakeEnum(Class<T> clazz) {
      super(clazz);
   }




}
