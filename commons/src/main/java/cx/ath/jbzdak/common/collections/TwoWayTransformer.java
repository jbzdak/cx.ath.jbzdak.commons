package cx.ath.jbzdak.common.collections;

/**
 * Created by: Jacek Bzdak
 */
public interface TwoWayTransformer<T, V> extends Transformer<T, V> {

   V transformReverse(T value);
}
