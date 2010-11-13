package cx.ath.jbzdak.common;

import cx.ath.jbzdak.common.fakeEnum.FakeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Jacek Bzdak
 */
public class FakeEnumTest {


   FakeEnum<FakeEnumTester> tested;

   @Before
   public void before(){
      tested = new FakeEnum<FakeEnumTester>(FakeEnumTester.class);
   }

   @Test
   public void testValuesLen(){
      Assert.assertEquals(2, tested.values().size());
   }

   @Test
   public void testValuesContents(){
      Assert.assertTrue(tested.values().contains(FakeEnumTester.NO_SUBCLASS_ENUM));
      Assert.assertTrue(tested.values().contains(FakeEnumTester.SUBCLASS_ENUM));
      Assert.assertFalse(tested.values().contains(FakeEnumTester.NOT_ENUM));
   }

   @Test
   public void testParse(){
      Assert.assertEquals(FakeEnumTester.NO_SUBCLASS_ENUM, tested.valueOf("NO_SUBCLASS_ENUM"));
   }


}
