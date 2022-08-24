/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.utils;

import org.devutils.vo.ContainerVO;
import org.devutils.vo.EnvironmentVO;
import org.devutils.vo.ProcessVO;
import org.devutils.vo.UnixServerVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonStreamParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aramanathan
 */
public class JsonPropertyUtility {
    static String jsonServerDetailsFilePath = "props\\servers.json";
    static String jsonEnvironmentFilePath = "props\\environment.json";
    static String jsonProcessFilePath = "props\\process.json";
    
    static {
        String systemProp = System.getProperty("sun.java.command");
        systemProp = systemProp.replaceFirst("dev-utils.jar", "");
        jsonServerDetailsFilePath = systemProp+jsonServerDetailsFilePath;
        jsonEnvironmentFilePath = systemProp+jsonEnvironmentFilePath;
        jsonProcessFilePath = systemProp+jsonProcessFilePath;
    }

    public static void saveServerDetails(String hostName, String userName, String password){
        UnixServerVO serverVO = new UnixServerVO();
        serverVO.setHostName(hostName);
        serverVO.setUserName(userName);
        serverVO.setPassword(password);

        try {
            boolean isAlreadyExists = false;
            String cumulativeJson = "";
            List<UnixServerVO> result = new ArrayList<>();
            Gson gsonBuilder = new GsonBuilder().create();
            try {
                JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonServerDetailsFilePath));
                while(parser.hasNext()) {
                    UnixServerVO vo = gsonBuilder.fromJson(parser.next(), UnixServerVO.class);

                    if (vo.getHostName().equalsIgnoreCase(hostName)) {
                        vo = serverVO;
                        isAlreadyExists = true;
                    }
                    result.add(vo);
                }
                Gson gson = new Gson();

                for (UnixServerVO serVO : result) {
                    cumulativeJson += gson.toJson(serVO);   
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JsonIOException ex) {
                System.out.println("Json io excepotion");
            }
            if (isAlreadyExists) {
                FileWriter writer = new FileWriter(jsonServerDetailsFilePath); 
                writer.write(cumulativeJson);
                writer.close();
                return;
            }
            try {
                String line = new Gson().toJson(serverVO);
                FileWriter writer = new FileWriter(jsonServerDetailsFilePath); 
                writer.write(line+cumulativeJson);
                writer.close();

            } catch (Exception e) {
              e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<UnixServerVO> parseServerDetails() {
        List<UnixServerVO> result = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonServerDetailsFilePath));
            while(parser.hasNext()) {
                UnixServerVO vo = gson.fromJson(parser.next(), UnixServerVO.class);
                result.add(vo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonIOException ex) {
            ex.printStackTrace();
            return result;
        }
        return result;
    }

    public static void createUnixServerMap() {
        Gson gson = new GsonBuilder().create();
        try {
            JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonServerDetailsFilePath));
            while(parser.hasNext()) {
                UnixServerVO vo = gson.fromJson(parser.next(), UnixServerVO.class);
                ContainerVO.getUnixServerMap().put(vo.getHostName(), vo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonIOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveEnvironmentDetails(String environmentName){
        EnvironmentVO envVO = new EnvironmentVO();
        envVO.setEnvironmentName(environmentName);

        try {
            boolean isAlreadyExists = false;
            String cumulativeJson = "";
            List<EnvironmentVO> result = new ArrayList<>();
            Gson gsonBuilder = new GsonBuilder().create();
            try {
                JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonEnvironmentFilePath));
                while(parser.hasNext()) {
                    EnvironmentVO vo = gsonBuilder.fromJson(parser.next(), EnvironmentVO.class);

                    if (vo.getEnvironmentName().equalsIgnoreCase(environmentName)) {
                        vo = envVO;
                        isAlreadyExists = true;
                    }
                    result.add(vo);
                }
                Gson gson = new Gson();

                for (EnvironmentVO serVO : result) {
                    cumulativeJson += gson.toJson(serVO);   
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JsonIOException ex) {
                System.out.println("Json io excepotion");
            }
            if (isAlreadyExists) {
                FileWriter writer = new FileWriter(jsonEnvironmentFilePath); 
                writer.write(cumulativeJson);
                writer.close();
                return;
            }
            try {
                String line = new Gson().toJson(envVO);
                FileWriter writer = new FileWriter(jsonEnvironmentFilePath); 
                writer.write(line+cumulativeJson);
                writer.close();

            } catch (Exception e) {
              e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<EnvironmentVO> parseEnvironmentDetails() {
        List<EnvironmentVO> result = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonEnvironmentFilePath));
            while(parser.hasNext()) {
                EnvironmentVO vo = gson.fromJson(parser.next(), EnvironmentVO.class);
                result.add(vo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonIOException ex) {
            ex.printStackTrace();
            return result;
        }
        return result;
    }
    
    public static void saveProcessDetails(String hostName, String environment, String port, String processName){
        ProcessVO processVO = new ProcessVO(); 
        processVO.setProcessName(processName);
        processVO.setProcessPort(port);
        processVO.setProcessEnvironment(environment);
        processVO.setProcessHost(hostName);

        try {
            boolean isAlreadyExists = false;
            String cumulativeJson = "";
            List<ProcessVO> result = new ArrayList<>();
            Gson gsonBuilder = new GsonBuilder().create();
            try {
                JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonProcessFilePath));
                while(parser.hasNext()) {
                    ProcessVO vo = gsonBuilder.fromJson(parser.next(), ProcessVO.class);

                    if (vo.getProcessName().equalsIgnoreCase(processName)) {
                        vo = processVO;
                        isAlreadyExists = true;
                    }
                    result.add(vo);
                }
                Gson gson = new Gson();

                for (ProcessVO proVO : result) {
                    cumulativeJson += gson.toJson(proVO);   
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JsonIOException ex) {
                System.out.println("Json io exception");
            }
            if (isAlreadyExists) {
                FileWriter writer = new FileWriter(jsonProcessFilePath); 
                writer.write(cumulativeJson);
                writer.close();
                return;
            }
            try {
                String line = new Gson().toJson(processVO);
                FileWriter writer = new FileWriter(jsonProcessFilePath); 
                writer.write(line+cumulativeJson);
                writer.close();

            } catch (Exception e) {
              e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<ProcessVO> parseProcessDetails() {
        List<ProcessVO> result = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        try {
            JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonProcessFilePath));
            while(parser.hasNext()) {
                ProcessVO vo = gson.fromJson(parser.next(), ProcessVO.class);
                result.add(vo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonIOException ex) {
            ex.printStackTrace();
            return result;
        }
        return result;
    }
    
    public static void createProcessMap() {
        Gson gson = new GsonBuilder().create();
        try {
            JsonStreamParser parser = new JsonStreamParser(new FileReader(jsonProcessFilePath));
            while(parser.hasNext()) {
                ProcessVO vo = gson.fromJson(parser.next(), ProcessVO.class);
                ContainerVO.getProcessMap().put(vo.getProcessName(), vo);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonPropertyUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonIOException ex) {
            ex.printStackTrace();
        }
    }
}
