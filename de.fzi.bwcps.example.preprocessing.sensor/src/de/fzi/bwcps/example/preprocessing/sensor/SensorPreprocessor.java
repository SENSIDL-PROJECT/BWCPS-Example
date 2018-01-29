package de.fzi.bwcps.example.preprocessing.sensor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.preprocessing.MeasuredData;

public class SensorPreprocessor implements DataProcessor<Map<String,Object>> {
	
	private final Publisher publisher;
	private final DataProcessorManager<MeasuredData<Object>, String> procManager;
	
	public SensorPreprocessor(Publisher publisher) {
		
		this.procManager = initDataProcessorManager();
		this.publisher = initPublisher(publisher);
		
	}

	private DataProcessorManager<MeasuredData<Object>, String> initDataProcessorManager() {
		
		DataPipe<MeasuredData<Object>> inputDataToConverter = new DataPipe<MeasuredData<Object>>();
		DataPipe<MeasuredData<GalileoGen2Data>> converterToSerializer = new DataPipe<MeasuredData<GalileoGen2Data>>();
		DataPipe<String> outputPipe = new DataPipe<String>();
		
		RawToGalileoGen2DataConverter converter = new RawToGalileoGen2DataConverter(inputDataToConverter, converterToSerializer);
		DataSerializer serializer = new DataSerializer(converterToSerializer, outputPipe);
		
		return new DataProcessorManager<MeasuredData<Object>, String>(inputDataToConverter, 
																	  outputPipe, 
																	  Arrays.asList(converter,serializer));
		
	}

	private Publisher initPublisher(Publisher newPublisher) {
		
		return newPublisher.isConnected() ? newPublisher : startToConnect(newPublisher);
		
	}
	
	private Publisher startToConnect(Publisher newPublisher) {
		
		try {
			
			newPublisher.connect();
			return newPublisher;
		
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

	@Override
	public void process(Map<String, Object> measurements) {
		
		procManager.resetWithNew(transform(measurements.entrySet().stream()));
		procManager.applyAllFilter();
		procManager.getFirstResult().ifPresent(result -> publish(result));
		
				
	}

	private void publish(String result) {
		
		try {
			
			publisher.publish(result);
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

	private List<MeasuredData<Object>> transform(Stream<Map.Entry<String,Object>> measurements) {
		
		return measurements.map(each -> new MeasuredData<Object>(each.getKey(), each.getValue()))
						   .collect(Collectors.toList());
											   
		
	}
	
}
