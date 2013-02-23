/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.persistence;

/**
 *
 * @author dcnorris
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author lorainelab
 */
public final class xmlParser {

    static String ip = "ip";
    static String port = "port";
    static String name = "name";
    static String exePath = "exePath";
    public static HashMap<String, HashMap<String, String>> fileTypeAssociations = new HashMap<String, HashMap<String, String>>();

    public HashMap<String, HashMap<String, String>> getFileAttributes() {
        if (fileTypeAssociations.isEmpty()) {
            initialize();
        }
        return fileTypeAssociations;
    }

    private void initialize() {
        File file = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            file = new File(System.getProperty("user.dir") + "/utserverstatus_data.xml");
            Document doc = null;
            try {
                doc = docBuilder.parse(file);
            } catch (java.io.FileNotFoundException e) {
                HashMap<String, String> defaultAttributes = new HashMap<String, String>();
                defaultAttributes.put(ip, "192.168.1.101");
                defaultAttributes.put(port, "27961");
                defaultAttributes.put(exePath, "C:/Program Files (x86)/UrbanTerror/ioUrbanTerror.exe");
                defaultAttributes.put(name, "unnamedPlayer");
                fileTypeAssociations.put("1", defaultAttributes);
                return;
            }

            // normalize text representation
            doc.getDocumentElement().normalize();

            NodeList listOfAssociations = doc.getElementsByTagName("SERVER_ASSOCIATIONS");
            //Build Array of server names
            ArrayList<String> associations = new ArrayList<String>();
            for (int i = 0; i < listOfAssociations.getLength(); i++) {
                Element test = (Element) listOfAssociations.item(i);
                NodeList testFNList = test.getChildNodes();
                String type = testFNList.item(0).getNodeValue().trim();
                associations.add(type);
            }


            //build hashMap of associations and attributes            
            for (int i = 0; i < listOfAssociations.getLength(); i++) {

                HashMap<String, String> fileAttributes = new HashMap<String, String>();

                Node AttributeRootNode = listOfAssociations.item(i);
                if (AttributeRootNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element rootAttributeElement = (Element) AttributeRootNode;

                    //-------
                    NodeList lastNameList = rootAttributeElement.getElementsByTagName(ip);
                    if (lastNameList.getLength() > 0) {
                        Element lastNameElement = (Element) lastNameList.item(0);
                        NodeList textLNList = lastNameElement.getChildNodes();
                        String backgroundValue = textLNList.item(0).getNodeValue().trim();
                        fileAttributes.put(ip, backgroundValue);
                    }

                    //----
                    NodeList ageList = rootAttributeElement.getElementsByTagName(port);
                    if (ageList.getLength() > 0) {
                        Element ageElement = (Element) ageList.item(0);
                        NodeList textAgeList = ageElement.getChildNodes();
                        String foregroundString = textAgeList.item(0).getNodeValue().trim();
                        fileAttributes.put(port, foregroundString);
                    }

                    NodeList exeList = rootAttributeElement.getElementsByTagName(exePath);
                    if (exeList.getLength() > 0) {
                        Element exeElement = (Element) exeList.item(0);
                        NodeList exeText = exeElement.getChildNodes();
                        String exeString = exeText.item(0).getNodeValue().trim();
                        fileAttributes.put(exePath, exeString);
                    }

                    NodeList seperateList = rootAttributeElement.getElementsByTagName(name);
                    if (seperateList.getLength() > 0) {
                        Element seperateElement = (Element) seperateList.item(0);
                        NodeList seperateText = seperateElement.getChildNodes();
                        String separateString = seperateText.item(0).getNodeValue().trim();
                        fileAttributes.put(name, separateString);
                    }
                    fileTypeAssociations.put(associations.get(i), fileAttributes);

                }


            }

        } catch (SAXParseException err) {
            System.err.println("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.err.println(err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}
