package cx.ath.jbzdak.common.properties;

import com.sun.org.apache.xerces.internal.impl.dv.xs.IntegerDV;
import com.sun.xml.internal.messaging.saaj.util.FinalArrayList;
import cx.ath.jbzdak.common.properties.transformer.DateTransformer;
import cx.ath.jbzdak.common.properties.transformer.IntTransformer;
import cx.ath.jbzdak.common.properties.transformer.NoopTransformer;
import cx.ath.jbzdak.common.properties.transformer.Transformer;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Jacek Bzdak
 */
public abstract class AbstractExtendedProperties extends AbstractMap<String, String>{

   protected DateFormat defaultDateFormat;

   protected abstract String getPropertyInternal(String keyName);

   protected AbstractExtendedProperties() {
      defaultDateFormat = new SimpleDateFormat("YYYY-MM-DD");
   }

   public <T> T getValue(String keyName, Transformer<T> transformer, T defaultValue){
      String value = getPropertyInternal(keyName);
      if (value == null){
         return defaultValue;
      }
      return transformer.transformReverse(value);
   }

   public <T> T getValue(String keyName, Transformer<T> transformer){
      return getValue(keyName, transformer, null);
   }

   public Date getDate(String keyName, DateFormat dateFormat, Date defaultDate){
      return getValue(keyName, new DateTransformer(dateFormat), defaultDate);
   }

   public Date getDate(String keyName, Date defaultDate){
      return getValue(keyName, new DateTransformer(defaultDateFormat), defaultDate);
   }

   public Date getDate(String keyName){
      return getValue(keyName, new DateTransformer(defaultDateFormat));
   }

   public void setDefaultDateFormat(DateFormat defaultDateFormat) {
      this.defaultDateFormat = defaultDateFormat;
   }

   public Integer getInt(String keyName, NumberFormat numberFormat, Integer defaultInt){
      return getValue(keyName, new IntTransformer(numberFormat), defaultInt);
   }

   public Integer getInt(String keyName, Integer defaultInt){
      return getValue(keyName, new IntTransformer(), defaultInt);
   }

   public Integer getInt(String keyName){
      return getValue(keyName, new IntTransformer(), null);
   }

   public <T> Collection<T> getMatching(String pattern, Transformer<T> transformer){
      Pattern p = Pattern.compile(pattern);
      List<T> result = new ArrayList<T>();
      for (Map.Entry<String, String> e : entrySet()) {
         if(p.matcher(e.getKey()).matches()){
            result.add(transformer.transformReverse(e.getValue()));
         }
      }
      return result;
   }

   public Collection<String> getMatching(String pattern){
      return getMatching(pattern, NoopTransformer.NOOP_TRANSFORMER);
   }


   public <K, V> Set<Entry<K, V>> getMatchingEntries(String pattern,
                                                     cx.ath.jbzdak.common.collections.Transformer<K, String> keyTransformer,
                                                     cx.ath.jbzdak.common.collections.Transformer<V, String> valueTransformer){
      Pattern p = Pattern.compile(pattern);
      Set<Entry<K, V>> result = new HashSet<Entry<K, V>>();
      for (Entry<String, String> e : entrySet()) {
         Matcher matcher = p.matcher(e.getKey());
         if(matcher.matches()){
            switch (matcher.groupCount()){
               case 0:
                  result.add(new SimpleImmutableEntry<K, V>(keyTransformer.transform(matcher.group()),
                       valueTransformer.transform(e.getValue())));
                  break;
               case 1:
                  result.add(new SimpleImmutableEntry<K, V>(keyTransformer.transform(matcher.group(1)),
                       valueTransformer.transform(e.getValue())));
                  break;
               default:
                  throw new InvalidParameterException();

            }
         }
      }
      return result;
   }


   private static void assertLen(List<?> objects, int len){
      if(objects.size() <= len){
         while(objects.size() <= len){
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
   public <T> List<T> getList(String keyPattern, cx.ath.jbzdak.common.collections.Transformer<T, String> transformer){
      Pattern pattern = Pattern.compile(keyPattern);
      NavigableMap<Integer, T> result = new TreeMap<Integer, T>();
      for (Entry<String, String> entry : entrySet()) {
         final Matcher matcher = pattern.matcher(entry.getKey());
         if(matcher.matches()){
            int index = Integer.parseInt(matcher.group(1));
            result.put(index, transformer.transform(entry.getValue()));
         }
      }
      return new ArrayList<T>(result.values());
   }

   public List<String> getList(String keyPattern){
      return getList(keyPattern, NoopTransformer.NOOP_TRANSFORMER);
   }





}
