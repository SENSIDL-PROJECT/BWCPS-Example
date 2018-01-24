package de.fzi.bwcps.example.preprocessing.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.fzi.bwcps.example.dataprocess.DataConsumer;
import de.fzi.bwcps.example.preprocessing.gateway.GatewayPreprocessor;
import de.fzi.bwcps.example.preprocessing.sensor.SensorPreprocessor;

public class PreprocessorFactory {

	// Temporary solution.
	private static Map<PreprocessorID, Preprocessable<?,?>> registeredPreprocessors;
	static {
		
		registeredPreprocessors = new HashMap<PreprocessorID, Preprocessable<?,?>>();
		registeredPreprocessors.put(PreprocessorID.GALILEO_GEN_2, new SensorPreprocessor());
		registeredPreprocessors.put(PreprocessorID.GATEWAY, new GatewayPreprocessor());
		
	}
	
	public static Optional<Preprocessable<?,?>> getPreprocessorBy(PreprocessorID id) {
		
		return Optional.of(registeredPreprocessors.getOrDefault(id, null));
		
	}

	public static Optional<DataConsumer<?>> getDataConsumingPreprocessorBy(PreprocessorID id) {
		
		Optional<Preprocessable<?,?>> preprocessor = getPreprocessorBy(id);
		if (preprocessor.isPresent() && (preprocessor.get() instanceof DataConsumer<?>)) {
			
			return Optional.of((DataConsumer<?>) preprocessor.get());
			
		}
		
		return Optional.empty();
		
	}
	
}
