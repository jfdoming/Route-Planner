package jds_wn_dx.routeplanner.controller;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;

public class XML_IO {

    public XML_IO() {
        initialize();
    }

    File file;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    NodeList pathList;
    Node meme;
    Element dank;
    private final String encoding = "UTF-8";

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
            pathList = doc.getElementsByTagName("RouteType");
            for (int i = 0; i < pathList.getLength(); i++) {
                meme = pathList.item(i);
                if (meme.getNodeType() == Node.ELEMENT_NODE) {
                    dank = (Element) meme;
                    System.out.println(dank.getTextContent());
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




}
