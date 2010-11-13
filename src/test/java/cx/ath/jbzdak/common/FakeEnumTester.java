package cx.ath.jbzdak.common;

/**
 * Jacek Bzdak
 */
public class FakeEnumTester {

   public static final FakeEnumTester NO_SUBCLASS_ENUM = new FakeEnumTester();
   public static final FakeEnumTester SUBCLASS_ENUM = new FakeEnumTester(){

   };

   public static FakeEnumTester NOT_ENUM = new FakeEnumTester();


}
