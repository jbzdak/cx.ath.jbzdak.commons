package cx.ath.jbzdak.common.properties.transformer;

/**
 * Created by: Jacek Bzdak
 */
public class NoopTransformer implements Transformer<String> {

   public static final NoopTransformer NOOP_TRANSFORMER = new NoopTransformer();

   private NoopTransformer() {
   }

   public String transformReverse(String value) {
      return value;
   }

   public String transform(String value) {
      return value;
   }
}
