package cx.ath.jbzdak.common.properties;

import cx.ath.jbzdak.common.collections.Transformer;

import java.awt.image.Kernel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Jacek Bzdak
 */
public class ExtendedProperties extends AbstractExtendedProperties {

   Properties properties;

   DateFormat defaultDateFormat;

   public ExtendedProperties(Properties properties) {
      this.properties = properties;
   }

   public Properties getProperties() {
      return properties;
   }

   public void setProperties(Properties properties) {
      this.properties = properties;
   }

   public DateFormat getDefaultDateFormat() {
      return defaultDateFormat;
   }

   public void setDefaultDateFormat(DateFormat defaultDateFormat) {
      this.defaultDateFormat = defaultDateFormat;
   }

   @Override
   protected String getPropertyInternal(String keyName) {
      return properties.getProperty(keyName);
   }

   @Override
   protected String setPropertyInternal(String keyName, String value) {
      return String.valueOf(properties.setProperty(keyName, value));
   }

   @Override
   public Map<String, String> getAsMap() {
      return new AbstractMap<String, String>() {
         @Override
         public Set<Entry<String, String>> entrySet() {
            return new AbstractSet<Entry<String, String>>() {
               @Override
               public Iterator<Entry<String, String>> iterator() {
                  return new Iterator<Entry<String, String>>() {

                     Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();

                     public boolean hasNext() {
                        return iterator.hasNext();
                     }

                     public Entry<String, String> next() {
                        Entry<Object, Object> entry = iterator.next();
                        return new SimpleImmutableEntry<String, String>(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                     }

                     public void remove() {
                        iterator.remove();
                     }
                  };
               }

               @Override
               public int size() {
                  return properties.size();
               }
            };
         }
      };
   }
}


