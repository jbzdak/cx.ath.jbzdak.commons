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
