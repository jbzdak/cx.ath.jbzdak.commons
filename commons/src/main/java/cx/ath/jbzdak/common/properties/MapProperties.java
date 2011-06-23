package cx.ath.jbzdak.common.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Jacek Bzdak
 */
public class MapProperties extends AbstractExtendedProperties{


   private final Map<String, String> internal;

   public MapProperties(Map<String, String> internal) {
      this.internal = internal;
   }

   @Override
   protected String getPropertyInternal(String keyName) {
      return internal.get(keyName);
   }

   @Override
   protected String setPropertyInternal(String keyName, String value) {
      return internal.put(keyName, value);
   }

   @Override
   public Map<String, String> getAsMap() {
      return internal;
   }
}
