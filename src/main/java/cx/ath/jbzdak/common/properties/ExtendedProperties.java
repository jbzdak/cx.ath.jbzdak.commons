package cx.ath.jbzdak.common.properties;

import cx.ath.jbzdak.common.collections.Transformer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Jacek Bzdak
 */
public class ExtendedProperties extends AbstractMap<String, String> implements Map<String, String> {

   Properties properties;


   public ExtendedProperties(Properties properties) {
      this.properties = properties;
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

   private static void assertLen(List<?> objects, int len){
      if(objects.size() < len){
         while(objects.size() == len){
            objects.add(null);
         }
      }
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
            return entrySet().size();
         }
      };
   }


}


