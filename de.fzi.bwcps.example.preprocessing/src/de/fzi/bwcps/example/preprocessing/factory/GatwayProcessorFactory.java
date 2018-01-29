package de.fzi.bwcps.example.preprocessing.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.fzi.bwcps.example.preprocessing.DataProcessor;

public class GatwayProcessorFactory {

	private static Map<DataProcessorId, DataProcessor<String>> gatewayPreprocessors;
	static {
		
		gatewayPreprocessors = new HashMap<DataProcessorId, DataProcessor<String>>();
		
	}
	
	public static void register(DataProcessorId id, DataProcessor<String> preproc) {
		
		gatewayPreprocessors.put(id, preproc);
		
	}
	
	public static void unregister(DataProcessorId id) {
		
		gatewayPreprocessors.remove(id);
		
	}
	
	public static Optional<DataProcessor<String>> getInstanceWith(DataProcessorId id) {
		
		return Optional.of(gatewayPreprocessors.get(id));
		
	}
	
	public static Optional<DataProcessor<String>> getInstanceWith(String id) {
		
		return Optional.of(gatewayPreprocessors.get(DataProcessorId.valueOf(id)));
		
	}
	
}
