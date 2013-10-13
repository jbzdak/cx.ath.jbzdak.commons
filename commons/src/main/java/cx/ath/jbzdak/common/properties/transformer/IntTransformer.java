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

package cx.ath.jbzdak.common.properties.transformer;



import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by: Jacek Bzdak
 */

public class IntTransformer implements Transformer<Integer>{

   private final NumberFormat numberFormat;

   public IntTransformer() {
      numberFormat = NumberFormat.getIntegerInstance();
   }

   public IntTransformer(NumberFormat numberFormat) {
      this.numberFormat = numberFormat;
   }

   public Integer transformReverse(String value) {
      try {
         return this.numberFormat.parse(value).intValue();
      } catch (ParseException e) {
         throw  new TransformationException("Parse exception", e);
      }
   }

   public String transform(Integer value) {
      return this.numberFormat.format(value);
   }
}
