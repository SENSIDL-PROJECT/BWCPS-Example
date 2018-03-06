package org.subscribe.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.cloud.CloudClient;
import org.eclipse.kura.cloud.CloudClientListener;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.configuration.ConfigurableComponent;

import org.eclipse.kura.message.KuraPayload;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Subscriber implements ConfigurableComponent, CloudClientListener {

	private static final Logger s_logger = LoggerFactory.getLogger(Subscriber.class);

	private static final String APP_ID = "App_id.ID";
	private String SUBSCRIBER_APP_ID;

	private static String PUBLISH_QOS_PROP_NAME = "subscribe.qos";
	private static String PUBLISH_TOPIC_PROP_NAME = "subscribe.semanticTopic";

	private CloudService m_cloudService;
	private CloudClient m_cloudClient;

	private final ScheduledExecutorService m_worker;
	private ScheduledFuture<?> m_handle;

	private Map<String, Object> m_properties;
	private Map<String, Integer> m_subscriptions;

	public Subscriber() {
		super();
		this.m_worker = Executors.newSingleThreadScheduledExecutor();
		this.m_subscriptions = new HashMap<>();
	}

	public void setCloudService(CloudService cloudService) {
		this.m_cloudService = cloudService;
	}

	public void unsetCloudService(CloudService cloudService) {
		this.m_cloudService = null;
	}

	protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
		this.m_properties = properties;
		SUBSCRIBER_APP_ID = (String) this.m_properties.get(APP_ID);

		for (String s : this.m_properties.keySet()) {
			s_logger.info("Activate - " + s + ": " + this.m_properties.get(s));
		}

		try {
			getMqttClient();
		} catch (KuraException e) {
			s_logger.error("Error during component activation", e);
			throw new ComponentException(e);
		}

		s_logger.info("Activating the service subscription... Done.");
	}

	protected void deactivate(ComponentContext componentContext) throws KuraException {
		s_logger.debug("Deactivating Heater...");

		// Releasing the CloudApplicationClient
		s_logger.info("Releasing CloudApplicationClient for {}...", APP_ID);

		m_cloudClient.unsubscribe(APP_ID);

		s_logger.debug("Deactivating service... Done.");
	}

	public void updated(Map<String, Object> properties) throws KuraException {
		s_logger.info("Updated Subscriber...");

		// store the properties received
		this.m_properties = properties;
		for (String s : this.m_properties.keySet()) {
			s_logger.info("Update - " + s + ": " + this.m_properties.get(s));
		}

		doUpdate(true);
		s_logger.info("Updated Subscriber... Done.");
	}

	@Override
	public void onMessageArrived(String deviceId, String appTopic, KuraPayload msg, int qos, boolean retain) {
		s_logger.info("message arrived! appTopic: {}; message: {}", appTopic, msg.getMetric("temperatureInternal"));
	}

	@Override
	public void onControlMessageArrived(String deviceId, String appTopic, KuraPayload msg, int qos, boolean retain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionLost() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionEstablished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageConfirmed(int messageId, String appTopic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessagePublished(int messageId, String appTopic) {
		// TODO Auto-generated method stub

	}

	private void getMqttClient() throws KuraException {
		s_logger.info("Getting CloudClient for {}...", SUBSCRIBER_APP_ID);
		this.m_cloudClient = this.m_cloudService.newCloudClient(SUBSCRIBER_APP_ID);
		this.m_cloudClient.addCloudClientListener(this);

		doUpdate(false);
	}

	private void doUpdate(boolean onUpdate) {
		// cancel a current worker handle if one if active
		if (this.m_handle != null) {
			this.m_handle.cancel(true);
		}

		this.m_handle = this.m_worker.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName(getClass().getSimpleName());
				doSubscribe();
			}
		}, 0, 2, TimeUnit.SECONDS);
	}

	private void doSubscribe() {
		String topic = (String) this.m_properties.get(PUBLISH_TOPIC_PROP_NAME);
		Integer qos = (Integer) this.m_properties.get(PUBLISH_QOS_PROP_NAME);

		try {
			if (this.m_cloudClient.isConnected() && !this.m_subscriptions.containsKey(topic)) {
				this.m_cloudClient.subscribe(topic, qos);
				System.out.println("Subscribed to topic: " + topic + " with QOS: " + qos);
				this.m_subscriptions.put(topic, qos);
			}
		} catch (KuraException ex) {
			s_logger.error("Cannot subscribe to topic: " + topic, ex);
		}
	}

}
