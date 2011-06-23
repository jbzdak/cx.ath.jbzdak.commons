package cx.ath.jbzdak.common.annotation;

import java.lang.annotation.*;

/**
 * This annotation means that in enum that represents some possible settings annotated setting is the default.
 * Created by: Jacek Bzdak
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default { }
