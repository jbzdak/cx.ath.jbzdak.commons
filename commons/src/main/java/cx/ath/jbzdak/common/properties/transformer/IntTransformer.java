package cx.ath.jbzdak.common.properties.transformer;

import net.jcip.annotations.NotThreadSafe;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by: Jacek Bzdak
 */
@NotThreadSafe
public class IntTransformer implements Transformer<Integer>{

   private final NumberFormat numberFormat;

   public IntTransformer() {
      numberFormat = NumberFormat.getIntegerInstance();
   }

   public IntTransformer(NumberFormat numberFormat) {
      this.numberFormat = numberFormat;
   }

   public Integer transformReverse(String value) {
      try {
         return this.numberFormat.parse(value).intValue();
      } catch (ParseException e) {
         throw  new TransformationException("Parse exception", e);
      }
   }

   public String transform(Integer value) {
      return this.numberFormat.format(value);
   }
}
