package cx.ath.jbzdak.common;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.rmi.server.UID;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-10-30
 */
public class PropertyChangeSupportTest {

   String source;

   PropertyChangeSupport support;

   @Before
   public void before(){
      source = new UID().toString();
      support = new PropertyChangeSupport(source);
   }


   @Test
   public void testAddListener(){
      PropertyChangeListener mock = mock(PropertyChangeListener.class);
      PropertyChangeEvent evt = new PropertyChangeEvent(source, "foo", "bar", "baz");
      support.addPropertyChangeListener(mock);
      support.firePropertyChange(evt);
      verify(mock).propertyChange(evt);
   }

   @Test
   public void testPropertyListeners(){
      PropertyChangeListener mock = mock(PropertyChangeListener.class);
      PropertyChangeEvent evt = new PropertyChangeEvent(source, "foo", "bar", "baz");
      support.addPropertyChangeListener("foo", mock);
      support.firePropertyChange(evt);
      verify(mock).propertyChange(evt);
   }

   @Test
   public void testPropertyListeners2(){
      PropertyChangeListener mock = mock(PropertyChangeListener.class);
      PropertyChangeEvent evt = new PropertyChangeEvent(source, "foo", "bar", "baz");
      doThrow(new UnsupportedOperationException()).when(mock).propertyChange(Matchers.<PropertyChangeEvent>any());
      support.addPropertyChangeListener("not-foo", mock);
      support.firePropertyChange(evt);
   }

   @Test
   public void testEventWithTheSameValues1(){
      PropertyChangeListener mock = mock(PropertyChangeListener.class);
      PropertyChangeEvent evt = new PropertyChangeEvent(source, "foo", "bar", "bar");
      doThrow(new UnsupportedOperationException()).when(mock).propertyChange(Matchers.<PropertyChangeEvent>any());
      support.addPropertyChangeListener(mock);
      support.firePropertyChange(evt);
   }

   @Test
   public void testEventWithTheSameValues2(){
      support = new PropertyChangeSupport(source, false);
      PropertyChangeListener mock = mock(PropertyChangeListener.class);
      PropertyChangeEvent evt = new PropertyChangeEvent(source, "foo", "bar", "bar");
      support.addPropertyChangeListener(mock);
      support.firePropertyChange(evt);
      verify(mock).propertyChange(evt);
   }

   public interface SerializablePCL extends PropertyChangeListener, Serializable{}

   public static class MockPcl implements SerializablePCL{

      boolean wasCalled;

      public boolean isWasCalled() {
         return wasCalled;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         wasCalled=true;
      }
   }
   @Test
   public void testSerialize() throws Exception {
      PropertyChangeListener mock = new MockPcl();
      PropertyChangeListener notSerializable = mock(PropertyChangeListener.class);
      support.addPropertyChangeListener(mock);
      support.addPropertyChangeListener(notSerializable);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ObjectOutputStream objOsStream = new ObjectOutputStream(outputStream);
      objOsStream.writeObject(support);
      objOsStream.flush();
      support = (PropertyChangeSupport) new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray())).readObject();
      support.firePropertyChange("aaa", "aaa", "vbb");
      Assert.assertEquals(1, support.getPropertyChangeListeners().size());
      Assert.assertTrue(((MockPcl)support.getPropertyChangeListeners().get(0)).isWasCalled());


   }
}
