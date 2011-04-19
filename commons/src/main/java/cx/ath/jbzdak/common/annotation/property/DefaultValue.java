package cx.ath.jbzdak.common.annotation.property;

import java.lang.annotation.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Mar 10, 2010
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Documented
public @interface DefaultValue {
   String value();
}
