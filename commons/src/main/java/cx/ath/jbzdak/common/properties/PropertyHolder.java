package cx.ath.jbzdak.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Jacek Bzdak
 */
public class PropertyHolder {

   protected Properties properties;

   public PropertyHolder(Class clazz, String defaultResources, String userResources) throws IOException {
      properties = new Properties();
      try {
         properties.load(clazz.getResourceAsStream(defaultResources));
      } catch (IOException e) {
         throw new IOException("Couldnt load default properties", e);
      } catch (NullPointerException e){
         throw new IOException("Couldnt load default properties", e);
      }
      File file = new File(userResources);
      file.getParentFile().mkdirs();
//      if (!file.exists()){
//         file.createNewFile();
//      }
      if (file.exists()){
         properties.load(new FileInputStream(file));
      }else{
         file.createNewFile();
         properties.store(new FileOutputStream(file), "Auto generated");

      }
   }

   public Properties getProperties() {
      return properties;
   }
}
