/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.jsu.mcis.cs310.tas_sp21;

import java.sql.Timestamp;
import java.util.Locale;
import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;
import java.time.ZoneOffset;
/**
 *
 * @author Gage
 */
public class Punch {
    
    private int id;
    private int terminalid;
    private String badgeid;
    private long originaltimestamp;
    private int punchtypeid;
    private String adjustmenttype;
    private long adjustedtimestamp;
    
    Punch(Badge badge, int terminalid, int punchtypeid){
        this.badgeid = badge.getId();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.adjustedtimestamp = 0;
        this.adjustmenttype = null;
        GregorianCalendar gc = new GregorianCalendar();
        
        originaltimestamp = gc.getTimeInMillis(); //initializes timestamp to current system time
        
        this.id = 0;
    }
    
    public int getId(){
        return id;
    }
    
    public int getTerminalid(){
        return terminalid;
    }
    
    public String getBadgeid(){
        return badgeid;
    }
    
    public long getOriginaltimestamp(){
        return originaltimestamp;
    }
    
    public long getAdjustedtimestamp(){
        return adjustedtimestamp;   
    }
    
    public int getPunchtypeid(){
        return punchtypeid;
    }
     
    public String getAdjustmenttype(){
        return adjustmenttype;
    }
    
     public void setOriginalTimeStamp(long originaltimestamp){
        this.originaltimestamp = originaltimestamp;
    }
     
    public void setId(int id){
        this.id = id;
    }
    
    public void setAdjustmenttype(String adjustmenttype){
        this.adjustmenttype = adjustmenttype;
    }
    
    public String printTimestamp(Boolean adjustedTimestamp){
        
        StringBuilder s = new StringBuilder();
        
        Date date;
        
        if(adjustedTimestamp){
            date = new Date(adjustedtimestamp);
        }
        else{
            date = new Date(originaltimestamp);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MM/dd/yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("CST"));
        String strDate = formatter.format(date);
        
        switch (punchtypeid) {
            case 1:
                s.append("#").append(badgeid).append(" ");
                s.append("CLOCKED IN: ");
                break;
            case 0:
                s.append("#").append(badgeid).append(" ");
                s.append("CLOCKED OUT: ");
                break;
            default:
                s.append("#").append(badgeid).append(" ");
                s.append("TIMED OUT: ");
                break;
        }
 
        s.append(strDate.toUpperCase());
        
        if(adjustedTimestamp){
            s.append(" ").append("(").append(adjustmenttype).append(")");
        }
        return s.toString();
    }
    
    public String printOriginalTimestamp(){
        return printTimestamp(false);
    }
    
    public String printAdjustedTimestamp(){
        return printTimestamp(true);
    }
    
