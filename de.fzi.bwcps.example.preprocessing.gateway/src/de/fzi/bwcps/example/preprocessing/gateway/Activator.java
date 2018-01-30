package de.fzi.bwcps.example.preprocessing.gateway;

import java.util.Optional;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.com.pubsub.factory.SubscriberFactory;
import de.fzi.bwcps.example.com.pubsub.factory.SubscriberId;
import de.fzi.bwcps.example.preprocessing.factory.DataProcessorId;
import de.fzi.bwcps.example.preprocessing.factory.GatwayProcessorFactory;
import de.fzi.bwcps.example.presentation.factory.DataPresenterFactory;

public class Activator implements BundleActivator {

	private Subscriber subscriber;
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
		
		subscriber = getSubscriber();
		GatewayPreprocessor gatPre = new GatewayPreprocessor(subscriber, DataPresenterFactory.getDefaultDataREpresentation());
		GatwayProcessorFactory.register(DataProcessorId.GATEWAY, gatPre);
		
	}

	private Subscriber getSubscriber() {
		
		try {
			
			Optional<Subscriber> subscriber = SubscriberFactory.getSubscriberInstanceWith(SubscriberId.MQTT);
			return subscriber.orElseThrow(() -> new Exception(String.format("There is no subscriber with id: %s", SubscriberId.MQTT.toString())));
			
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
		subscriber.disconnect();
		
	}

}
