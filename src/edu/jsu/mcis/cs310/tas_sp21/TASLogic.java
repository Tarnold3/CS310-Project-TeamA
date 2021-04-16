/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author Andrew
 */
public class TASLogic {
    
    public static int calculateTotalMinutes(ArrayList<Punch>dailypunchlist, Shift shift){
        
        int minminutes = shift.getLunchDeduct();
        long lunchdur = shift.getLunchDuration();
        
        int sizepl = dailypunchlist.size();        

        Punch firstp = dailypunchlist.get(0);
        
        long firstday = firstp.getAdjustedtimestamp();
                
        GregorianCalendar beginTimestamp = new GregorianCalendar(); 
        beginTimestamp.setTimeInMillis(firstday);
        beginTimestamp.set(Calendar.HOUR_OF_DAY, 0);
        beginTimestamp.set(Calendar.MINUTE, 0);
        beginTimestamp.set(Calendar.SECOND, 0);
        beginTimestamp.set(Calendar.MILLISECOND, 0);
        long datemilli = beginTimestamp.getTimeInMillis();
                        
        
        LocalTime lstart = shift.getLunchStart();        
        
        int starthour = lstart.getHour();                   //lunch start
        int startminute = lstart.getMinute();

        
        long lstartmilli = (starthour * 3600000) + (startminute * 60000);  
        lstartmilli += datemilli;
               
        
        int a = 0;
        int lunchstrt = 0;
        
        while(a < sizepl)   //iterates through dailypunchlist and determines if there is a lunch start
        {
            Punch indpunch = dailypunchlist.get(a);
            long indp = indpunch.getAdjustedtimestamp();
            GregorianCalendar greg = new GregorianCalendar();
            greg.setTimeInMillis(indp);       
            long lpun = greg.getTimeInMillis();
            int indtype = indpunch.getPunchtypeid();
            
            if((lpun >= lstartmilli) && (lpun <= (lstartmilli + 60000)))
            {
                Punch lstartpunch = indpunch;
                
                if(indtype == 0)
                {
                    lunchstrt = 1;
                }
            }                  
            a++;            
        }
        
        int b = 0;
        
        long totalmilli = 0;
        
        while(b < sizepl)               //get punches and time in minutes
        {

            Punch tpunch = dailypunchlist.get(b);
            long tp = tpunch.getAdjustedtimestamp();
            GregorianCalendar gregtp = new GregorianCalendar();
            gregtp.setTimeInMillis(tp);       
            long ltpun = gregtp.getTimeInMillis();
            int typetp = tpunch.getPunchtypeid();
            
            Punch nxtpunch = dailypunchlist.get(b+1);
            long nxtp = nxtpunch.getAdjustedtimestamp();
            GregorianCalendar gregnxtp = new GregorianCalendar();
            gregnxtp.setTimeInMillis(nxtp);       
            long lnxtpun = gregnxtp.getTimeInMillis();
            int typenxtp = nxtpunch.getPunchtypeid();
            


            if((typetp == 1) && (typenxtp == 0)) //if first is clock in and second is clockout
            {
                long tdiff = lnxtpun - ltpun;
                totalmilli += tdiff;
            }
            else if((typetp == 1) && (typenxtp == 2)) //if second is a time out then no time is added
            {
                long tdiff = 0;
            }
            
            b+=2;
        }
        
        int totalmin = (int)(totalmilli / 60000);
        
        if((totalmin > minminutes) && (lunchstrt == 0)) //deducts lunch if requirements are not met
        {
            totalmin -= lunchdur;
        }
        
        return totalmin;
    }
}