    public void adjust(Shift s){
        Date date = new Date(originaltimestamp);
        
        SimpleDateFormat format = new SimpleDateFormat("EEE");
        String sDate = format.format(date).toUpperCase();
        
        LocalTime localt = Instant.ofEpochMilli(originaltimestamp).atZone(ZoneId.systemDefault()).toLocalTime();
        
        switch(this.punchtypeid){
            case 0:
                //This is for the weekdays
                if(!"SAT".equals(sDate) && !"SUN".equals(sDate)){
                    //This is for Late Clock-Out
                    if(localt.isAfter(s.getStop()) && (Math.abs(s.getStop().toSecondOfDay() - localt.toSecondOfDay()) <= s.getInterval() * 60)){
                        adjustedtimestamp = originaltimestamp - 1000 * Math.abs(localt.toSecondOfDay() - (s.getStop().toSecondOfDay()));
                        adjustmenttype = "Shift Stop";
                    }
                    // This is for Late Lunch Start
                    else if(localt.isAfter(s.getLunchStart()) && localt.isBefore(s.getLunchStop())){
                        adjustedtimestamp = originaltimestamp - 1000 * Math.abs(localt.toSecondOfDay() - s.getLunchStart().toSecondOfDay());
                        adjustmenttype = "Lunch Start";
                    }
                    //This is for Early Clock-Out
                    else if(localt.isBefore(s.getStop()) && localt.isAfter(s.getLunchStop()) && (Math.abs(s.getStop().toSecondOfDay() - localt.toSecondOfDay())) <= s.getGracePeriod() * 60){
                        adjustedtimestamp = originaltimestamp + 1000 * Math.abs(s.getStop().toSecondOfDay() - localt.toSecondOfDay());
                        adjustmenttype = "Shift Stop";
                    }
                    //This is for Dock 15 minutes
                    else if(localt.isBefore(s.getStop()) && localt.isAfter(s.getLunchStop()) && (Math.abs(s.getStop().toSecondOfDay() - localt.toSecondOfDay())) > s.getGracePeriod() * 60 && (Math.abs(s.getStop().toSecondOfDay() - localt.toSecondOfDay()) <= s.getDock() * 60)){
                        adjustedtimestamp = (originaltimestamp + (Math.abs(localt.toSecondOfDay() - s.getStop().toSecondOfDay())) * 1000) - (s.getDock() * 60 * 1000);
                        adjustmenttype = "Shift Dock";
                    }
                    //This is for none. No adjustment needed
                    else if((localt.getMinute() % s.getInterval()) == 0){
                        adjustedtimestamp = originaltimestamp - (localt.getSecond() * 1000);
                        adjustmenttype = "None";
                    }
                    //This is for Interval Round that is outside shift rules
                    else{
                        intervalRound(localt, s);
                    }
                }
                //Interval round for weekend
                else if("SAT".equals(sDate) || "SUN".equals(sDate)){
                    intervalRound(localt, s);
                }
                break;
                
              case 1: 
                // On Weekdays
                if( !"SAT".equals(sDate) && !"SUN".equals(sDate) ){
                     // EARLY CLOCK IN
                    if ( localt.isBefore(s.getStart()) && (Math.abs(s.getStart().toSecondOfDay() - localt.toSecondOfDay())  <= s.getInterval() * 60)){
                       adjustedtimestamp = originaltimestamp + 1000 * Math.abs(s.getStart().toSecondOfDay() - localt.toSecondOfDay());
                       adjustmenttype = "Shift Start";
                    }
                    //This is for LUNCH STOP
                    else if (localt.isAfter(s.getLunchStart())  &&  localt.isBefore(s.getLunchStop())){
                       adjustedtimestamp = originaltimestamp + 1000 * Math.abs(s.getLunchStop().toSecondOfDay() - localt.toSecondOfDay()); 
                       adjustmenttype = "Lunch Stop";
                    }  
                    //This is for LATE CLOCK IN 
                    else if ( localt.isAfter(s.getStart()) && localt.isBefore(s.getLunchStart()) && ((Math.abs( localt.toSecondOfDay() - s.getStart().toSecondOfDay())  <= s.getGracePeriod() * 60))){
                       adjustedtimestamp = originaltimestamp - 1000 * Math.abs(localt.toSecondOfDay() - s.getStart().toSecondOfDay()); 
                       adjustmenttype = "Shift Start";
                    }
                    //This is for DOCK 15 min 
                    else if((localt.isAfter(s.getStart())) && (localt.isBefore(s.getLunchStart())) && (Math.abs(localt.toSecondOfDay() - s.getStart().toSecondOfDay())) > s.getGracePeriod() * 60 && (Math.abs(s.getStart().toSecondOfDay() - localt.toSecondOfDay()) <= s.getDock() * 60)){
                        adjustedtimestamp = (originaltimestamp - (Math.abs(localt.toSecondOfDay() - s.getStart().toSecondOfDay())) * 1000) + (s.getDock() * 60 * 1000);
                        adjustmenttype = "Shift Dock";
                    }
                    // none
                    else if ((localt.getMinute() % s.getInterval()) == 0 ){
                        adjustedtimestamp = originaltimestamp - (localt.getSecond() * 1000);
                        adjustmenttype = "None";
                    } 
                    // INTERVAL ROUNDING a
                    else{
                     intervalRound(localt, s);
                    }            
                }                
                // WEEKEND REQUIRES ONLY INTERVAL ROUNDING
                else if( "SAT".equals(sDate) || "SUN".equals(sDate) ){
                     intervalRound(localt, s);
                }              
                break;                
            default:
                break;
            }
        }
    public void intervalRound(LocalTime localt, Shift s){
        long r = localt.getMinute() % s.getInterval();
        int half = s.getInterval()/2;
        if (r != 0){
            if(r < half){
                adjustedtimestamp = originaltimestamp - (r*60*1000);          
            }
            else if (r >= half){
                adjustedtimestamp = originaltimestamp +((s.getInterval() - r)*60*1000);                
            }
            adjustedtimestamp = adjustedtimestamp - (localt.getSecond() *1000);
            adjustmenttype = "Interval Round";
        }
    }
}

