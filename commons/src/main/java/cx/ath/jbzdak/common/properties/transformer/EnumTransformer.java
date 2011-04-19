package cx.ath.jbzdak.common.properties.transformer;

/**
 * Created by: Jacek Bzdak
 */
public class EnumTransformer<E extends Enum<E>> implements Transformer<E>{

   Class<E> clazz;

   public EnumTransformer(Class<E> clazz) {
      this.clazz = clazz;
   }

   public E transformReverse(String value) {
      return Enum.valueOf(clazz, value);
   }

   public String transform(E value) {
      return value.name();
   }
}
