package org.devutils.utils;

import org.devutils.vo.ContainerVO;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 * @author Arunan Ramanathan
 * @date 23/07/2015
 */
public class IOThreadHandler extends Thread {

    private InputStream inputStream;
    private String serverName;
    private StringBuilder output = new StringBuilder();

    public IOThreadHandler(InputStream inputStream, String serverName) {
        this.inputStream = inputStream;
        this.serverName = serverName;
    }

    public void run() {
        Scanner br = null;
        try {
            br = new Scanner(new InputStreamReader(inputStream));
            String line = null;
            while (br.hasNextLine()) {
                line = br.nextLine();
                JTextArea serverTextArea = ContainerVO.getTextAreaMap().get(serverName);
                serverTextArea.append(line +"\n");
            }
        } finally {
            br.close();
        }
    }
    public StringBuilder getOutput() {
        return output;
    }
}
