package jds_wn_dx.routeplanner.controller;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class XML_IO {

    private File file;
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList routeTypeList;
    private NodeList startList;
    private NodeList endList;
    private Node node;
    private Element element;
    private ArrayList<String> routeType = new ArrayList<String>(0);
    private ArrayList<String> start = new ArrayList<String>(0);
    private ArrayList<String> end = new ArrayList<String>(0);

    private final String encoding = "UTF-8";

    public XML_IO() {
        initialize();
    }

    private void initialize() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getData() {
        try {
            routeTypeList = doc.getElementsByTagName("RouteType");
            for (int i = 0; i < routeTypeList.getLength(); i++) {
                node = routeTypeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    routeType.add(i, element.getTextContent());
                }
            }
            startList = doc.getElementsByTagName("Start");
            for (int i = 0; i < startList.getLength(); i++) {
                node = startList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;
                    start.add(i, element.getTextContent());
                }
            }
            endList = doc.getElementsByTagName("End");
            for (int i = 0; i < endList.getLength(); i++) {
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

    public void inputFile(String name) {
        try {
            file = new File(name);
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRouteType() {
        return routeType;
    }

    public ArrayList<String> getStart() {
        return start;
    }

    public ArrayList<String> getEnd() {
        return end;
    }

}
