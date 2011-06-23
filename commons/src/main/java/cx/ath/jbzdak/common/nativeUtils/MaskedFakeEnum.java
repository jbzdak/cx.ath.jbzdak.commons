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
