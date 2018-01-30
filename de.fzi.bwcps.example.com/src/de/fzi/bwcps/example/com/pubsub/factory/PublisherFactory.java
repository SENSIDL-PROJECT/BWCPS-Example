package de.fzi.bwcps.example.com.pubsub.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.fzi.bwcps.example.com.ConnectionConfig;
import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.com.pubsub.mqtt.MQTTClient;

public class PublisherFactory {

	private final static Map<PublisherId,Class<? extends Publisher>> publishers;
	static {
		
		publishers = new HashMap<PublisherId, Class<? extends Publisher>>();
		publishers.put(PublisherId.MQTT, MQTTClient.class);
		
	}
	
	public static void register(PublisherId id, Class<? extends Publisher> pubClass) {
		
		publishers.put(id, pubClass);
		
	}
	
	public static void unregister(PublisherId id) {
		
		publishers.remove(id);
		
	}
	
	public static Optional<Publisher> getPublisherInstanceWith(PublisherId id) throws Exception {
		
		return publishers.containsKey(id) ? Optional.of(getInstanceWith(id)) : Optional.empty();
		
	}

	public static Optional<Publisher> getPublisherInstanceWith(PublisherId id, ConnectionConfig config) throws Exception {
		
		return publishers.containsKey(id) ? Optional.of(getInstanceWith(id, config)) : Optional.empty();
		
	}
	
	private static Publisher getInstanceWith(PublisherId id) throws Exception {
		
		return publishers.get(id).newInstance();
		
	}
	
	private static Publisher getInstanceWith(PublisherId id, ConnectionConfig config) throws Exception {
		
		return publishers.get(id).getConstructor(ConnectionConfig.class).newInstance(config);
		
	}
	
}
