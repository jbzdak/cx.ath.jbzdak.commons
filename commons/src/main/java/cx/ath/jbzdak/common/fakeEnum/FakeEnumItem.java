package cx.ath.jbzdak.common.fakeEnum;

/**
 * Jacek Bzdak
 */
public class FakeEnumItem<FE extends FakeEnumItem> {

   FakeEnum<FE> fakeEnum;

   public FakeEnumItem(FakeEnum<FE> fakeEnum) {
      this.fakeEnum = fakeEnum;
   }

   public final String name(){
      return fakeEnum.nameOf((FE) this);
   }

   public final boolean equals(Object e2){
      return fakeEnum.equals(this, e2);
   }
   
}
