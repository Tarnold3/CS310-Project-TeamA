/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;
import java.sql.Timestamp;

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
    
    public String printOriginalTimestamp(){
        
        StringBuilder s = new StringBuilder();
        
            if (punchtypeid == 0){
                s.append("#").append(badgeid).append(" ");
                s.append("CLOCKED OUT: ");
                s.append(originaltimestamp);                              
            }
            
            else if (punchtypeid == 1){
                s.append("#").append(badgeid).append(" ");
                s.append("CLOCKED IN: ");
                s.append(originaltimestamp);     
            }
            
            else {
                s.append("#").append(badgeid).append(" ");
                s.append("TIMED OUT: ");
                s.append(originaltimestamp);     
            }
            
            return s.toString();
    }
    
    
    
}
