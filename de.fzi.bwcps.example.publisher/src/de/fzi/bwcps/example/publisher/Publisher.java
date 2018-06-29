package de.fzi.bwcps.example.publisher;

import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fzi.bwcps.example.com.ConnectionConfig;
import de.fzi.bwcps.example.com.pubsub.IPublisher;

import org.osgi.service.component.annotations.Component;

/**
 * Publisher that publishes sensor data to a given topic.
 * 
 * @author czogalik
 *
 */
@Component(service = IPublisher.class, property = { "service.pid = de.fzi.bwcps.example.publisher.Publisher" })
public class Publisher implements IPublisher {

	private class MqttMessageCallback implements MqttCallback {

		@Override
		public void connectionLost(Throwable arg0) {

		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		@Override
		public void messageArrived(String topic, MqttMessage message) throws Exception {

			if (arrivedMessageHandler != null)
				arrivedMessageHandler.accept(new String(message.getPayload()));

		}

	}

	private final ConnectionConfig config;
	private final MqttClient client;

	private static final Logger s_logger = LoggerFactory.getLogger(Publisher.class);

	private Consumer<String> arrivedMessageHandler = null;

	public Publisher() {

		this(ConnectionConfig.defaultConfig());

	}

	public Publisher(ConnectionConfig config) {

		this.config = config;
		this.client = createMqttClient();

	}

	/**
	 * Creates a mqtt client and returns it.
	 * 
	 * @return a mqtt client
	 */
	private MqttClient createMqttClient() {

		try {

			MqttClient client = new MqttClient(config.broker, createUniqueClientId(), new MemoryPersistence());
			client.setCallback(new MqttMessageCallback());
			return client;

		} catch (MqttException e) {

			throw new RuntimeException(e);

		}

	}

	private String createUniqueClientId() {

		return config.clientId.concat(new Long(System.currentTimeMillis()).toString());

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
	public boolean isConnected() {

		return client.isConnected();

	}

	@Override
	public void publish(String message) throws Exception {

		MqttMessage mqttMessage = toMqttMessage(message);
		client.publish(config.topic, mqttMessage);

		s_logger.info("published to: " + config.topic + "msg: " + mqttMessage);

	}

	/**
	 * Converts a string message into a mqtt message.
	 * 
	 * @param message
	 *            to be converted into an mqtt message.
	 * @return the converted mqtt message.
	 */
	private MqttMessage toMqttMessage(String message) {

		byte[] bytes = message.getBytes();
		MqttMessage mqttMessage = new MqttMessage(bytes);
		mqttMessage.setQos(config.qos);
		return mqttMessage;

	}

}
