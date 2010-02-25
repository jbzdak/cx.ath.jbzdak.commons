package cx.ath.jbzdak.common.annotation.property;

import java.lang.annotation.*;

/**
 * Means that property annotated with this annotation is bound ie changing it will cause proper
 * {@link java.beans.PropertyChangeEvent} raised. 
 *
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Feb 13, 2010
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
public @interface Bound {
   Effective effective() default Effective.EFFECTIVE_INSTANTLY;

   String description() default "";
}
