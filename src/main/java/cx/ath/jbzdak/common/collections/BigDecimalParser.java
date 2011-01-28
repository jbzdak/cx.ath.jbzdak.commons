package cx.ath.jbzdak.common.collections;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 1/27/11
 */
public class BigDecimalParser implements Transformer<BigDecimal,String>{

   final MathContext mathContext;

   public BigDecimalParser() {
      this(MathContext.UNLIMITED);
   }

   public BigDecimalParser(MathContext mathContext) {
      this.mathContext = mathContext;
   }

   public BigDecimal transform(String value) {
      return new BigDecimal(value);
   }
}
