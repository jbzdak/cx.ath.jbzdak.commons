package cx.ath.jbzdak.common.properties.transformer;

import cx.ath.jbzdak.common.fakeEnum.FakeEnum;

/**
 * @author Jacek Bzdak
 */
public class FakeEnumTransformer<T> implements Transformer<T>{

   private final FakeEnum<T> fakeEnum;

   public FakeEnumTransformer(FakeEnum<T> fakeEnum) {
      this.fakeEnum = fakeEnum;
   }

   public T transformReverse(String value) {
      return fakeEnum.valueOf(value);
   }

   public String transform(T value) {
      return fakeEnum.nameOf(value);
   }
}

