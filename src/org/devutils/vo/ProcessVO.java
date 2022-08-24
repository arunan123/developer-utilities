/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.vo;

/**
 * @author Arunan Ramanathan
 * @date 30/July/2015
 */
public class ProcessVO {
    private String processName;
    private String processPort;
    private String processHost;
    private String processEnvironment;

    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getProcessPort() {
        return processPort;
    }
    public void setProcessPort(String processPort) {
        this.processPort = processPort;
    }
    public String getProcessHost() {
        return processHost;
    }
    public void setProcessHost(String processHost) {
        this.processHost = processHost;
    }
    public String getProcessEnvironment() {
        return processEnvironment;
    }
    public void setProcessEnvironment(String processEnvironment) {
        this.processEnvironment = processEnvironment;
    }
}
