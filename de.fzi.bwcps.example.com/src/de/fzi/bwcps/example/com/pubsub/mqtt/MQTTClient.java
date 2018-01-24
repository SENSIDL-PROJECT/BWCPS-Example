package de.fzi.bwcps.example.com.pubsub.mqtt;

import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import de.fzi.bwcps.example.com.ConnectionConfig;
import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.com.pubsub.Subscriber;

public class MQTTClient implements Publisher, Subscriber, MqttCallback {

	private final ConnectionConfig config;
	private final MqttClient client;
	
	private Consumer<String> arrivedMessageHandler = null;
	
	public MQTTClient() {
		
		this.config = ConnectionConfig.defaultConfig();
		this.client = create();
		
	}
	
	public MQTTClient(ConnectionConfig config) {
		
		this.config = config;
		this.client = create();
		
	}
	
	private MqttClient create() {
		
		try {
			
			return new MqttClient(config.broker, config.clientId, new MemoryPersistence());
			
		} catch (MqttException e) {
			
			throw new RuntimeException(e);
			
		}
		
	}

	@Override
	public void connect() throws Exception {
		
		MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);
		
	}

	@Override
	public void disconnect() throws Exception {
		
		client.disconnect();
		
	}

	@Override
	public void subscribe(String topic) throws Exception {
		
		client.subscribe(topic, config.qos);
		
	}

	@Override
	public void publish(String message) throws Exception {
		
		MqttMessage mqttMessage = toMqttMessage(message);
		client.publish(config.topic, mqttMessage);
		
	}

	private MqttMessage toMqttMessage(String message) {
		
		MqttMessage mqttMessage = new MqttMessage(message.getBytes());
		mqttMessage.setQos(config.qos);
		return mqttMessage;
		
	}

	@Override
	public void connectionLost(Throwable arg0) {
		
		throw new RuntimeException("Connection lost.");
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
		
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		arrivedMessageHandler.accept(new String(message.getPayload()));
		
	}

	@Override
	public void setArrivedMessageHandler(Consumer<String> arrivedMessageHandler) {
		
		this.arrivedMessageHandler = arrivedMessageHandler;
		
	}

}
