package cx.ath.jbzdak.common.collections;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 13, 2010
 */
public interface Transformer<T, V> {

   T transform(V value); 
}
