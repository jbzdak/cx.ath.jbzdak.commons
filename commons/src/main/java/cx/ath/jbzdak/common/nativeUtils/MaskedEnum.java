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
 * Class for enums that can be and'ed or or'ed to int constant
 *
 * Created by: Jacek Bzdak
 */
public abstract class MaskedEnum {

   protected final int constant;

   protected final boolean useInDecomposition;

   protected MaskedEnum(int constant) {
      this.constant = constant;
      this.useInDecomposition = true;
   }

   protected MaskedEnum(int constant, boolean useInDecomposition) {
      this.constant = constant;
      this.useInDecomposition = useInDecomposition;
   }

   protected MaskedEnum(MaskedEnum maskedEnum) {
      this.constant = maskedEnum.constant;
      this.useInDecomposition = false;
   }

   public int getConstant() {
      return constant;
   }


   public static int or(int base, MaskedEnum... enums){
      int result = base;
      for (MaskedEnum me : enums){
         result |= me.constant;
      }
      return result;
   }

   public static int and(int base, MaskedEnum... enums){
      int result = base;
      for (MaskedEnum me : enums){
         result &= me.constant;
      }
      return result;
   }

   public static int and(MaskedEnum... enums){
       return and(0, enums);
    }


   public static int switchOn(int base, MaskedEnum... enums){
      return or(base, enums);
   }

   public static int switchOff(int base, MaskedEnum... enums){
      int result = base;
      for (MaskedEnum me : enums){
         result &= ~me.constant;
      }
      return result;
   }

   public static int or(MaskedEnum... enums){
      return or(0, enums);
   }

   public static int switchOn(MaskedEnum... enums){
      return or(0, enums);
   }

   public static int or(int base, FakeEnum<MaskedEnum> fakeEnum, String... enums){
      int result = base;
      for (String me : enums){
         result |= fakeEnum.valueOf(me).constant;
      }
      return result;
   }

   public static int or(FakeEnum<MaskedEnum> fakeEnum, String... enums){
      return or(0, fakeEnum, enums);
   }

   public static int switchOn(int base, FakeEnum<MaskedEnum> fakeEnum, String... enums){
      return or(base, fakeEnum, enums);
   }

   public static int switchOn(FakeEnum<MaskedEnum> fakeEnum, String... enums){
      return switchOn(0, fakeEnum, enums);
   }

   public static int and(int base, FakeEnum<MaskedEnum> fakeEnum, String... enums){
      int result = base;
      for (String me : enums){
         result &= fakeEnum.valueOf(me).constant;
      }
      return result;
   }

   public static int switchOff(int base, FakeEnum<MaskedEnum> fakeEnum, String... enums){
      int result = base;
      for (String me : enums){
         result &= fakeEnum.valueOf(me).constant;
      }
      return result;
   }

   public static int or(int base, FakeEnum<? extends MaskedEnum> fakeEnum, Iterable<String> enums){
      int result = base;
      for (String me : enums){
         result |= fakeEnum.valueOf(me).constant;
      }
      return result;
   }

   public static int or(FakeEnum<? extends MaskedEnum> fakeEnum, Iterable<String> enums){
      return or(0, fakeEnum, enums);
   }

   public static int switchOn(int base, FakeEnum<? extends MaskedEnum> fakeEnum, Iterable<String> enums){
      return or(base, fakeEnum, enums);
   }


   public static int switchOn(FakeEnum<? extends MaskedEnum> fakeEnum, Iterable<String>  enums){
      return switchOn(0, fakeEnum, enums);
   }



}
