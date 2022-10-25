package com.freshcells.freshcellstask.repository;

import com.freshcells.freshcellstask.model.Hotel;
import com.freshcells.freshcellstask.model.HotelBuilder;
import com.freshcells.freshcellstask.utils.JsonXmlUtils;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log
public class HotelFilesRepo implements HotelsRepo {
    private static final String DATA_FILES_SOURCE_DIR = "HOTEL_XML_FILES_DIRECTORY";

    public List<Hotel> getHotels() {
        return  getHotelsByFileDirPath(JsonXmlUtils.getValueFromProperties(DATA_FILES_SOURCE_DIR));
    }

    /**
     * this method will read all compatible files in the provided folder and convert each file to a hotel object
     *
     * @param dirPath
     * @return List<Hotel>
     */
    private static List<Hotel> getHotelsByFileDirPath(String dirPath){
        List<Hotel> hotels =  new ArrayList<>();

        File[] allfiles = new File(dirPath).listFiles();
        if(allfiles == null){
            log.log(Level.INFO, String.format("no xml or json dataFiles found in the dir %s ", dirPath)); //
            return hotels;
        }

        Set<File> dataFiles = Stream.of(allfiles)
                .filter(file -> !file.isDirectory() && validFileExt(file.getName()))
                .collect(Collectors.toSet());

        if(dataFiles.isEmpty()){
            log.log(Level.INFO, String.format("no xml or json dataFiles found in the dir %s ", dirPath));
            return hotels;
        }

        for (File file : dataFiles){
            if(!validateHotelDataName(file.getName())){
                continue;
            }

            Hotel hotel = HotelBuilder.build(file);

            hotels.add(hotel);
        }

        if(hotels.isEmpty()) {
            log.log(Level.INFO, String.format("could not find valid hotels xml dataFiles in the specified dir %s ", dirPath));
            return hotels;
        }

        return hotels;
    }

    private static boolean validFileExt(String fileName) {
        return fileName.endsWith(".xml") || fileName.endsWith(".json");
    }

    private static boolean validateHotelDataName(String name) {
        String[] nameCompination = name.split("-");
        if(name.length() < 7){//assuming shortest name is like 1-c.xml
            log.log(Level.INFO, String.format("file name: %s Wrong file format ! it should be similer to this 123-type.xml ", name));
            return false;
        }
        try {
            Long.valueOf(nameCompination[0]);
        }catch(NumberFormatException e){
            log.log(Level.INFO, String.format("file name : %s does not start with number ! ", name));
            return false;
        }

        String hotelDataType =  nameCompination[1].substring(0,  nameCompination[1].indexOf('.'));
        List<String> types = Arrays.asList("coah", "giata");// should read from properties file
        if(!types.contains(hotelDataType)){
            String[] params = {hotelDataType, name};
            log.log(Level.INFO, String.format("data type %s is not valid in the file name : %s ",params));
            return false;
        }

        return true;
    }





}
