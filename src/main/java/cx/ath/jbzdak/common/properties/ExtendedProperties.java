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
public class ExtendedProperties extends AbstractMap<String, String> implements Map<String, String> {

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

   public int getAsInt(String key){
      return Integer.parseInt(get(key));
   }

   public Date getAsDate(String key, DateFormat dateFormat) throws ParseException {
      return dateFormat.parse(get(key));
   }

   public Date getAsDate(String key, String dateFormat) throws ParseException {
      return new SimpleDateFormat(dateFormat).parse(get(key));
   }

   public Date getAsDate(String key) throws ParseException {
      return defaultDateFormat.parse(get(key));
   }

   private static void assertLen(List<?> objects, int len){
      if(objects.size() <= len){
         while(objects.size() <= len){
            objects.add(null);
         }
      }
   }

   public Collection<String> getMatching(String pattern){
      Pattern p = Pattern.compile(pattern);
      List<String> result = new ArrayList<String>();
      for (Entry<String, String> e : entrySet()) {
         if(p.matcher(e.getKey()).matches()){
            result.add(e.getValue());
         }
      }
      return result;
   }

   /**
    * Returns set that consists of pairs <code>(pattern.mather(key).group(1), value)</code>
    * @param pattern
    * @return
    */
   public Set<Entry<String, String>> getMatchingEntries(String pattern){
      Pattern p = Pattern.compile(pattern);
      Set<Entry<String, String>> result = new HashSet<Entry<String, String>>();
      for (Entry<String, String> e : entrySet()) {
         Matcher matcher = p.matcher(e.getKey());
         if(matcher.matches()){
            result.add(new SimpleImmutableEntry<String, String>(matcher.group(1), e.getValue()));
         }
      }
      return result;
   }

   /**
    * Returns set that consists of pairs <code>(pattern.mather(key).group(1), value)</code>
    * @param pattern
    * @return
    */
   public <K, V> Set<Entry<K, V>> getMatchingEntries(String pattern,
                                                        Transformer<K, String> keyTransformer,
                                                        Transformer<V, String> valueTransformer){
      Pattern p = Pattern.compile(pattern);
      Set<Entry<K, V>> result = new HashSet<Entry<K, V>>();
      for (Entry<String, String> e : entrySet()) {
         Matcher matcher = p.matcher(e.getKey());
         if(matcher.matches()){
            result.add(new SimpleImmutableEntry<K, V>(keyTransformer.transform(matcher.group(1)),
                                                      valueTransformer.transform(e.getValue())));
         }
      }
      return result;
   }


   /**
    * Returns list of values.
    * @param keyPattern pattern of keys to be added to list. Must contain single group that matches digits. For example
    *    <code>enty.(\\d+)</code> contents of that group will be used index of value in the list.
    * @return
    */
   public List<String> getList(String keyPattern){
      Pattern pattern = Pattern.compile(keyPattern);
      List<String> result = new ArrayList<String>();
      for (Entry<String, String> entry : entrySet()) {
         final Matcher matcher = pattern.matcher(entry.getKey());
         if(matcher.matches()){
            int index = Integer.parseInt(matcher.group(1));
            assertLen(result, index);
            result.set(index, entry.getValue());
         }
      }
      while (result.remove(null));
      return result;
   }

   public <T> List<T> getList(String keyPattern, Transformer<T, String> transformer){
      List<String> strings = getList(keyPattern);
      List<T> result = new ArrayList<T>(strings.size());
      for (String string : strings) {
         result.add(transformer.transform(string));
      }
      return result;
   }



//   public int get

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


