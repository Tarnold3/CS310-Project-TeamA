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

/**
 *
 * @author Tucker
 */
public class TASDatabase {
    private Connection conn;
    
    public TASDatabase(){
        
        try{
            String server = ("jdbc:mysql://localhost/p2_test");
            String username = "CS310TeamA";
            String password = "passcs310";
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
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
                query = "SELECT * FROM badge WHERE id=" + badgeID;
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    metadata = resultset.getMetaData();
                    
                    //Store column name for badge description
                    columnName = metadata.getColumnLabel(1);
                    
                    //Retrieve and store badge information
                    badgeDesc = resultset.getString(columnName);
                    
                    //Populate badge object with badge information
                    badge = new Badge(badgeID, badgeDesc);
                }
                
            }
            
        }
        catch(Exception e){
            System.err.println(e.toString());
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
        
        String shiftDesc, shiftStart, shiftStop, shiftInterval, shiftGP,
                shiftDock, lunchStart, lunchStop, lunchDeduct;
        
        int shiftId;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database
                query = "SELECT * FROM shift WHERE id=" + shiftID;
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    metadata = resultset.getMetaData();
                    
                    //Store column labels
                    idLabel = metadata.getColumnLabel(0);
                    descLabel = metadata.getColumnLabel(1);
                    startLabel = metadata.getColumnLabel(2);
                    stopLabel = metadata.getColumnLabel(3);
                    intervalLabel = metadata.getColumnLabel(4);
                    gpLabel = metadata.getColumnLabel(5);
                    dockLabel = metadata.getColumnLabel(6);
                    lunchStartLabel = metadata.getColumnLabel(7);
                    lunchStopLabel = metadata.getColumnLabel(8);
                    lunchDeductLabel = metadata.getColumnLabel(9);   
                    
                    
                    //Retrieve and store shift information
                    shiftId = resultset.getInt(idLabel);
                    shiftDesc = resultset.getString(descLabel);
                    shiftStart = resultset.getString(startLabel);
                    shiftStop = resultset.getString(stopLabel);
                    shiftInterval = resultset.getString(intervalLabel);
                    shiftGP = resultset.getString(gpLabel);
                    shiftDock = resultset.getString(dockLabel);
                    lunchStart = resultset.getString(lunchStartLabel);
                    lunchStop = resultset.getString(lunchStopLabel);
                    lunchDeduct = resultset.getString(lunchDeductLabel);
                    
                    
                    //Populate shift object with shift information
                    shift = new Shift(shiftId, shiftDesc, shiftStart, shiftStop, 
                            shiftInterval, shiftGP, shiftDock, lunchStart, 
                    lunchStop, lunchDeduct);
                    
                }
                
            }
            
        }
        catch(Exception e){
            System.err.println(e.toString());
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
                query = "SELECT * FROM employee WHERE badgeid=" + badgeId;
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                if(hasResults){
                    
                    //Retrieve ResultSet Information for employee table
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
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
            System.err.println(e.toString());
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
        
        String query, tIdLabel, bIdLabel, ptIdLabel, badgeId;
        int terminalId, punchTypeId;
        boolean hasResults;
        
        try{
            
            if(conn.isValid(0)){
                
                //Query the database
                query = "SELECT * FROM badge WHERE id=" + punchID;
                pstSelect = conn.prepareStatement(query);
                hasResults = pstSelect.execute();
                
                
                if(hasResults){
                    //Retrieve ResultSet Information
                    resultset = pstSelect.getResultSet();
                    
                    //Retrieve Metadata
                    metadata = resultset.getMetaData();
                    
                    //Store column labels
                    tIdLabel = metadata.getColumnLabel(1);
                    bIdLabel = metadata.getColumnLabel(2);
                    ptIdLabel = metadata.getColumnLabel(4);
                    
                    //Retrieve and store punch information
                    terminalId = resultset.getInt(tIdLabel);
                    badgeId = resultset.getString(bIdLabel);
                    punchTypeId = resultset.getInt(ptIdLabel);
                    
                    //Get badge information from badge id and store in a badge object
                    Badge badge = getBadge(badgeId);
                    
                    //Populate the punch object with punch information
                    punch = new Punch(badge, terminalId, punchTypeId);
                }
                
            }
            
        }
        catch(Exception e){
            System.err.println(e.toString());
        }
        finally{
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstSelect != null) { try { pstSelect.close(); pstSelect = null; } catch (Exception e) {} }
        }
        
        
        return punch;
    }
    
}
