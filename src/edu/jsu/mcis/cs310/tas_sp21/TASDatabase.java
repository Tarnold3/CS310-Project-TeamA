/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs310.tas_sp21;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.time.LocalTime;

/**
 *
 * @author Tucker
 */
public class TASDatabase {
    private Connection conn;
    
    public TASDatabase(){
        
        try{
            String server = ("jdbc:mysql://localhost/TAS?autoReconnect=true&useSSL=false");
            String username = "CS310TeamA";
            String password = "passcs310";
            
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            conn = DriverManager.getConnection(server, username, password);
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        
    }
    
    public void close(Connection c){
        
        try{
            
            if(c != null){
                
                c.close(); //close connection
                
            }
            
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        
    }
    
    public Badge getBadge(String badgeID){
        Badge badge = null;
        
        PreparedStatement pstSelect = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        String query, columnName, badgeDesc;
        boolean hasResults;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database
                query = "SELECT * FROM badge WHERE id='" + badgeID + "'";
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    resultset.first();
                    metadata = resultset.getMetaData();
                    
                    //Store column name for badge description
                    columnName = metadata.getColumnLabel(2);
                    
                    //Retrieve and store badge information
                    badgeDesc = resultset.getString(columnName);
                    
                    //Populate badge object with badge information
                    badge = new Badge(badgeID, badgeDesc);
                    
                }
                
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
        }
        
        return badge;
    }
    
    public Shift getShift(int shiftID){
        Shift shift = null;
        
        boolean hasResults;
        PreparedStatement pstSelect = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        String query;
        
        String idLabel, descLabel, startLabel, stopLabel, intervalLabel, gpLabel,
                dockLabel, lunchStartLabel, lunchStopLabel, lunchDeductLabel;
        
        String shiftDesc, shiftStart, shiftStop, lunchStart, lunchStop;
        
        int shiftId, shiftInterval, shiftGP, shiftDock, lunchDeduct;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database
                query = "SELECT * FROM shift WHERE id='" + shiftID + "'";
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    resultset.first();
                    metadata = resultset.getMetaData();
                    
                    //Store column labels
                    idLabel = metadata.getColumnLabel(1);
                    descLabel = metadata.getColumnLabel(2);
                    startLabel = metadata.getColumnLabel(3);
                    stopLabel = metadata.getColumnLabel(4);
                    intervalLabel = metadata.getColumnLabel(5);
                    gpLabel = metadata.getColumnLabel(6);
                    dockLabel = metadata.getColumnLabel(7);
                    lunchStartLabel = metadata.getColumnLabel(8);
                    lunchStopLabel = metadata.getColumnLabel(9);
                    lunchDeductLabel = metadata.getColumnLabel(10); 
                    
                    
                    //Retrieve and store shift information
                    shiftId = resultset.getInt(idLabel);
                    shiftDesc = resultset.getString(descLabel);
                    shiftStart = resultset.getString(startLabel);
                    shiftStop = resultset.getString(stopLabel);
                    shiftInterval = resultset.getInt(intervalLabel);
                    shiftGP = resultset.getInt(gpLabel);
                    shiftDock = resultset.getInt(dockLabel);
                    lunchStart = resultset.getString(lunchStartLabel);
                    lunchStop = resultset.getString(lunchStopLabel);
                    lunchDeduct = resultset.getInt(lunchDeductLabel);
                    
                    //Convert times into LocalTime objects
                    String[] values1 = shiftStart.split(":");
                    String[] values2 = shiftStop.split(":");
                    String[] values3 = lunchStart.split(":");
                    String[] values4 = lunchStop.split(":");
                    
                    int startHour = Integer.parseInt(values1[0]);
                    int startMin = Integer.parseInt(values1[1]);
                    LocalTime start = LocalTime.of(startHour, startMin);
                    
                    int stopHour = Integer.parseInt(values2[0]);
                    int stopMin = Integer.parseInt(values2[1]);
                    LocalTime stop = LocalTime.of(stopHour, stopMin);
                    
                    int lunchStartHour = Integer.parseInt(values3[0]);
                    int lunchStartMin = Integer.parseInt(values3[1]);
                    LocalTime lunchStartObj = LocalTime.of(lunchStartHour, lunchStartMin);
                    
                    int lunchStopHour = Integer.parseInt(values4[0]);
                    int lunchStopMin = Integer.parseInt(values4[1]);
                    LocalTime lunchStopObj = LocalTime.of(lunchStopHour, lunchStopMin);
                    
                    
                    //Populate shift object with shift information
                    shift = new Shift(shiftId, shiftDesc, start, stop, 
                            shiftInterval, shiftGP, shiftDock, lunchStartObj, 
                    lunchStopObj, lunchDeduct);
                    
                }
                
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
        }
       
        return shift;
    }
    
    public Shift getShift(Badge badge){
        Shift shift = null;
        
        //test variable
        boolean hasResults;
        
        //database objects
        PreparedStatement pstSelect = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        
        //query variable
        String query;
        
        String badgeId = badge.getId(); //retrieves badge id to connect employee
        
        String shiftIdLabel; //employee table column label
        
        int shiftId;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database for employee table
                query = "SELECT * FROM employee WHERE badgeid='" + badgeId + "'";
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    
                    //Retrieve ResultSet Information for employee table
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    resultset.first();
                    metadata = resultset.getMetaData();
                    
                    //Store shiftid column label
                    shiftIdLabel = metadata.getColumnLabel(7);
                    
                    //Retrieve and store shift id
                    shiftId = resultset.getInt(shiftIdLabel);
                    
                    
                    //Query the database for shift table and populate shift object with shift information
                    shift = getShift(shiftId);
                     
                }
                
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
        }
        
        return shift;
    }
    
    public Punch getPunch(int punchID){
        Punch punch = null;
        
        PreparedStatement pstSelect = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        
        String query, tIdLabel, bIdLabel, timeLabel, ptIdLabel, badgeId;
        int terminalId, punchTypeId;
        Timestamp timeStamp;
        boolean hasResults;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database
                query = "SELECT * FROM punch WHERE id='" + punchID + "'";
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    resultset.first();
                    metadata = resultset.getMetaData();
                    
                    //Store column labels
                    tIdLabel = metadata.getColumnLabel(2);
                    bIdLabel = metadata.getColumnLabel(3);
                    timeLabel = metadata.getColumnLabel(4);
                    ptIdLabel = metadata.getColumnLabel(5);
                    
                    //Retrieve and store punch information
                    terminalId = resultset.getInt(tIdLabel);
                    badgeId = resultset.getString(bIdLabel);
                    timeStamp = resultset.getTimestamp(timeLabel);
                    punchTypeId = resultset.getInt(ptIdLabel);
                    
                    //Get badge information from badge id and store in a badge object
                    Badge badge = getBadge(badgeId);
                    
                    //Populate the punch object with punch information
                    punch = new Punch(badge, terminalId, punchTypeId);
                    
                }
                
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
        }
        
        return punch;
    }
    
}
