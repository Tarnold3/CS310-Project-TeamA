/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

/**
 *
 * @author Gage
 */
public class Shift {
    
    private int id;
    private String description;
    private String start;
    private String stop;
    private String interval;
    private String graceperiod;
    private String dock;
    private String lunchstart;
    private String lunchstop;
    private String lunchdeduct;
    private int lunchduration;

    public Shift(int id, String description, String start, String stop, String interval, String graceperiod, String dock, String lunchstart, String lunchstop, String lunchdeduct) {
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
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public String getInterval() {
        return interval;
    }

    public String getGraceperiod() {
        return graceperiod;
    }

    public String getDock() {
        return dock;
    }

    public String getLunchstart() {
        return lunchstart;
    }

    public String getLunchstop() {
        return lunchstop;
    }

    public String getLunchdeduct() {
        return lunchdeduct;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(description).append(": ");
        sb.append(start).append(" - ").append(stop).append(" ");
        sb.append("(510 minutes); ");
        sb.append("Lunch: ").append(lunchstart).append(" - ").append(lunchstop);
        sb.append(" (30 minutes)");
        return sb.toString();
    }
    
    
}
