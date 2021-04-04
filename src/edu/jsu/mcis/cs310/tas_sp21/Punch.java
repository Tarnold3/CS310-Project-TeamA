/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.GregorianCalendar;

/**
 *
 * @author Gage
 */
public class Punch {
    
    private String id;
    private int terminalid;
    private String badgeid;
    private long originaltimestamp;
    private int punchtypeid;
    private String adjustmenttype;
    
    Punch(Badge badge, int terminalid, int punchtypeid){
        this.badgeid = badge.getId();
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        
        GregorianCalendar gc = new GregorianCalendar();
        
        originaltimestamp = gc.getTimeInMillis(); //initializes timestamp to current system time
    }
    
    public void setOriginalTimeStamp(long t){
        originaltimestamp = t;
    }
    
    public String getId(){
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
    
    public int getPunchtypeid(){
        return punchtypeid;
    }
    
    public String formatDay(Timestamp t){
        String day = "";
        int dayNum = t.getDay();
        
        switch(dayNum){
            case 0:
                day = "SUN";
                break;
            case 1:
                day = "MON";
                break;
            case 2:
                day = "TUE";
                break;
            case 3:
                day = "WED";
                break;
            case 4:
                day = "THU";
                break;
            case 5:
                day = "FRI";
                break;
            case 6:
                day = "SAT";
                break;
        }
        
        return day;
    }
    
    public String formatDate(Timestamp t){
        
        String[] values = t.toString().split(" ");
        
        String[] dateValues = values[0].split("-");
        String year = dateValues[0];
        String month = dateValues[1];
        String day = dateValues[2];
        
        return month + "/" + day + "/" + year;
        
    }
    
    public String formatTime(Timestamp t){
        LocalTime lt;
        
        int hour = t.getHours();
        int minute = t.getMinutes();
        int seconds = t.getSeconds();
        
        lt = LocalTime.of(hour, minute, seconds);
        
        if(seconds == 0){
            return lt.toString() + ":00";
        }
        
        return lt.toString();
    }
    
    public String printOriginalTimestamp(){
        
        StringBuilder s = new StringBuilder();
        
        Timestamp ts = new Timestamp(originaltimestamp);
        
        String day = formatDay(ts);
        String date = formatDate(ts);
        String time = formatTime(ts);
        String timeStamp = day + " " + date + " " + time;
        
        if(punchtypeid == 0){
            s.append("#").append(badgeid).append(" ");
            s.append("CLOCKED OUT: ");
            s.append(timeStamp);
        }
        else if(punchtypeid == 1){
            s.append("#").append(badgeid).append(" ");
            s.append("CLOCKED IN: ");
            s.append(timeStamp);
        }
        else{
            s.append("#").append(badgeid).append(" ");
            s.append("TIMED OUT: ");
            s.append(timeStamp);
        }
        
        return s.toString();
    }
}
