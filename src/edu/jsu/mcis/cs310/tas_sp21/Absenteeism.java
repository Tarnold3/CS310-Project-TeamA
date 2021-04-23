/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Jayden
 */
public class Absenteeism {
    
    private String badgeId;
    private long payPeriod;
    private double percentage;
    
    public Absenteeism(String badgeId, long payPeriod, double percentage){
        this.badgeId = badgeId;
        this.percentage = percentage;
        
        GregorianCalendar startPayPeriod = new GregorianCalendar();
        startPayPeriod.setTimeInMillis(payPeriod);
        startPayPeriod.set(Calendar.DAY_OF_WEEK, 1);
        startPayPeriod.set(Calendar.HOUR_OF_DAY, 0);
        startPayPeriod.set(Calendar.MINUTE, 0);
        startPayPeriod.set(Calendar.SECOND, 0);
        startPayPeriod.set(Calendar.MILLISECOND, 0);
        
        this.payPeriod = startPayPeriod.getTimeInMillis();
    }
    
    public void setBadgeId(String badgeId){
        this.badgeId = badgeId;
    }
    
    public void setPayPeriod(long payPeriod){
        this.payPeriod = payPeriod;
    }
    
    public void setPercentage(double percentage){
        this.percentage = percentage;
    }
    
    public String getBadgeId(){
        return badgeId;
    }
    
    public long getPayPeriod(){
        return payPeriod;
    }
    
    public double getPercentage(){
        return percentage;
    }
    
    
    
    @Override
    public String toString(){
        
        StringBuilder s = new StringBuilder();
        
        //Make a Timestamp object from the long integer
        Timestamp timestamp = new Timestamp(payPeriod);
        
        //Split the toString of the ts object to store the date only
        String[] timeValues = timestamp.toString().split(" ");
        String dateValue = timeValues[0];
        
        String strPercentage = String.format("%.2f", percentage); //format percentage to 2 decimal places
        String badgeIdValue = "#" + badgeId;
        String payPeriodValue = " (Pay Period Starting " + dateValue + "): ";
        String percentageValue = strPercentage + "%";
        
        s.append(badgeIdValue);
        s.append(payPeriodValue);
        s.append(percentageValue);
        
        return s.toString();
        
    }
}