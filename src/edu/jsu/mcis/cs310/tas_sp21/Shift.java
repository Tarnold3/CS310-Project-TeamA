/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

import java.time.LocalTime;
import java.time.temporal.*;
/**
 *
 * @author Tucker
 * @author Andrew
 * @author Gage
 */
public class Shift {
    private int id = 0;
    private String description = "";
    private LocalTime start = null;
    private LocalTime stop = null;
    private int interval = 0;
    private int graceperiod = 0;
    private int dock = 0;
    private LocalTime lunchstart = null;
    private LocalTime lunchstop = null;
    private int lunchdeduct = 0;
    private long lunchduration = 0;
    
    public Shift(int id, String description, LocalTime start, LocalTime stop, 
            int interval, int graceperiod, int dock, LocalTime lunchstart, 
            LocalTime lunchstop, int lunchdeduct){
        
        this.id = id;
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.interval = interval;
        this.graceperiod = graceperiod;
        this.dock = dock;
        this.lunchstart = lunchstart;
        this.lunchstop = lunchstop;
        this.lunchdeduct = lunchdeduct;
        lunchduration = lunchstart.until(lunchstop, ChronoUnit.MINUTES);
        
    }
    
    public int getId(){
        return id;
    }
    
    public String getDescription(){
        return description;
    }
    
    public LocalTime getStart(){
        return start;
    }
    
    public LocalTime getStop(){
        return stop;
    }
    
    public int getInterval(){
        return interval;
    }
    
    public int getGracePeriod(){
        return graceperiod;
    }
    
    public int getDock(){
        return dock;
    }
    
    public LocalTime getLunchStart(){
        return lunchstart;
    }
    
    public LocalTime getLunchStop(){
        return lunchstop;
    }
    
    public int getLunchDeduct(){
        return lunchdeduct;
    }
    
    public long getLunchDuration(){
        return lunchduration;
    }
    
    
    @Override
    public String toString(){
        
        StringBuilder strb = new StringBuilder();
        
        strb.append(description).append(": ").append(start).append(" - ").append(stop);
        strb.append(" (").append(start.until(stop, ChronoUnit.MINUTES)).append(" minutes); ").append("Lunch: ");
        strb.append(lunchstart).append(" - ").append(lunchstop);
        strb.append(" (").append(lunchstart.until(lunchstop, ChronoUnit.MINUTES)).append(" minutes)");
        
        return strb.toString();
        
    }
}
