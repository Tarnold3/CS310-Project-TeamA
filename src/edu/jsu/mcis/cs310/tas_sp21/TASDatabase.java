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
    Connection conn;
    
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
                
                c.close();
                
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
        
        return badge;
    }
    
    public Shift getShift(int shiftID){
        Shift shift;
        
        String query;
        
        //Query the database
        
        return null;
    }
    
    public Shift getShift(Badge badge){
        Shift shift;
        
        String query;
        
        //Query the database
        
        return null;
    }
    
    public Punch getPunch(int punchID){
        Punch punch = null;
        
        PreparedStatement pstSelect = null;
        ResultSet resultset = null;
        ResultSetMetaData metadata = null;
        String query, tIdLabel, bIdLabel, ptIdLabel, terminalId, badgeId, 
                punchTypeId;
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
                    terminalId = resultset.getString(tIdLabel);
                    badgeId = resultset.getString(bIdLabel);
                    punchTypeId = resultset.getString(ptIdLabel);
                    
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
        
        
        return punch;
    }
    
}
