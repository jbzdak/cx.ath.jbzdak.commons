package cx.ath.jbzdak.common.properties;

import cx.ath.jbzdak.common.collections.Transformer;

/**
 * @author Jacek Bzdak
 */
public class InstantiateTransformer<CLS> implements Transformer<CLS, String>{

   /**
    * Instantiates object of class named <code>classname</code> using no arg constrctor.
    * @param classname name of classs
    * @return instance
    * @throws RuntimeException if any checked exception is raised 
    */
   public CLS transform(String classname) {
      try {
         return (CLS) Class.forName(classname).newInstance();
      }catch (RuntimeException  e){
         throw e;
      }
      catch (Exception e){
         throw  new RuntimeException(e);
      }
   }
}
