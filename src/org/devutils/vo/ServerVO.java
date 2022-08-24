/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.vo;

/**
 * @author Arunan Ramanathan
 * @date 23/07/2015
 */
public class ServerVO {
    private String serverType;
    private String serverName;
    private String serverPath;
    private String command;
    private String httpPort;
    private String eurekaName;
    private String eurekaPort;
    private String connectionString;
    private String debugPort;

    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    public String getServerPath() {
        return serverPath;
    }
    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }
    public String getDebugPort() {
        return debugPort;
    }
    public void setDebugPort(String debugPort) {
        this.debugPort = debugPort;
    }
    public String getHttpPort() {
        return httpPort;
    }
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }
    public String getEurekaName() {
        return eurekaName;
    }
    public void setEurekaName(String eurekaName) {
        this.eurekaName = eurekaName;
    }
    public String getEurekaPort() {
        return eurekaPort;
    }
    public void setEurekaPort(String eurekaPort) {
        this.eurekaPort = eurekaPort;
    }
    public String getConnectionString() {
        return connectionString;
    }
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getServerType() {
        return serverType;
    }
    public void setServerType(String serverType) {
        this.serverType = serverType;
    }
}
