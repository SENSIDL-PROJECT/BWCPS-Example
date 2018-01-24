package de.fzi.bwcps.example.com;

import java.util.function.Consumer;

import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.com.pubsub.mqtt.MQTTClient;

public class ComFactory {

	public static Publisher createMqttClient(ConnectionConfig config) {
		
		return new MQTTClient(config);
		
	}
	
	public static Subscriber createMqttClient(ConnectionConfig config, Consumer<String> arrivedMessageHandler) {
		
		MQTTClient client = new MQTTClient(config);
		client.setArrivedMessageHandler(arrivedMessageHandler);
		return client;
		
	}
	
}
