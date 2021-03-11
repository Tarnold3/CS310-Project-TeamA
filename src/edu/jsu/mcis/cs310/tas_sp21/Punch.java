/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;
import java.sql.Timestamp;
import java.time.LocalTime;

/**
 *
 * @author Gage
 */
public class Punch {
    //"#D2C39273 CLOCKED IN: WED 09/05/2018 07:00:07"
    private String id;
    private int terminalid;
    private String badgeid;
    private Timestamp originaltimestamp;
    private int punchtypeid;
    private String adjustmenttype;

    public Punch(Badge badge, int terminalid, int punchtypeid, String badgeid, Timestamp originaltimestamp) {
        this.terminalid = terminalid;
        this.punchtypeid = punchtypeid;
        this.badgeid = badge.getId();
        this.originaltimestamp = originaltimestamp;
    }

    public String getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public Timestamp getOriginaltimestamp() {
        return originaltimestamp;
    }

    public int getPunchtypeid() {
        return punchtypeid;
    }
    
    public String formatDay(Timestamp t){
        String day = "";
        int dayNum;
        
        dayNum = t.getDay();
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
        
        String day = formatDay(originaltimestamp);
        String date = formatDate(originaltimestamp);
        String time = formatTime(originaltimestamp);
        String timeStamp = day + " " + date + " " + time;
        
        if (punchtypeid == 0){
            s.append("#").append(badgeid).append(" ");
            s.append("CLOCKED OUT: ");
            s.append(timeStamp);                              
        }

        else if (punchtypeid == 1){
            s.append("#").append(badgeid).append(" ");
            s.append("CLOCKED IN: ");
            s.append(timeStamp);     
        }

        else {
            s.append("#").append(badgeid).append(" ");
            s.append("TIMED OUT: ");
            s.append(timeStamp);     
        }

        return s.toString();
    }
    
    
    
}
