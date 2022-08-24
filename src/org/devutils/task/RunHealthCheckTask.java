/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.task;

import static org.devutils.ui.DevUtilsUI.*;
import static org.devutils.ui.DevUtilsUI.environmentEditorPane;
import static org.devutils.ui.DevUtilsUI.listEnvironmentComboBox;
import static org.devutils.ui.DevUtilsUI.removeUIChangesForRunHealthCheck;
import org.devutils.unixops.Authenticator;
import org.devutils.utils.JsonPropertyUtility;
import org.devutils.vo.ContainerVO;
import org.devutils.vo.ProcessVO;
import org.devutils.vo.UnixServerVO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author aramanathan
 */
public class RunHealthCheckTask extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        addUIChangesForRunHealthCheck();
        String selectedEnvironment = (String) listEnvironmentComboBox.getSelectedItem();
        List<ProcessVO> environmentProcessVOs = new ArrayList(10);
        List<ProcessVO> processVOs = JsonPropertyUtility.parseProcessDetails();
        for (ProcessVO processVO : processVOs) {
            if (processVO.getProcessEnvironment().equalsIgnoreCase(selectedEnvironment)) {
                environmentProcessVOs.add(processVO);
            }
        }
        String htmlContent = "";

        for (ProcessVO envProcessVO : environmentProcessVOs) {
            UnixServerVO unixVO = ContainerVO.getUnixServerMap().get(envProcessVO.getProcessHost());
            
            try {
                boolean isRunning = Authenticator.isProcessRunning(unixVO.getHostName(), 
                                                    unixVO.getUserName(), unixVO.getPassword(), 
                                                    envProcessVO.getProcessPort());
                if (isRunning) {
                    htmlContent += envProcessVO.getProcessName() + 
                            " : <font color=\"green\">is running</font> <br><br>";
                    remoteProcessTextArea.append(envProcessVO.getProcessName() +" : is running \n\n");
                } else {
                     htmlContent += envProcessVO.getProcessName() +
                             " : <font color=\"red\">is NOT running</font> <br><br>";
                     remoteProcessTextArea.append(envProcessVO.getProcessName() +" : is NOT running \n\n");
                    //htmlContent += envProcessVO.getProcessName() + " : <font color=\"red\">is Not running</font> <br><br>";
                }
            } catch (Exception ex) {
                htmlContent += envProcessVO.getProcessName() + 
                        " : <font color=\"blue\">Not able to connect with Server "+
                        unixVO.getHostName()+" try again! </font> <br><br>";
                remoteProcessTextArea.append(envProcessVO.getProcessName() +
                        " : Not able to connect with Server "+unixVO.getHostName()+" try again! \n\n");
            }
        }
        environmentEditorPane.setText(htmlContent);
        removeUIChangesForRunHealthCheck();
        return null;
    }
    
}
