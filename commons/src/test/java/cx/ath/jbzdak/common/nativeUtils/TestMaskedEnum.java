package cx.ath.jbzdak.common.nativeUtils;

import cx.ath.jbzdak.common.fakeEnum.FakeEnum;
import junit.framework.Test;

import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by: Jacek Bzdak
 */
public class TestMaskedEnum extends MaskedEnum{

   public static final TestMaskedEnum A0 = new TestMaskedEnum(1);
   public static final TestMaskedEnum A1 = new TestMaskedEnum(2);
   public static final TestMaskedEnum A2 = new TestMaskedEnum(4);
   public static final TestMaskedEnum MASK = new TestMaskedEnum(3);

   public static final FakeEnum<TestMaskedEnum> FAKE_ENUM = new FakeEnum<TestMaskedEnum>(TestMaskedEnum.class);

   public static final NavigableMap<Integer, TestMaskedEnum> MAP = TestMaskedEnum.collect(FAKE_ENUM);

   @Override
   public String toString() {
      return FAKE_ENUM.genToString(this);
   }

   public TestMaskedEnum(int constant) {
      super(constant);
   }

   public TestMaskedEnum(int constant, boolean useInDecomposition) {
      super(constant, useInDecomposition);
   }

   public TestMaskedEnum(MaskedEnum maskedEnum) {
      super(maskedEnum);
   }
}
