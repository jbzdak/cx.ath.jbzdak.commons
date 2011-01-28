package cx.ath.jbzdak.common.collections;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 1/27/11
 */
public class NoopTransformer<T> implements Transformer<T,T>{
   public T transform(T value) {
      return value;
   }
}
