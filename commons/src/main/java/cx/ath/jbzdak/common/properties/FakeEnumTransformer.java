package cx.ath.jbzdak.common.properties;

import cx.ath.jbzdak.common.collections.Transformer;
import cx.ath.jbzdak.common.fakeEnum.FakeEnum;

/**
 * @author Jacek Bzdak
 */
public class FakeEnumTransformer<T> implements Transformer<T,String>{

   private final FakeEnum<T> fakeEnum;

   public FakeEnumTransformer(FakeEnum<T> fakeEnum) {
      this.fakeEnum = fakeEnum;
   }

   public T transform(String value) {
      return fakeEnum.valueOf(value);
   }
}

