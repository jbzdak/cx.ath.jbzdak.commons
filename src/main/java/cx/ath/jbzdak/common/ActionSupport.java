package cx.ath.jbzdak.common;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-11-11
 */
public class ActionSupport {

   private final List<ActionListener> listeners = new ArrayList<ActionListener>();

   private final Object source;

   public ActionSupport(Object source) {
      this.source = source;
   }

   public void addActionListener(ActionListener actionListener){
      listeners.add(actionListener);
   }

   public List<ActionListener> getActionListeners() {
      return Collections.unmodifiableList(listeners);
   }

   public boolean hasActionListeners() {
      return !listeners.isEmpty();
   }

   public boolean removeActionListener(Object o) {
      return listeners.remove(o);
   }

   public void fireActionEvent(String command, int id, long when, int modifiers){
      if(listeners.isEmpty()){
         return;
      }
      ActionEvent event = new ActionEvent(source, id, command, when, modifiers);
      for(ActionListener listener : listeners){
         listener.actionPerformed(event);
      }
   }

   public void fireActionEvent(String command, int id, int modifiers){
      fireActionEvent(command, id, 0, modifiers);
   }

   public void fireActionEvent(String command, int id){
      fireActionEvent(command, id, 0, 0);
   }

   public void fireActionEvent(){
      fireActionEvent("DEFAULT", 0, 0, 0);
   }



}
