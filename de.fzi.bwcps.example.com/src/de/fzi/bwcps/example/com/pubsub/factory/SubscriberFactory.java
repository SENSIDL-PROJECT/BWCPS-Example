package de.fzi.bwcps.example.com.pubsub.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.fzi.bwcps.example.com.ConnectionConfig;
import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.com.pubsub.mqtt.MQTTClient;

public class SubscriberFactory {

	private final static Map<SubscriberId,Class<? extends Subscriber>> subscribers;
	static {
		
		subscribers = new HashMap<SubscriberId, Class<? extends Subscriber>>();
		subscribers.put(SubscriberId.MQTT, MQTTClient.class);
		
	}
	
	public static void register(SubscriberId id, Class<? extends Subscriber> pubClass) {
		
		subscribers.put(id, pubClass);
		
	}
	
	public static void unregister(SubscriberId id) {
		
		subscribers.remove(id);
		
	}
	
	public static Optional<Subscriber> getSubscriberInstanceWith(SubscriberId id) throws Exception {
		
		return subscribers.containsKey(id) ? Optional.of(getInstanceWith(id)) : Optional.empty();
		
	}

	public static Optional<Subscriber> getSubscriberInstanceWith(SubscriberId id, ConnectionConfig config) throws Exception {
		
		return subscribers.containsKey(id) ? Optional.of(getInstanceWith(id, config)) : Optional.empty();
		
	}
	
	private static Subscriber getInstanceWith(SubscriberId id) throws Exception {
		
		return subscribers.get(id).newInstance();
		
	}
	
	private static Subscriber getInstanceWith(SubscriberId id, ConnectionConfig config) throws Exception {
		
		return subscribers.get(id).getConstructor(ConnectionConfig.class).newInstance(config);
		
	}
	
}
