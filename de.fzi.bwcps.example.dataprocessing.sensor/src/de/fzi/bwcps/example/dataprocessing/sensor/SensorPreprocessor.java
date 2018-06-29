package de.fzi.bwcps.example.dataprocessing.sensor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.ComponentContext;

import de.fzi.bwcps.example.com.pubsub.IPublisher;
import de.fzi.bwcps.example.dataprocessing.util.DataProcessorManager;
import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.sensor.plantower.gen.PlantowerData;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Initializes publisher and publishes incoming sensor data.
 * 
 * @author scheerer, czogalik
 *
 */
@Component(service = DataProcessor.class)
public class SensorPreprocessor implements DataProcessor<Map<String, Object>> {

	private IPublisher publisher;
	private DataProcessorManager<MeasuredData<Object>, String> procManager;

	@Reference
	public synchronized void setPublisher(IPublisher publisher) {
		this.publisher = initPublisher(publisher);
	}

	public synchronized void unsetPublisher(IPublisher publisher) {
		if (this.publisher == publisher) {
			this.publisher = null;
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
		this.procManager = initDataProcessorManager();

	}

	private IPublisher initPublisher(IPublisher newPublisher) {
		try {
			if (newPublisher.isConnected() == false) {
				newPublisher.connect();
			}
			return newPublisher;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private DataProcessorManager<MeasuredData<Object>, String> initDataProcessorManager() {

		DataPipe<MeasuredData<Object>> inputDataToConverter = new DataPipe<MeasuredData<Object>>();
		DataPipe<MeasuredData<PlantowerData>> converterToSerializer = new DataPipe<MeasuredData<PlantowerData>>();
		DataPipe<String> outputPipe = new DataPipe<String>();

		RawToPlantowerDataConverter converter = new RawToPlantowerDataConverter(inputDataToConverter,
				converterToSerializer);
		DataSerializer serializer = new DataSerializer(converterToSerializer, outputPipe);

		return new DataProcessorManager<MeasuredData<Object>, String>(inputDataToConverter, outputPipe,
				Arrays.asList(converter, serializer));

	}

	@Override
	public void process(Map<String, Object> measurements) {

		procManager.resetWithNew(transform(measurements.entrySet().stream()));
		procManager.applyAllFilter();

		Optional<String> firstResult = procManager.getFirstResult();
		firstResult.ifPresent(result -> publish(result));

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
