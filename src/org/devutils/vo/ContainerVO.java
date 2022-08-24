/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.vo;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author aramanathan
 */
public class ContainerVO {

    private static Map<String, JTextArea> textAreaMap = new HashMap<>();
    private static Map<String, ServerVO> playServerMap = new HashMap<>();
    private static Map<String, JScrollPane> scrollPaneMap = new HashMap<>();
    private static Map<String, UnixServerVO> unixServerMap = new HashMap<>();
    private static Map<String, ProcessVO> processMap = new HashMap<>();

    
    public static Map<String,JTextArea> getTextAreaMap() {
        return textAreaMap;
    }
    public static void setTextAreaMap(Map<String,JTextArea> aTextAreaMap) {
        textAreaMap = aTextAreaMap;
    }
    public static Map<String, ServerVO> getPlayServerMap() {
        return playServerMap;
    }
    public static void setPlayServerMap(Map<String, ServerVO> aPlayServerMap) {
        playServerMap = aPlayServerMap;
    }
    public static Map<String, JScrollPane> getScrollPaneMap() {
        return scrollPaneMap;
    }
    public static void setScrollPaneMap(Map<String, JScrollPane> aScrollPaneMap) {
        scrollPaneMap = aScrollPaneMap;
    }
    public static Map<String, UnixServerVO> getUnixServerMap() {
        return unixServerMap;
    }
    public static void setUnixServerMap(Map<String, UnixServerVO> aUnixServerMap) {
        unixServerMap = aUnixServerMap;
    }
    public static Map<String, ProcessVO> getProcessMap() {
        return processMap;
    }
    public static void setProcessMap(Map<String, ProcessVO> aProcessMap) {
        processMap = aProcessMap;
    }
}
