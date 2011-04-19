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
