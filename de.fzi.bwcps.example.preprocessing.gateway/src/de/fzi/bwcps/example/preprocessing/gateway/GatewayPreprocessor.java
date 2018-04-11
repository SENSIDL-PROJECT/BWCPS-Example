package de.fzi.bwcps.example.preprocessing.gateway;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fzi.bwcps.example.com.pubsub.ISubscriber;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.presentation.DataPresenter;
import de.fzi.bwcps.example.presentation.DataRepresentation;

public class GatewayPreprocessor implements DataProcessor<String> {

	private DataProcessorManager<String, GalileoGen2Data> procManager;
	private ISubscriber subscriber;
	private DataPresenter presenter;
	
	private static final Logger s_logger = LoggerFactory.getLogger(GatewayPreprocessor.class);
	
	public GatewayPreprocessor() {
		
	}
	
	public synchronized void setSubscriber(ISubscriber subscriber) {
		this.subscriber = initSubscriber(subscriber);
		s_logger.info("subscriber set");
	}

	public synchronized void unsetSubscriber(ISubscriber subscriber) {
		if (this.subscriber == subscriber) {
			this.subscriber = null;
			s_logger.info("subscriber unset");
		}
	}
	
	public synchronized void setPresenter(DataPresenter presenter) {
		this.presenter = presenter;
		s_logger.info("presenter set");
	}

	public synchronized void unsetPresenter(DataPresenter presenter) {
		if (this.presenter == presenter) {
			this.presenter = null;
			s_logger.info("presenter unset");
		}
	}
	
	public void activate(ComponentContext componentContext) {
		this.procManager = initDataProcessorManager();
	}
	

	private DataProcessorManager<String, GalileoGen2Data> initDataProcessorManager() {
		
		DataPipe<String> inputDataToDeserialized = new DataPipe<String>();
		DataPipe<GalileoGen2Data> outputPipe = new DataPipe<GalileoGen2Data>();
		
		DataDeserializer deserializer = new DataDeserializer(inputDataToDeserialized, outputPipe);
		
		return new DataProcessorManager<String, GalileoGen2Data>(inputDataToDeserialized, 
																 outputPipe, 
																 Arrays.asList(deserializer));	
		
	}

	private ISubscriber initSubscriber(ISubscriber newSubscriber) {
		
		try {
			
			if (newSubscriber.isConnected() == false) {
				
				newSubscriber.connect();
				
			}
			
			newSubscriber.setArrivedMessageHandler(message -> this.process(message));
			
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
