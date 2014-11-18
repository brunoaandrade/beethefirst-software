package replicatorg.app;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import replicatorg.app.tools.XML;

/**
 * Copyright (c) 2013 BEEVC - Electronic Systems This file is part of BEESOFT
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version. BEESOFT is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with
 * BEESOFT. If not, see <http://www.gnu.org/licenses/>.
 */
public class CalibrationGCoder {

    private static String fileName = "colorsGCode";

    /**
     * Loads XML File Stores in DataSets also
     */
    public static void printXML() {
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file

            File f = new File(Base.getApplicationDirectory() + "/machines/".concat(fileName).concat(".xml"));
            if (f.exists() && f.isFile() && f.canRead()) {

                dom = db.parse(f);
                Element doc = dom.getDocumentElement();
                Node rootNode = doc.cloneNode(true);
                //System.out.println(doc.getTagName());

                if (XML.hasChildNode(rootNode, "tags")) {
                    Node startnode = XML.getChildNodeByName(rootNode, "tags");
                    org.w3c.dom.Element element = (org.w3c.dom.Element) startnode;
                    NodeList nodeList = element.getChildNodes(); // NodeList

                    /**
                     * Print section
                     */
                    for (int i = 1; i < nodeList.getLength(); i++) {
                        if (!nodeList.item(i).getNodeName().equals("#text") && !nodeList.item(i).hasChildNodes()) {
                            System.out.print(nodeList.item(i).getNodeName() + " Value: " + nodeList.item(i).getAttributes().getNamedItem("value") + "\n");
                        } else if (!nodeList.item(i).getNodeName().equals("#text") && nodeList.item(i).hasChildNodes()) //SubNode List
                        {
                            for (int j = 1; j < nodeList.item(i).getChildNodes().getLength(); j += 2) //Each NodeSubList
                            {
                                System.out.println(nodeList.item(i).getNodeName());
                                System.out.println("\t" + nodeList.item(i).getChildNodes().item(j).getNodeName() + " " + nodeList.item(i).getChildNodes().item(j).getAttributes().getNamedItem("value").getNodeValue());
                            }
                        }
                    }
                }

            } else {
                Base.writeLog("Permission denied over " + "languages/".concat(fileName).concat(".xml"));

            }
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }

    /**
     * Retrieves Tag value from XML
     *
     * @param rootTag Parent Node to search
     * @param subTag Child Node to get value
     * @return Child Node value
     */
    private static String getCode() {
        String rootTag = "colors";
        String beeCode = Base.getMainWindow().getMachineInterface().getModel().getCoilCode();
        String subTag = FilamentControler.getColor(beeCode).toLowerCase();

        /**
         * Lets user do calibration test event without filament. Default color =
         * Black
         */
        if (subTag.equalsIgnoreCase(FilamentControler.NO_FILAMENT)) {
            subTag = Languager.getTagValue(3, "CoilColors", "BLACK");
        }

        String tag = "gcode";

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file

            File f = new File(Base.getApplicationDirectory() + "/machines/".concat(fileName).concat(".xml"));
            if (f.exists() && f.isFile() && f.canRead()) {

                dom = db.parse(f);
                Element doc = dom.getDocumentElement();
                Node rootNode = doc.cloneNode(true);

                if (XML.hasChildNode(rootNode, tag)) {
                    Node startnode = XML.getChildNodeByName(rootNode, tag);
                    org.w3c.dom.Element element = (org.w3c.dom.Element) startnode;
                    NodeList nodeList = element.getChildNodes(); // NodeList

                    for (int i = 1; i < nodeList.getLength(); i++) {
                        if (!nodeList.item(i).getNodeName().equals("#text") && !nodeList.item(i).hasChildNodes()) {
                            if (nodeList.item(i).getNodeName().equals(rootTag)) // Found rooTag
                            {
                                return nodeList.item(i).getAttributes().getNamedItem("value").getNodeValue();
                            }
                            //System.out.print(nodeList.item(i).getNodeName() + " Value: " + nodeList.item(i).getAttributes().getNamedItem("value")+"\n");    
                        } else if (!nodeList.item(i).getNodeName().equals("#text") && nodeList.item(i).hasChildNodes()) //SubNode List
                        {
                            if (nodeList.item(i).getNodeName().equals(rootTag)) // Found rooTag
                            {

                                for (int j = 1; j < nodeList.item(i).getChildNodes().getLength(); j += 2) //Each NodeSubList
                                {
                                    if (nodeList.item(i).getChildNodes().item(j).getNodeName().contains(subTag.toLowerCase())) // Found subTag
                                    {
                                        return nodeList.item(i).getChildNodes().item(j).getAttributes().getNamedItem("value").getNodeValue().toString();
                                    }
                                    //                           System.out.println(nodeList.item(i).getNodeName());
                                    //                           System.out.println("\t" + nodeList.item(i).getChildNodes().item(j).getNodeName() + " "+nodeList.item(i).getChildNodes().item(j).getAttributes().getNamedItem("value").getNodeValue());
                                }

                            }
                        }
                    }
                }

            } else {
                Base.writeLog("Permission denied over " + "machines/".concat(fileName).concat(".xml"));

            }
        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }


        return null;
    }

    /**
     * Parses tag value from XML and removes string chars only.
     *
     * @return plain text array without spaces
     */
    public static String[] getColorGCode() {
        String code = getCode();

        Base.writeLog("code: " + code);

        return code.split(",");
    }
}
