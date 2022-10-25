package com.freshcells.freshcellstask.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

@Log
public final class  JsonXmlUtils {
    /**
     *
     * @param xmlFile
     * @return JsonNode
     */
    public static JsonNode getJsonNodeFromXml(File xmlFile) {
        XmlMapper xmlMapper = new XmlMapper();

        JsonNode jsonNode = null;
        try {
            jsonNode = xmlMapper.readTree(xmlFile);
        } catch (IOException e) {
            log.log(Level.INFO,"Unable to read  xml file : %s, the format is not valid!" , xmlFile.getName());
            throw new RuntimeException(e);
        }
        return jsonNode;
    }

    /**
     *
     * @param jsonFile
     * @return JsonNode
     */
    public static JsonNode getJsonNodeFromJson(File jsonFile) {
        ObjectMapper xmlMapper = new ObjectMapper();

        JsonNode jsonNode = null;
        try {
            jsonNode = xmlMapper.readTree(jsonFile);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unable to read  Json file : {}, the format is not valid!" , jsonFile.getName());
            throw new RuntimeException(e);
        }
        return jsonNode;
    }

    /**
     * if file does not exists it will be created in the provided path with the JsonNode as its contents.
     *
     * @param filePath
     * @param node
     */
    public static void writeJasonNodeToFile(String filePath, JsonNode node){
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            jsonMapper.writerWithDefaultPrettyPrinter().
                    writeValue(
                            new File(filePath), node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * the file will be created as json format, if it exists it will override it with the provided data in the map
     * in Json format.
     *
     * @param filePath
     * @param map
     */
    public static void writeJsonMapToFile(String filePath, Map<Long, Map<String, JsonNode>> map){
        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            jsonMapper.writerWithDefaultPrettyPrinter().
                    writeValue(
                            new File(filePath), map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public static String getValueFromProperties(String property){
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("application.properties");
        try {
            prop.load(stream);
        } catch (IOException e) {
            log.log(Level.SEVERE, " application.properties is missing !");
            throw new RuntimeException(e);
        }
        return prop.getProperty(property);
    }




}
