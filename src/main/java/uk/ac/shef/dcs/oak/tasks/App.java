package uk.ac.shef.dcs.oak.tasks;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;

/**
 * Class for showing the current task from a google calendar
 */
public class App extends JFrame
{
   String currentTask = "No Task";
   String password = null;

   public App()
   {
      password = JOptionPane.showInputDialog("Password?");
      initGUI();
   }

   JLabel label;

   private void initGUI()
   {
      label = new JLabel(currentTask);
      this.add(label);
      this.pack();

      try
      {
         updateCurrentTask();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      Thread threader = new Thread(new Runnable()
      {
         @Override
         public void run()
         {
            try
            {
               while (true)
               {
                  Thread.sleep(1000 * 60);
                  updateCurrentTask();
               }
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
         }
      });
      threader.start();

      this.setAlwaysOnTop(true);
   }

   private void updateCurrentTask() throws Exception
   {
      // Create a CalenderService and authenticate
      CalendarService myService = new CalendarService("exampleCo-exampleApp-1");

      myService.setUserCredentials("s.tucker@sheffield.ac.uk", password);
      URL feedUrl = new URL(
            "https://www.google.com/calendar/feeds/s.tucker@sheffield.ac.uk/private/full");

      // Send the request and receive the response:
      CalendarEventFeed myFeed = myService.getFeed(feedUrl, CalendarEventFeed.class);

      for (int i = 0; i < myFeed.getEntries().size(); i++)
      {
         CalendarEventEntry entry = myFeed.getEntries().get(i);
         for (When time : entry.getTimes())
         {
            long sTime = time.getStartTime().getValue();
            long eTime = time.getEndTime().getValue();
            if (System.currentTimeMillis() >= sTime && System.currentTimeMillis() <= eTime)
               currentTask = myFeed.getEntries().get(i).getTitle().getPlainText();
         }
      }

      updateGUI();
   }

   private void updateGUI()
   {
      label.setText(currentTask);
      pack();
   }

   public static void main(String[] args)
   {
      App mine = new App();
      mine.setLocationRelativeTo(null);
      mine.setVisible(true);
      mine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}
