package jds_wn_dx.routeplanner.model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Assignment: Route Planner
 * Author: Danny Xu
 * Date: 30/05/2017
 * Description: Handles all of the XML I/O subsystems.
 *
 * This object is a model object.
 */

public class XML_IO {

    // Fields required for XML operations
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList nameList;
    private NodeList routeTypeList;
    private NodeList startList;
    private NodeList endList;
    private Node node;
    private Element element;
    // Linear + Descent
    private String name;
    private ArrayList<String> routeType = new ArrayList<>(0);
    private ArrayList<String> start = new ArrayList<>(0);
    private ArrayList<String> end = new ArrayList<>(0);

    public XML_IO() {
        initialize();
    }

    // Initializes the program
    private void initialize() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sets the data, to be called after a file has been loaded so that information is visible to other systems
    public void setData() {
        try {
            nameList = doc.getElementsByTagName("Name");
            node = nameList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                element = (Element) node;
                name = element.getTextContent();
            }
            routeTypeList = doc.getElementsByTagName("RouteType");
            startList = doc.getElementsByTagName("Start");
            endList = doc.getElementsByTagName("End");
            for (int i = 0; i < routeTypeList.getLength(); i++) {
                node = routeTypeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    routeType.add(i, element.getTextContent());
                }
                node = startList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    start.add(i, element.getTextContent());
                }
                node = endList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    end.add(i, element.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Loads the file into memory and makes file information visible to other systems
    public void inputFile(File file) {
        try {
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Outputs a file given the correct information as an XML following the FLT format
    public void outputFile(ArrayList<String> routetype, ArrayList<String> startcoords, ArrayList<String> endcoords, String nameType, File location) {
        Document document = dBuilder.newDocument();
        // root element
        Element rootElement = document.createElement("Document");
        Attr attrType = document.createAttribute("type");
        attrType.setValue("FTL");
        rootElement.setAttributeNode(attrType);
        document.appendChild(rootElement);

        Element flight = document.createElement("Flight");
        rootElement.appendChild(flight);

        Element name = document.createElement("Name");
        name.appendChild(
            document.createTextNode(nameType));
        flight.appendChild(name);

        for (int i = 0; i < routetype.size(); i++) {
            Element path = document.createElement("Path");
            flight.appendChild(path);

            Element routetypeE = document.createElement("RouteType");
            routetypeE.appendChild(document.createTextNode(routetype.get(i)));
            path.appendChild(routetypeE);

            Element startE = document.createElement("Start");
            startE.appendChild(document.createTextNode(startcoords.get(i)));
            path.appendChild(startE);

            Element endE = document.createElement("End");
            endE.appendChild(document.createTextNode(endcoords.get(i)));
            path.appendChild(endE);
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(location);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters for various point data from files
    public ArrayList<String> getRouteType() {
        return routeType;
    }

    public ArrayList<String> getStart() {
        return start;
    }

    public ArrayList<String> getEnd() {
        return end;
    }
    public String getName() {
        return name;
    }



}
