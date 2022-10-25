package com.freshcells.freshcellstask;

import com.freshcells.freshcellstask.service.HotelsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FreshcellsTaskApplication {

	public static void consolidateHotelsData(){
		HotelsService hotelsService =  new HotelsService();
		hotelsService.consolidateHotelsData();
	}

	public static void main(String[] args) throws IOException {
		FreshcellsTaskApplication.consolidateHotelsData();
	}

}
