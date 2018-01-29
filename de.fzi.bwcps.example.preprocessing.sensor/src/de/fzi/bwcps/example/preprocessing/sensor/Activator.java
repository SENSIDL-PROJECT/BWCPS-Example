package de.fzi.bwcps.example.preprocessing.sensor;

import java.util.Optional;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.com.pubsub.factory.PublisherFactory;
import de.fzi.bwcps.example.com.pubsub.factory.PublisherId;
import de.fzi.bwcps.example.preprocessing.factory.SensorDataProcessorFactory;
import de.fzi.bwcps.example.preprocessing.factory.DataProcessorId;

public class Activator implements BundleActivator {

	private Publisher publisher;
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		Activator.context = bundleContext;
		
		publisher = getPublisher();
		SensorPreprocessor sensorPre = new SensorPreprocessor(publisher);
		SensorDataProcessorFactory.register(DataProcessorId.GALILEO_GEN_2, sensorPre);
		
	}

	private Publisher getPublisher() {
		
		try {
			
			Optional<Publisher> publisher = PublisherFactory.getPublisherInstanceWith(PublisherId.MQTT);
			return publisher.orElseThrow(() -> new Exception(String.format("There is no publisher with id: %s", PublisherId.MQTT.toString())));
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		
		Activator.context = null;
		publisher.disconnect();
		
	}

}
