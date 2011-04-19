package cx.ath.jbzdak.common.properties.transformer;

import cx.ath.jbzdak.common.collections.TwoWayTransformer;

import javax.xml.transform.TransformerException;

/**
 * Created by: Jacek Bzdak
 */
public interface Transformer<V> extends TwoWayTransformer<String, V>{}
