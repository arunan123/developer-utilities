package org.devutils.utils;

import org.devutils.vo.ContainerVO;
import org.devutils.vo.ServerVO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Arunan Ramanathan
 * @date 24/July/2015
 */
public class XMLPropertyUtility {

    public static final String SERVER = "server";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String DIRECTORY = "directory";
    public static final String COMMAND = "command";
    public static final String PORT = "port";

    static String xmlFilePath = "props/servers.xml";

    public static void initProperties() {

        String systemProp = System.getProperty("sun.java.command");
        systemProp = systemProp.replaceFirst("dev-utils.jar", "");
        systemProp = systemProp+xmlFilePath;

        try {
            File fXmlFile = new File(systemProp);
//            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(SERVER);

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
                ServerVO playServerVO = new ServerVO();

		Node nNode = nList.item(temp);

 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;
                     playServerVO.setServerName(eElement.getAttribute(NAME));
                     NodeList list = nNode.getChildNodes();

                    for (int i=0; i<list.getLength(); i++) {
                        Node node = list.item(i);
                        if (TYPE.equals(node.getNodeName())) {
                            playServerVO.setServerType(node.getTextContent());
                        }
                        if (DIRECTORY.equals(node.getNodeName())) {
                            playServerVO.setServerPath(node.getTextContent());
                        }
                        if (COMMAND.equals(node.getNodeName())) {
                            playServerVO.setCommand(node.getTextContent());
                        }
                        if (PORT.equals(node.getNodeName())) {
                            playServerVO.setHttpPort(node.getTextContent());
                        }
                    }
                }
                ContainerVO.getPlayServerMap().put(playServerVO.getServerName(), playServerVO);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addNode(String serverName, String type, String portNumber,
                                String directory, String command) {
        try {        
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(fXmlFile);
            boolean isProfileExists = false;

            // If already exists
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(SERVER);

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);

 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;

                     if (serverName.equalsIgnoreCase(eElement.getAttribute(NAME))) {
                         isProfileExists = true;
                         NodeList list = nNode.getChildNodes();

                         for (int i=0; i<list.getLength(); i++) {
                             Node node = list.item(i);
                             if (TYPE.equals(node.getNodeName())) {
                                node.setTextContent(type);
                             }
                             if (DIRECTORY.equals(node.getNodeName())) {
                                node.setTextContent(directory);
                             }
                             if (COMMAND.equals(node.getNodeName())) {
                                node.setTextContent(command);
                             }
                             if (PORT.equals(node.getNodeName())) {
                                node.setTextContent(portNumber);
                             }
                         }
                     }
                }
            }
            if (!isProfileExists) {
                Element rootElement = doc.getDocumentElement();

                Element profile = doc.createElement(SERVER);
                rootElement.appendChild(profile);

                // set attribute to staff element
                Attr attr = doc.createAttribute(NAME);
                attr.setValue(serverName);
                profile.setAttributeNode(attr);

                Element typeElement = doc.createElement(TYPE);
                typeElement.appendChild(doc.createTextNode(type));
                profile.appendChild(typeElement);

                Element portElement = doc.createElement(PORT);
                portElement.appendChild(doc.createTextNode(portNumber));
                profile.appendChild(portElement);

                Element directoryElement = doc.createElement(DIRECTORY);
                directoryElement.appendChild(doc.createTextNode(directory));
                profile.appendChild(directoryElement);

                Element commandElement = doc.createElement(COMMAND);
                commandElement.appendChild(doc.createTextNode(command));
                profile.appendChild(commandElement);

                rootElement.appendChild(profile);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));

            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }
    
    public static List<String> getServerNames() {
 
        List<String> serverList = new ArrayList<>(10);

        try {
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(SERVER);

            for (int temp = 0; temp < nList.getLength(); temp++) {
 
		Node nNode = nList.item(temp);

                if (temp == 0) {
                    serverList.add("---select---");
                }
 		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element eElement = (Element) nNode;
                     serverList.add(eElement.getAttribute(NAME));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serverList;
    }
}
