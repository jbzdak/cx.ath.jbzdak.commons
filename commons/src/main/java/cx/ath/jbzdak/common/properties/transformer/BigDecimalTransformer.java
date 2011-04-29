package cx.ath.jbzdak.common.properties.transformer;

import java.math.BigDecimal;

/**
 * Created by: Jacek Bzdak
 */
public class BigDecimalTransformer implements Transformer<BigDecimal>{

   public static final BigDecimalTransformer DECIMAL_TRANSFORMER = new BigDecimalTransformer();

   private BigDecimalTransformer() {
   }

   public BigDecimal transformReverse(String value) {
      return new BigDecimal(value);
   }

   public String transform(BigDecimal value) {
      return value.toEngineeringString();
   }
}
