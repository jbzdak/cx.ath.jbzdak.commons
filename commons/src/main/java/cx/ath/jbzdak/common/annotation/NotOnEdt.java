package cx.ath.jbzdak.common.annotation;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 2/12/11
 */
public @interface NotOnEdt {

   String value() default "";
}
