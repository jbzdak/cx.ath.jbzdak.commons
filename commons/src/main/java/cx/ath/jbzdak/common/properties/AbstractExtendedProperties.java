package cx.ath.jbzdak.common.properties;

import cx.ath.jbzdak.common.properties.transformer.*;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Jacek Bzdak
 */
public abstract class AbstractExtendedProperties{

   protected DateFormat defaultDateFormat;

   protected abstract String getPropertyInternal(String keyName);

   protected abstract String setPropertyInternal(String keyName, String value);

   public abstract Map<String, String> getAsMap();

   protected AbstractExtendedProperties() {
      defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
   }

   public <T> T getValue(String keyName, Transformer<T> transformer, T defaultValue){
      String value = getPropertyInternal(keyName);
      if (value == null){
         return defaultValue;
      }
      return transformer.transformReverse(value);
   }

   public <T> String setValue(String keyName, Transformer<T> transformer, T value){
      return setPropertyInternal(keyName, transformer.transform(value));
   }

   public String getString(String keyName, String defaultStr){
      return getValue(keyName, NoopTransformer.NOOP_TRANSFORMER, defaultStr);
   }

   public String getString(String keyName){
      return getString(keyName, null);
   }

   public Boolean getBoolen(String keyName, Boolean defaultValue){
      return getValue(keyName, BooleanTransformer.BOOLEAN_TRANSFORMER, defaultValue);
   }


   public Boolean getBoolen(String keyName){
      return getBoolen(keyName, null);
   }


   public String setBoolean(String keyName, Boolean value){
      return setValue(keyName, BooleanTransformer.BOOLEAN_TRANSFORMER, value);
   }

   public String setString(String keyName, String keyValue){
      return setValue(keyName, NoopTransformer.NOOP_TRANSFORMER, keyValue);
   }

   public <T> T getValue(String keyName, Transformer<T> transformer){
      return getValue(keyName, transformer, null);
   }

   public Date getDate(String keyName, DateFormat dateFormat, Date defaultDate){
      return getValue(keyName, new DateTransformer(dateFormat), defaultDate);
   }

    public Date getDate(String keyName, DateFormat dateFormat){
      return getDate(keyName, dateFormat, null);
   }

   public Date getDate(String keyName, Date defaultDate){
      return getValue(keyName, new DateTransformer(defaultDateFormat), defaultDate);
   }

   public Date getDate(String keyName){
      return getValue(keyName, new DateTransformer(defaultDateFormat));
   }

   public String setDate(String keyName, DateFormat dateFormat, Date date){
      return setValue(keyName, new DateTransformer(dateFormat), date);
   }

   public String setDate(String keyName, Date date){
      return setValue(keyName, new DateTransformer(defaultDateFormat), date);
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

   public String setInt(String ketyName, NumberFormat numberFormat, Integer integer){
      return setValue(ketyName, new IntTransformer(numberFormat), integer);
   }

    public String setInt(String ketyName, Integer integer){
      return setValue(ketyName, new IntTransformer(), integer);
   }

   public <T extends Enum> T getEnum(String keyName, Class<T> enumClass, T defaultValue){
      return getValue(keyName, new EnumTransformer<T>(enumClass), defaultValue);
   }

   public <T extends Enum> T getEnum(String keyName, Class<T> enumClass){
      return getEnum(keyName, enumClass, null);
   }

   public <T extends Enum> String setEnum(String keyName, T enumInstance){
      if (enumInstance == null){
         return setValue(keyName, NoopTransformer.NOOP_TRANSFORMER, null);
      }  else {
         return setValue(keyName, new EnumTransformer<T>((Class<T>) enumInstance.getClass()), enumInstance);
      }
   }


   public <T> Collection<T> getMatching(String pattern, Transformer<T> transformer){
      Pattern p = Pattern.compile(pattern);
      List<T> result = new ArrayList<T>();
      for (Map.Entry<String, String> e : getAsMap().entrySet()) {
         if(p.matcher(e.getKey()).matches()){
            result.add(transformer.transformReverse(e.getValue()));
         }
      }
      return result;
   }

   public Collection<String> getMatching(String pattern){
      return getMatching(pattern, NoopTransformer.NOOP_TRANSFORMER);
   }


   public <K, V> Set<Map.Entry<K, V>> getMatchingEntries(String pattern,
                                                     Transformer<K> keyTransformer,
                                                     Transformer<V> valueTransformer){
      Pattern p = Pattern.compile(pattern);
      Set<Map.Entry<K, V>> result = new HashSet<Entry<K, V>>();
      for (Entry<String, String> e : getAsMap().entrySet()) {
         Matcher matcher = p.matcher(e.getKey());
         if(matcher.matches()){
            switch (matcher.groupCount()){
               case 0:
                  result.add(new SimpleImmutableEntry<K, V>(keyTransformer.transformReverse(matcher.group()),
                       valueTransformer.transformReverse(e.getValue())));
                  break;
               case 1:
                  result.add(new SimpleImmutableEntry<K, V>(keyTransformer.transformReverse(matcher.group(1)),
                       valueTransformer.transformReverse(e.getValue())));
                  break;
               default:
                  throw new InvalidParameterException();

            }
         }
      }
      return result;
   }

      public Set<Entry<String, String>> getMatchingEntries(String pattern){
      return getMatchingEntries(pattern, NoopTransformer.NOOP_TRANSFORMER, NoopTransformer.NOOP_TRANSFORMER);
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
   public <T> List<T> getList(String keyPattern, Transformer<T> transformer){
      Pattern pattern = Pattern.compile(keyPattern);
      NavigableMap<Integer, T> result = new TreeMap<Integer, T>();
      for (Entry<String, String> entry : getAsMap().entrySet()) {
         final Matcher matcher = pattern.matcher(entry.getKey());
         if(matcher.matches()){
            int index = Integer.parseInt(matcher.group(1));
            result.put(index, transformer.transformReverse(entry.getValue()));
         }
      }
      return new ArrayList<T>(result.values());
   }

   public List<String> getList(String keyPattern){
      return getList(keyPattern, NoopTransformer.NOOP_TRANSFORMER);
   }
}
