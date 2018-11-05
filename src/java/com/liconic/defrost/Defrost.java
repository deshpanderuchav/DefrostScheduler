/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liconic.defrost;

import java.util.Calendar;
import java.util.Timer;

/**
 *
 * @author Rucha Deshpande
 */
public class Defrost {
     private static Defrost instance;
     public static Defrost getInstance(){
        
        if(instance == null)
            instance = new Defrost();
        return instance;
    }
     
     public void RunDefrost(){
         Calendar now = Calendar.getInstance();
         Calendar calendar = Calendar.getInstance();
         int hour,minute;
         hour = 00;
         minute = 00;
         
         calendar.set(Calendar.HOUR_OF_DAY, hour);
         calendar.set(Calendar.MINUTE, minute);
         calendar.set(Calendar.SECOND, 0);     
         if (now.get(Calendar.HOUR_OF_DAY) >= hour){
                calendar.add(Calendar.DATE, 1);
         }
         System.out.println("Defrost Time: "+calendar.getTime());
         DefrostTimer defrostTimer = new DefrostTimer(this);
         Timer timer = new Timer();
         timer.schedule(defrostTimer, calendar.getTime(), 1000*60*60*24);
      }
}
