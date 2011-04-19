package cx.ath.jbzdak.common.annotation.property;

import java.lang.annotation.*;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2010-02-15
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Immutable {
}
