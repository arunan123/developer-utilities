/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.unixops;

import com.sshtools.net.SocketTransport;
import com.sshtools.ssh.ChannelOpenException;
import com.sshtools.ssh.PasswordAuthentication;
import com.sshtools.ssh.SshAuthentication;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshException;
import com.sshtools.ssh.SshSession;
import com.sshtools.ssh2.Ssh2Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aramanathan
 */
public class Authenticator {

    public static boolean isProcessRunning(String hostName, String userName, 
                                            String password, String port) throws Exception {
        try {
            SshConnector con = SshConnector.createInstance();
            //con.getContext().setHostKeyVerification(new ConsoleKnownHostsKeyVerification());
            //con.getContext().setPreferredPublicKey(Ssh2Context.PUBLIC_KEY_SSHDSS);
            SocketTransport transport = new SocketTransport(hostName, 22);
            transport.setTcpNoDelay(true);
            transport.setSoTimeout(1000);
            SshClient client = con.connect(transport, userName);

            Ssh2Client ssh2 = (Ssh2Client) client;

            PasswordAuthentication pwd = new PasswordAuthentication();
            do {
                pwd.setPassword(password);
            } while (ssh2.authenticate(pwd) != SshAuthentication.COMPLETE && client.isConnected());

            String command = "ps -ef | grep " + port + '\n';
            if (client.isAuthenticated()) {

                int isRunning = 0;
                SshSession session = client.openSessionChannel();

                try {
                    session.startShell();
                    session.getOutputStream().write(command.getBytes());
                    InputStream is = session.getInputStream();
                    Scanner br = new Scanner(new InputStreamReader(is));
                    String line = null;
                    while (br.hasNextLine()) {
                        line = br.nextLine();
                        isRunning++;
                        System.out.println(line);
                    }
                } catch (SocketTimeoutException ex) {
                    ex.printStackTrace();
                }
                session.close();
                if (isRunning > 1) {
                    System.out.println("Service is running");
                    return true;
                } else {
                    System.out.println("Service down");
                }
            }
        } catch (SshException | IOException | ChannelOpenException ex) {
            throw ex;
        }
        return false;
    }
    
//    public static void main(String[] args) {
//        new Authenticator().isProcessRunning("10.24.23.10", "root", "Shaklee_domain", "9001");
//    }
}
