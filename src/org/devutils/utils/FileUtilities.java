/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.devutils.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;
/**
 *
 * @author arramanathan
 */
public class FileUtilities {
    
    static String xmlFilePath = "props\\profiles.xml";
    
    public static void exportFile(File file, String delimitor) {
//        logger.log(Level.INFO, "Exporting file");
//        PrintWriter writer = null;
//        try {
//            int rowCount = Main.resultSetTable.getRowCount();
//            int columnCount = Main.resultSetTable.getColumnCount();
//
//            writer = new PrintWriter(file);
//            for (int i=0; i<rowCount; i++) {
//                StringBuffer row = new StringBuffer();
//                for (int j=0; j<columnCount; j++) {
//                    row.append( Main.resultSetTable.getValueAt(i, j));
//                    if (j!=columnCount-1) {
//                        row.append(delimitor);
//                    }
//                }
//                writer.println(row);
//            }
//        } catch (FileNotFoundException ex) {
//            logger.log(Level.SEVERE,null, ex);
//        } catch (IOException ex) {
//            logger.log(Level.SEVERE,null, ex);
//        } finally {
//            writer.close();
//        }
    }
    
    public static List getProfilesNames() {
            
        List profileList = new ArrayList(10);
        
        try {
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("profile");

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);

                if (temp == 0) {
                    profileList.add("---select---");
                }
 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;
                     profileList.add(eElement.getAttribute("name"));
                }
            }

        } catch (Exception ex) {
//            logger.log(Level.SEVERE,null, ex);
        }
        return profileList;
    }
    
    public static void addNode(String profileName, String hostName, String portNumber,
                                String serviceName, String userName, String password) {
        try {        
//            logger.log(Level.INFO, "Saving profile " + profileName);

            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            boolean isProfileExists = false;
            
            // If already exists
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("profile");

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);

 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;
                     
                     if (profileName.equalsIgnoreCase(eElement.getAttribute("name"))) {
                         isProfileExists = true;
                         NodeList list = nNode.getChildNodes();
                         
                         for (int i=0; i<list.getLength(); i++) {
                             Node node = list.item(i);
                             if ("port".equals(node.getNodeName())) {
                                node.setTextContent(portNumber);
                             }
                             if ("hostname".equals(node.getNodeName())) {
                                node.setTextContent(hostName);
                             }
                             if ("servicename".equals(node.getNodeName())) {
                                node.setTextContent(serviceName);
                             }
                             if ("username".equals(node.getNodeName())) {
                                node.setTextContent(userName);
                             }
                             if ("password".equals(node.getNodeName())) {
                                node.setTextContent(password);
                             }
                         }
                     }
                }
            }
            if (!isProfileExists) {
                Element rootElement = doc.getDocumentElement();

                Element profile = doc.createElement("profile");
                rootElement.appendChild(profile);

                // set attribute to staff element
                Attr attr = doc.createAttribute("name");
                attr.setValue(profileName);
                profile.setAttributeNode(attr);

                Element hostNameElement = doc.createElement("hostname");
                hostNameElement.appendChild(doc.createTextNode(hostName));
                profile.appendChild(hostNameElement);

                Element portElement = doc.createElement("port");
                portElement.appendChild(doc.createTextNode(portNumber));
                profile.appendChild(portElement);

                Element serviceNameElement = doc.createElement("servicename");
                serviceNameElement.appendChild(doc.createTextNode(serviceName));
                profile.appendChild(serviceNameElement);

                Element userNameElement = doc.createElement("username");
                userNameElement.appendChild(doc.createTextNode(userName));
                profile.appendChild(userNameElement);

                Element passwordElement = doc.createElement("password");
                passwordElement.appendChild(doc.createTextNode(password));
                profile.appendChild(passwordElement);

                rootElement.appendChild(profile);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));

            transformer.transform(source, result);

        } catch (ParserConfigurationException ex) {
//            logger.log(Level.SEVERE,null, ex);
        } catch (SAXException ex) {
//            logger.log(Level.SEVERE,null, ex);
        } catch (IOException ex) {
//            logger.log(Level.SEVERE,null, ex);
        } catch (TransformerConfigurationException ex) {
//            logger.log(Level.SEVERE,null, ex);
        } catch (TransformerException ex) {
//            logger.log(Level.SEVERE,null, ex);
        }
    }
    
    public static Map getProfile(String profileName) {
        Map profileMap = new HashMap(10);
        
        try {
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("profile");

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);

 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;
                     
                     if (profileName.equalsIgnoreCase(eElement.getAttribute("name"))) {
                         profileMap.put("profilename", eElement.getAttribute("name"));
                         profileMap.put("hostname",  eElement.getElementsByTagName("hostname").item(0).getTextContent());
                         profileMap.put("port",  eElement.getElementsByTagName("port").item(0).getTextContent());
                         profileMap.put("servicename",  eElement.getElementsByTagName("servicename").item(0).getTextContent());
                         profileMap.put("username",  eElement.getElementsByTagName("username").item(0).getTextContent());
                         profileMap.put("password",  eElement.getElementsByTagName("password").item(0).getTextContent());
                         break;
                     }
                }
            }

        } catch (Exception ex) {
//            logger.log(Level.SEVERE,null, ex);
        }
       
        return profileMap;
    }
//    public static void main(String... args) {
//        Map mapOfItems = FileUtilities.getProfile("stage2dev387");
//        System.out.println(mapOfItems.get("hostname"));
//        System.out.println(mapOfItems.get("port"));
//        System.out.println(mapOfItems.get("servicename"));
//        System.out.println(mapOfItems.get("username"));
//        System.out.println(mapOfItems.get("password"));
//    }
}
