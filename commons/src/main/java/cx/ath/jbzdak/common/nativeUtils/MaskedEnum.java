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

   protected static <T extends MaskedEnum> NavigableMap<Integer, T> collect(FakeEnum<T> fakeEnum){
      NavigableMap<Integer, T> result = new TreeMap<Integer, T>();
      for (T t : fakeEnum.values()) {
         if(!t.useInDecomposition){
            continue;
         }
         if(result.containsKey(t.constant)){
            throw new InvalidParameterException("Constants in navigable map cant repeat!");
         }
         result.put(t.constant, t);
      }
      return result;
   }

   protected static <T extends MaskedEnum> Set<T> decomposite(NavigableMap<Integer, T> map, int value){
      Set<T> tSet = new HashSet<T>();
      for (Map.Entry<Integer, T> entry : map.entrySet()) {
         Integer key = entry.getKey();
         if((value & key) == key){
            tSet.add(entry.getValue());
         }
      }
      return tSet;
   }

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
