package de.fzi.bwcps.example.preprocessing.gateway;

import java.util.Arrays;

import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;

public class GatewayPreprocessor implements DataProcessor<String> {

	private final DataProcessorManager<String, GalileoGen2Data> procManager;
	private final Subscriber subscriber;
	
	public GatewayPreprocessor(Subscriber subscriber) {
		
		this.procManager = initDataProcessorManager();
		this.subscriber = initSubscriber(subscriber);
		
	}

	private DataProcessorManager<String, GalileoGen2Data> initDataProcessorManager() {
		
		DataPipe<String> inputDataToDeserialized = new DataPipe<String>();
		DataPipe<GalileoGen2Data> outputPipe = new DataPipe<GalileoGen2Data>();
		
		DataDeserializer deserializer = new DataDeserializer(inputDataToDeserialized, outputPipe);
		
		return new DataProcessorManager<String, GalileoGen2Data>(inputDataToDeserialized, 
																 outputPipe, 
																 Arrays.asList(deserializer));	
		
	}

	private Subscriber initSubscriber(Subscriber newSubscriber) {
		
		try {
			
			if (newSubscriber.isConnected() == false) {
				
				newSubscriber.connect();
				
			}
			
			newSubscriber.setArrivedMessageHandler(message -> process(message));
			newSubscriber.subscribe();
			
			return newSubscriber;
		
		} catch(Exception e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

	@Override
	public void process(String measurement) {
		
		procManager.resetWithNew(Arrays.asList(measurement));
		procManager.applyAllFilter();
		
	}

}
