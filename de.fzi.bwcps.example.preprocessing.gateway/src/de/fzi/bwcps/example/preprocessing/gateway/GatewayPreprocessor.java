package de.fzi.bwcps.example.preprocessing.gateway;

import java.util.Arrays;
import java.util.List;

import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.presentation.DataPresenter;
import de.fzi.bwcps.example.presentation.DataRepresentation;

public class GatewayPreprocessor implements DataProcessor<String> {

	private final DataProcessorManager<String, GalileoGen2Data> procManager;
	private final Subscriber subscriber;
	private final DataPresenter presenter;
	
	public GatewayPreprocessor(Subscriber subscriber, DataPresenter presenter) {
		
		this.procManager = initDataProcessorManager();
		this.subscriber = initSubscriber(subscriber);
		this.presenter = presenter;
		
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
			
			newSubscriber.setArrivedMessageHandler(message -> this.process(message));
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
		
		GalileoGen2Data result = procManager.getFirstResult()
											.orElseThrow(() -> new RuntimeException(String.format("%1s terminated with no result.", this.getClass().getName())));
		display(result);
		
	}

	private void display(GalileoGen2Data result) {
		
		presenter.display(makePresentable(result));
		
	}

	private List<DataRepresentation> makePresentable(GalileoGen2Data result) {
		
		return Arrays.asList(new DataRepresentation("Light", result.getLight().toString()),
							 new DataRepresentation("Temperature", result.getTemperature().toString()));
		
	}

}
