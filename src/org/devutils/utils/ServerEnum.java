/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.utils;

/**
 * @author Arunan Ramanathan
 * @date 27/July/2015
 */
public enum ServerEnum {

    PLAYSERVER("PlayServer"), 
    ZOOKEEPER("Zookeeper"), 
    TOMCAT("Tomcat"), 
    JBOSS("JBoss"), 
    WEBLOGIC("Weblogic"); 

    public final String serverName;

    private ServerEnum(String serverName) {
        this.serverName = serverName;
    }

    static public Boolean isValid(String supposedEnumName){
        for(ServerEnum profEnum: ServerEnum.values()){
            if(profEnum.name().equalsIgnoreCase(supposedEnumName)){
                return true;
            }
        }
        return false;
    }
}
