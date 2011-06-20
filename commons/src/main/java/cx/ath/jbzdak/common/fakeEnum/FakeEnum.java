package cx.ath.jbzdak.common.fakeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Jacek Bzdak
 */
public class FakeEnum<E> {

   private static final Logger LOGGER = LoggerFactory.getLogger(FakeEnum.class);

   Class<E> clazz;

   Map<String, ? extends E> constants;
   Map<? extends E, String> constantsReversed;   

   List<? extends E> constantList = new ArrayList<E>();

   static List<Field> findConstants(Class clazz){     
      List<Field> fields = new ArrayList<Field>();
      for (Field field : clazz.getDeclaredFields()){
         final int mod = field.getModifiers();
         if (
            Modifier.isStatic(mod) &&
            Modifier.isFinal(mod) &&
            Modifier.isPublic(mod) &&
            clazz.isAssignableFrom(field.getType()) &&
            field.getAnnotation(FakeEnumIgnore.class) == null){
            fields.add(field);
         }
      }
      return fields;
   }

   private static Map<String, Object> makeConstansts(Class clazz, List<Field> fields){
      Map<String, Object> constants = new HashMap<String, Object>();
      for (Field f : fields){
         boolean isAccesible = f.isAccessible();
         try {
            f.setAccessible(true);
            constants.put(f.getName(), f.get(null));
         } catch (IllegalAccessException e) {
            LOGGER.warn("Couldnt load field named {} from class", f.getName(), clazz);
         } finally {
            f.setAccessible(isAccesible);
         }
      }
      return constants;
   }

   private Map<String, E> makeConstansts (Class clazz){
      Map<String, Object> result =
              makeConstansts(clazz, findConstants(clazz));
      return (Map<String,E>) result;
   }

   public FakeEnum(Class<E> clazz) {
      this.clazz = clazz;
      this.constants = makeConstansts(clazz);
      Map<E, String> constantsReversed = new HashMap<E, String>();
      for (Map.Entry<String, ? extends E> stringEntry : constants.entrySet()) {
         constantsReversed.put(stringEntry.getValue(), stringEntry.getKey());
      }
      this.constantsReversed = constantsReversed;
//      this.constantList = new ArrayList<E>(constants.values());
   }

   public String nameOf(E value){
      if (constantsReversed.containsKey(value)){
         return constantsReversed.get(value);
      }
      throw new RuntimeException();      
   }

   public Collection<? extends E> values(){
      return constants.values();
   }

   public E valueOf(String s){
      return constants.get(s);
   }


   public String genToString(E o) {
      return o.getClass().getCanonicalName() + "@" + nameOf(o);
   }

   public boolean equals(Object e1, Object e2){
      if (! constantsReversed.containsKey(e1) || ! constantsReversed.containsKey(e2)){
         return false;
      }
      return nameOf((E) e1).equals(nameOf((E) e2));
   }
}
