package com.freshcells.freshcellstask.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.freshcells.freshcellstask.model.Hotel;
import com.freshcells.freshcellstask.repository.HotelFilesRepo;
import com.freshcells.freshcellstask.repository.HotelsRepo;
import com.freshcells.freshcellstask.utils.JsonXmlUtils;
import lombok.extern.java.Log;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
@Log
public class HotelsService {
    private static final String JSON_FILE_EXT = ".json";

    private HotelsRepo hotelsRepo = new HotelFilesRepo();
    private static final String JSON_FILE_PATH = "JSON_OUT_FILE_PATH";

    public void consolidateHotelsData(){
        List<Hotel> hotels = hotelsRepo.getHotels();

        Map<Long, Map<String, JsonNode>> hotelsAsJsonMap = convertHotelsListToMap(hotels);

        try {
            String filePath = JsonXmlUtils.getValueFromProperties(JSON_FILE_PATH);
            JsonXmlUtils.writeJsonMapToFile(filePath, hotelsAsJsonMap);
            log.log(Level.INFO , String.format("Json File Updated %s", filePath) );
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private Map<Long, Map<String, JsonNode>> convertHotelsListToMap(List<Hotel> hotels) {
        Map<Long, Map<String, JsonNode>> hotelsMap = new HashMap<>();
        Set<Hotel> hotelsSet = hotels.stream().collect(Collectors.toSet()); // to remove duplicates

        for (Hotel hotel : hotelsSet) {
            //get the hotel data from its file as a jsonNode
            JsonNode jsonNodeFromFile;
            if (hotel.getFileExtention().equalsIgnoreCase(JSON_FILE_EXT)) {
                jsonNodeFromFile = JsonXmlUtils.getJsonNodeFromJson(hotel.getDataFile());
            } else {
                jsonNodeFromFile = JsonXmlUtils.getJsonNodeFromXml(hotel.getDataFile());
            }

            //add the jsonNode to the Map to structure the json file
            if (hotelsMap.get(hotel.getHotelId()) == null) { // if id does not exist add it
                Map<String, JsonNode> hotelNode = new HashMap<>();
                hotelNode.put(hotel.getDataType().toString(), jsonNodeFromFile);
                hotelsMap.put(hotel.getHotelId(), hotelNode);

            } else {// if id already exists add record to it (no need to check if type exists as we removed duplicates in the set)
                hotelsMap.get(hotel.getHotelId()).put(hotel.getDataType().toString(), jsonNodeFromFile);
            }
            log.log(Level.INFO,String.format("adding file %s to jsonMap", hotel.getDataFile().getName()));
        }
        return hotelsMap;
    }

}
