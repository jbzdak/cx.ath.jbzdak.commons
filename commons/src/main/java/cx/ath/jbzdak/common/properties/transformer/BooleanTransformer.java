package cx.ath.jbzdak.common.properties.transformer;

/**
 * Created by: Jacek Bzdak
 */
public class BooleanTransformer implements Transformer<Boolean>{

   public static final BooleanTransformer BOOLEAN_TRANSFORMER = new BooleanTransformer();

   private BooleanTransformer() {
   }

   public Boolean transformReverse(String value) {
      return Boolean.valueOf(value);
   }

   public String transform(Boolean value) {
      return String.valueOf(value);
   }
}
