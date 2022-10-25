package com.freshcells.freshcellstask.model;

import java.io.File;
/*
    this class to handle the complications of creating hotel object
 */
public class HotelBuilder {

    public static Hotel build(File file){
        Hotel hotel = new Hotel(file);

        String[] fileNameCompination = file.getName().split("-");

        Long   hotelId = Long.parseLong(fileNameCompination[0]);
        hotel.setHotelId(hotelId);

        String fileNameCompartment = fileNameCompination[1];
        hotel.setDataType(fileNameCompartment.substring(0, fileNameCompartment.indexOf(".")));
        hotel.setFileExtention(fileNameCompartment.substring(fileNameCompartment.indexOf("."), fileNameCompartment.length()));

        return hotel;
    }





}
