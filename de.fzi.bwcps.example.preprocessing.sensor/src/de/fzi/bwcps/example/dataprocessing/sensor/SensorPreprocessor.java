package de.fzi.bwcps.example.dataprocessing.sensor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.kura.KuraException;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fzi.bwcps.example.com.pubsub.IPublisher;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.galileogen2.gen.GalileoGen2Data;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.preprocessing.MeasuredData;

public class SensorPreprocessor implements DataProcessor<Map<String, Object>> {

	private IPublisher publisher;
	private DataProcessorManager<MeasuredData<Object>, String> procManager;
	
	private static final Logger s_logger = LoggerFactory.getLogger(SensorPreprocessor.class);

	public SensorPreprocessor() {
		
	}
		
	public synchronized void setPublisher(IPublisher publisher) {
		this.publisher = publisher;
		s_logger.info("publisher set");
	}

	public synchronized void unsetPublisher(IPublisher publisher) {
		if (this.publisher == publisher) {
			this.publisher = null;
			s_logger.info("publisher unset");
		}
	}
	
	protected void activate(ComponentContext componentContext,
			Map<String, Object> properties) {
		s_logger.info("activate preprocessor");
		this.procManager = initDataProcessorManager();
		
	}
	public void deactivate(ComponentContext componentContext) throws KuraException {
		
	}
	public void updated(ComponentContext componentContext) throws KuraException {
		
	}

	private DataProcessorManager<MeasuredData<Object>, String> initDataProcessorManager() {

		DataPipe<MeasuredData<Object>> inputDataToConverter = new DataPipe<MeasuredData<Object>>();
		DataPipe<MeasuredData<GalileoGen2Data>> converterToSerializer = new DataPipe<MeasuredData<GalileoGen2Data>>();
		DataPipe<String> outputPipe = new DataPipe<String>();

		RawToGalileoGen2DataConverter converter = new RawToGalileoGen2DataConverter(inputDataToConverter,
				converterToSerializer);
		DataSerializer serializer = new DataSerializer(converterToSerializer, outputPipe);

		return new DataProcessorManager<MeasuredData<Object>, String>(inputDataToConverter, outputPipe,
				Arrays.asList(converter, serializer));

	}

	private IPublisher initPublisher(IPublisher newPublisher) {

		return newPublisher.isConnected() ? newPublisher : startToConnect(newPublisher);

	}

	private IPublisher startToConnect(IPublisher newPublisher) {

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
			if (publisher.isConnected()) {
				publisher.publish(result);
			}

		} catch (Exception e) {

			throw new RuntimeException(e);

		}

	}

	private List<MeasuredData<Object>> transform(Stream<Map.Entry<String, Object>> measurements) {

		return measurements.map(each -> new MeasuredData<Object>(each.getKey(), each.getValue()))
				.collect(Collectors.toList());

	}

}
