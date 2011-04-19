package cx.ath.jbzdak.common.annotation.property;

/**
 * Governs when calling a setter will change value returned by getter.
 * There may be a time lapse, or setting value may give no result.
 *
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: Feb 24, 2010
 */
public enum Effective {
   /**
    * After exiting setter value returned getter has changed
    */
   EFFECTIVE_INSTANTLY,
   /**
    * Value set by setter will be returned by getter after some time.
    *
    * For example setter sends message to electronic equipement, and getter
    * reads this value from equipement.
    */
   EFFECTIVE_AFTER_TIMEOUT,
   /**
    * Value may or might not be updated
    */
   MAY_FAIL;
}
