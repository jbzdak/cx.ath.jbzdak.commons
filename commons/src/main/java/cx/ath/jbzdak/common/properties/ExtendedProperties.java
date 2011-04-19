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
   public boolean containsKey(Object key) {
      return properties.containsKey(key);
   }

   @Override
   public String get(Object key) {
      return (String) properties.get(key);
   }

   @Override
   protected String getPropertyInternal(String keyName) {
      return properties.getProperty(keyName);
   }

   @Override
   public Set<Entry<String, String>> entrySet() {
      return new AbstractSet<Entry<String, String>>() {

         Set<Entry<Object, Object>> entries = properties.entrySet();

         @Override
         public Iterator<Entry<String, String>> iterator() {
            return new Iterator<Entry<String, String>>() {

               Iterator<Entry<Object, Object>> iterator = entries.iterator();

               public boolean hasNext() {
                  return  iterator.hasNext();
               }

               public Entry<String, String> next() {
                  final Entry<Object, Object> entry = iterator.next();
                  return new SimpleEntry<String, String>(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
               }

               public void remove() {
                  iterator.remove();
               }
            };
         }

         @Override
         public int size() {
            return entries.size();
         }
      };
   }

}


