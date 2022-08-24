package org.devutils.serverops;

import org.devutils.ui.DevUtilsUI;
import org.devutils.utils.IOThreadHandler;
import org.devutils.utils.ServerEnum;
import org.devutils.vo.ContainerVO;
import org.devutils.vo.ServerVO;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * @author Arunan Ramanathan
 * @date 23/07/2015
 */
public class ServerOperations {

    public void startServer(String serverName) {

        //TODO: Get the ServerVO from Map
        Map<String, ServerVO> playServerMap = ContainerVO.getPlayServerMap();
        ServerVO playServerVO = playServerMap.get(serverName);

        SwingUtilities.invokeLater(new Runnable() {

            String[] command = {"CMD", "/C", playServerVO.getCommand()};
            public void run() {
                try {
                    ProcessBuilder processbuilder = new ProcessBuilder(command);
                    processbuilder.directory(new File(playServerVO.getServerPath()));
                    Process process = processbuilder.start();

                    IOThreadHandler thread = new IOThreadHandler(process.getInputStream(), serverName);
                    thread.start();

                } catch (IOException ex) {
                    Logger.getLogger(DevUtilsUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void stopServer(String serverName) {

        Map<String, ServerVO> playServerMap = ContainerVO.getPlayServerMap();
        ServerVO playServerVO = playServerMap.get(serverName);

        String command = playServerVO.getCommand();
        String[] commands = command.split(" ");
        String port = null;

        if (playServerVO.getServerType().equalsIgnoreCase(ServerEnum.PLAYSERVER.serverName)) {
            for (String comm : commands) {
                if (comm.startsWith("-Dhttp.port")) {
                    int startIndex = comm.indexOf("=");
                    port = comm.substring(startIndex+1, startIndex+5);
                    break;
                }
            }
        } else {
            port = playServerVO.getHttpPort();
        }
        //TODO: Construct the URL
        String stopCommand = "netstat -aon |find /i \"listening\" |find \"" + port + "\"";

        SwingUtilities.invokeLater(new Runnable() {

            String[] command = {"CMD", "/C", stopCommand};

            public void run() {
                try {
                    ProcessBuilder processbuilder = new ProcessBuilder(command);
                    Process process = processbuilder.start();

                    Scanner br = new Scanner(new InputStreamReader(process.getInputStream()));
                    String line = null;
                    if (br.hasNextLine()) {
                        line = br.nextLine();
                    }

                    String[] breakLine = line.split(" ");
                    String PID = breakLine[breakLine.length-1];

                    String[] commandToKill = {"CMD", "/C", "taskkill /F /PID " + PID};

                    ProcessBuilder processbuilderKill = new ProcessBuilder(commandToKill);
                    processbuilderKill.start();

                } catch (IOException ex) {
                    Logger.getLogger(DevUtilsUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        DevUtilsUI.forkServerTabbedPane.remove(ContainerVO.getScrollPaneMap().get(serverName));
        ContainerVO.getTextAreaMap().remove(serverName);
    }
    
    public void makeCurlRequest(String serverName) {

        Map<String, ServerVO> playServerMap = ContainerVO.getPlayServerMap();
        ServerVO playServerVO = playServerMap.get(serverName);

        String command = playServerVO.getCommand();
        String[] commands = command.split(" ");
        String port = null;

        for (String comm : commands) {
            if (comm.startsWith("-Dhttp.port")) {
                int startIndex = comm.indexOf("=");
                port = comm.substring(startIndex+1, startIndex+5);
                break;
            }
        }
        //TODO: Construct the URL
        String curlCommand = "curl localhost:" + port;

        SwingUtilities.invokeLater(new Runnable() {

            String[] command = {"CMD", "/C", curlCommand};

            public void run() {
                try {
                    ProcessBuilder processbuilder = new ProcessBuilder(command);
                    Process process = processbuilder.start();
                } catch (IOException ex) {
                    Logger.getLogger(DevUtilsUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
