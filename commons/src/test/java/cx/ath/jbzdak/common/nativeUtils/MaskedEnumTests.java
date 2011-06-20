package cx.ath.jbzdak.common.nativeUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by: Jacek Bzdak
 */
public class MaskedEnumTests {


   @Before
   public void setUp() throws Exception {

   }

   @Test
   public void testTestBuild() throws Exception {
      for (TestMaskedEnum anEnum : TestMaskedEnum.FAKE_ENUM.values()) {
         Assert.assertEquals(anEnum.constant, TestMaskedEnum.or(anEnum));
      }
   }

   @Test
   public void testTestOr() throws Exception {
      Assert.assertEquals(3, TestMaskedEnum.or(TestMaskedEnum.A0, TestMaskedEnum.A1));
   }

   @Test
   public void testTestOrBase() throws Exception {
      Assert.assertEquals(6, TestMaskedEnum.or(4, TestMaskedEnum.A1));
   }

   @Test
   public void testTestAndNegative() throws Exception {
      Assert.assertEquals(0, TestMaskedEnum.and(4, TestMaskedEnum.A1));
   }

   @Test
   public void testAndPositive() throws Exception {
      Assert.assertEquals(2, TestMaskedEnum.and(6, TestMaskedEnum.A1));
   }

   @Test
   public void testSwitchUp() throws Exception {
      Assert.assertEquals(2, TestMaskedEnum.switchOn(TestMaskedEnum.A1));
   }

   @Test
   public void testSwitchUp2() throws Exception {
      Assert.assertEquals(2, TestMaskedEnum.switchOn(2, TestMaskedEnum.A1));
   }

   @Test
   public void testSwitchUp3() throws Exception {
      Assert.assertEquals(6, TestMaskedEnum.switchOn(2, TestMaskedEnum.A2));
   }


   @Test
   public void testDecompose() throws Exception {
      Assert.assertEquals(new HashSet(Arrays.asList(TestMaskedEnum.A1, TestMaskedEnum.A2)), TestMaskedEnum.decomposite(TestMaskedEnum.MAP, 6));
   }

   @Test
   public void testDecompose2() throws Exception {
      Assert.assertEquals(new HashSet(Arrays.asList(TestMaskedEnum.A1, TestMaskedEnum.A0, TestMaskedEnum.MASK)), TestMaskedEnum.decomposite(TestMaskedEnum.MAP, 3));
   }



}

