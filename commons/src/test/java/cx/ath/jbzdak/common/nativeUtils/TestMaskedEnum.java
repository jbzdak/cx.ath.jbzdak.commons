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

   public static final MaskedFakeEnum<TestMaskedEnum> FAKE_ENUM = new MaskedFakeEnum<TestMaskedEnum>(TestMaskedEnum.class);

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
