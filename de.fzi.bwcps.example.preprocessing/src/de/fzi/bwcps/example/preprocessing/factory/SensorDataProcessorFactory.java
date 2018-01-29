package de.fzi.bwcps.example.preprocessing.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.fzi.bwcps.example.preprocessing.DataProcessor;

public class SensorDataProcessorFactory {

	private static Map<DataProcessorId, DataProcessor<Map<String,Object>>> sensorPreprocessors;
	static {
		
		sensorPreprocessors = new HashMap<DataProcessorId, DataProcessor<Map<String,Object>>>();
		
	}
	
	public static void register(DataProcessorId id, DataProcessor<Map<String,Object>> preproc) {
		
		sensorPreprocessors.put(id, preproc);
		
	}
	
	public static void unregister(DataProcessorId id) {
		
		sensorPreprocessors.remove(id);
		
	}
	
	public static Optional<DataProcessor<Map<String,Object>>> getInstanceWith(DataProcessorId id) {
		
		return Optional.of(sensorPreprocessors.get(id));
		
	}
	
	public static Optional<DataProcessor<Map<String,Object>>> getInstanceWith(String id) {
		
		return Optional.of(sensorPreprocessors.get(DataProcessorId.valueOf(id)));
		
	}
	
}
