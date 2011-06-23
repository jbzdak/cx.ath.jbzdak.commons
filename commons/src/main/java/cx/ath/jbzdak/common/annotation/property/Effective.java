/*
 * Copyright for Jacek Bzdak 2011.
 *
 * This file is part of my commons library.
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * It is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License.
 * If not, see <http://www.gnu.org/licenses/>.
 */

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
