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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by: Jacek Bzdak
 */
public class DateTransformer implements Transformer<Date>{

   private final DateFormat dateFormat;

   public DateTransformer() {
      dateFormat = new SimpleDateFormat("YYYY-MM-DD");
   }

   public DateTransformer(DateFormat dateFormat) {
      this.dateFormat = dateFormat;
   }


   public Date transformReverse(String from) {
      try {
         return dateFormat.parse(from);
      } catch (ParseException e) {
         throw new TransformationException(e);
      }
   }

   public String transform(Date value) {
       return dateFormat.format(value);
   }
}
