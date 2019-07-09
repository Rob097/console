/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import java.util.Calendar;
import static java.util.Calendar.DAY_OF_WEEK;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import varie.copyWeekViews;

/**
 * Web application lifecycle listener.
 *
 * @author Roberto97
 */
public class scheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler = null;
    Map<Integer, Integer> dayToDelay;
    int dayOfWeek, delayInDays, hour, delayInHours;
    Calendar with;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dayToDelay = new HashMap<>();
        with = Calendar.getInstance();
        dayToDelay.put(Calendar.FRIDAY, 2);
        dayToDelay.put(Calendar.SATURDAY, 1);
        dayToDelay.put(Calendar.SUNDAY, 0);
        dayToDelay.put(Calendar.MONDAY, 6);
        dayToDelay.put(Calendar.TUESDAY, 5);
        dayToDelay.put(Calendar.WEDNESDAY, 4);
        dayToDelay.put(Calendar.THURSDAY, 3);
        dayOfWeek = with.get(DAY_OF_WEEK);
        hour = with.get(Calendar.HOUR_OF_DAY);
        delayInDays = dayToDelay.get(dayOfWeek);
        delayInHours = 0;

        if (delayInDays == 6 && hour == 0) {
            delayInHours = 0;
        } else {
            delayInHours = delayInDays * 24 + (24 - hour);
        }

        scheduler = Executors.newScheduledThreadPool(1); 
        //System.out.println("HOUR: " + hour + "TIME: " + delayInHours + "DAY: " + delayInDays);
        scheduler.scheduleAtFixedRate(new copyWeekViews(sce), delayInHours, 7*24, TimeUnit.HOURS);
        //scheduler.scheduleAtFixedRate(new copyWeekViews(sce), 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Scheduler Shutting down successfully " + new Date());
        scheduler.shutdown();
    }
}
