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
