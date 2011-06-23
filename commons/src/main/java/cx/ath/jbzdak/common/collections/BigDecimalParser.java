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

package cx.ath.jbzdak.common.collections;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Created by IntelliJ IDEA.
 * User: Jacek Bzdak
 * Date: 1/27/11
 */
public class BigDecimalParser implements Transformer<BigDecimal,String>{

   final MathContext mathContext;

   public BigDecimalParser() {
      this(MathContext.UNLIMITED);
   }

   public BigDecimalParser(MathContext mathContext) {
      this.mathContext = mathContext;
   }

   public BigDecimal transform(String value) {
      return new BigDecimal(value);
   }
}
