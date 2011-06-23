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

package cx.ath.jbzdak.common.properties.transformer;

/**
 * @author Jacek Bzdak
 */
public class InstantiateTransformer<CLS> implements Transformer<CLS>{

   /**
    * Instantiates object of class named <code>classname</code> using no arg constrctor.
    * @param classname name of classs
    * @return instance
    * @throws RuntimeException if any checked exception is raised 
    */
   public CLS transformReverse(String classname) {
      try {
         return (CLS) Class.forName(classname).newInstance();
      }catch (RuntimeException  e){
         throw e;
      }
      catch (Exception e){
         throw  new RuntimeException(e);
      }
   }

   public String transform(CLS value) {
      return value.getClass().getCanonicalName();
   }
}
