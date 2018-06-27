package de.fzi.bwcps.example.dataprocessing.sensor;

import org.junit.Test;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerData;

public class DataSerializerTest {
	
	@Test
	public void simple() {
		DataPipe<MeasuredData<Object>> inputDataToConverter = new DataPipe<MeasuredData<Object>>();
		DataPipe<MeasuredData<PlantowerData>> converterToSerializer = new DataPipe<MeasuredData<PlantowerData>>();
		DataPipe<String> outputPipe = new DataPipe<String>();

		RawToPlantowerDataConverter converter = new RawToPlantowerDataConverter(inputDataToConverter,
				converterToSerializer);
		DataSerializer serializer = new DataSerializer(converterToSerializer, outputPipe);
		
		serializer.apply();
		
	}

}
