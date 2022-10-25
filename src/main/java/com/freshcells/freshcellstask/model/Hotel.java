package com.freshcells.freshcellstask.model;

import lombok.Data;

import java.io.File;

@Data
public class Hotel {
    private Long hotelId;
    private String dataType;
    private final File dataFile;

    private String fileExtention;

    public Hotel(File dataFile){
        this.dataFile = dataFile;
    }


    @Override
    public boolean equals(Object hotel){
        if(hotel == null || !(hotel instanceof Hotel)){
            return false;
        }
        if(((Hotel) hotel).getHotelId() == this.getHotelId() && ((Hotel) hotel).getDataType() == this.getDataType()){
            return true;
        }

        return false;
    }




}
