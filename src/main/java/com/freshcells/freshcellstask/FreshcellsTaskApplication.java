package com.freshcells.freshcellstask;

import com.freshcells.freshcellstask.service.HotelsService;

/**
 * the file will execute the merging process
 */
public class FreshcellsTaskApplication {
	public static final HotelsService hotelsService = new HotelsService();

	/**
	 * The methods will execute the merging process for the hotel data files into one json file, to update the dir and json file location
	 * please change the attribute values in the application.properties file (JSON_OUT_FILE_PATH and HOTEL_XML_FILES_DIRECTORY)
	 */
	public static void consolidateHotelsData(){
		hotelsService.consolidateHotelsData();
	}

	public static void main(String[] args) {
		FreshcellsTaskApplication.consolidateHotelsData();
	}

}
