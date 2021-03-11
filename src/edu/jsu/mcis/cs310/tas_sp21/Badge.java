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
public class Badge {
    
    private String id;
    private String description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
       
        StringBuilder s = new StringBuilder();
        
        s.append("#").append(id).append(" ");
        s.append("(").append(description).append(")");
        
        return (s.toString());
    }

    
    
    
}
