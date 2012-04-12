package uk.ac.shef.dcs.oak.tasks;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Class for showing the current task from a google calendar
 */
public class App extends JFrame
{
   String currentTask = "No Task";

   public App()
   {
      initGUI();
   }

   JLabel label;

   private void initGUI()
   {
      label = new JLabel(currentTask);
      this.add(label);
      this.pack();
   }

   public static void main(String[] args)
   {
      App mine = new App();
      mine.setLocationRelativeTo(null);
      mine.setVisible(true);
      mine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}
