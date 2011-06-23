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
         throw new IOException("Couldnt load default properties from '" + defaultResources + "'", e);
      } catch (NullPointerException e){
         throw new IOException("Couldnt load default properties '" + defaultResources + "'", e);
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
