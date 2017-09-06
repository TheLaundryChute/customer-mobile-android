package com.inMotion.core.config;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

/**
 * Created by sfbechtold on 12/1/15.
 */
public abstract class ConfigSection {


    private Element section = null;

    public ConfigSection(Element section) {
        this.section = section;
    }

    public Integer getAsInt(String path) {

        String raw = this.getAsString(path);
        if (raw == null)
            return null;

        return Integer.parseInt(raw);
    }

    public Boolean getAsBoolean(String path) {

        String raw = this.getAsString(path);
        if (raw == null)
            return null;

        return Boolean.parseBoolean(raw);
    }

    public String getAsString(String path) {
        Element value = this.getAsElement(path);

        if (value == null)
            return  null;

        return  value.getTextContent();
    }

    public <T extends ConfigSection> T getAsSection(String path, Class<T> type) {
        Element value = this.getAsElement(path);
        if (value == null)
            return null;
        try {
            return type.getConstructor(Element.class).newInstance(value);
        }
        catch (Exception ex) {
            return null;
        }
    }

    private List<Element> getAsElementList(String path) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPath.evaluate(path, this.section, XPathConstants.NODESET);

            if (nodes == null || nodes.getLength() < 1)
                return null;

            List<Element> result = new ArrayList<Element>();
            for (int i = 0; i < nodes.getLength(); ++i) {
                result.add((Element)nodes.item(i));
            }

            return result;
        }
        catch (Exception ex) {
            int i =0;
        }
        return  null;
    }


    private Element getAsElement(String path) {

        try {
            List<Element> result = this.getAsElementList(path);
            if (result == null || result.size() < 1)
                return null;
            return result.get(0);
        }
        catch (Exception ex) {

        }
        return  null;
    }



}
