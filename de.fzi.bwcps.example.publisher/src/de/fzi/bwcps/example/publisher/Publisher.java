package de.fzi.bwcps.example.publisher;

import java.util.Date;
import java.util.Map;
import java.util.Random;
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

import de.fzi.bwcps.example.com.pubsub.IPublisher;

public class Publisher implements ConfigurableComponent, CloudClientListener, IPublisher {

	private static final Logger s_logger = LoggerFactory.getLogger(Publisher.class);
	
    private static final String PUBLISH_TOPIC_PROP_NAME = "publish.semanticTopic";
    private static final String PUBLISH_QOS_PROP_NAME = "publish.qos";
    private static final String PUBLISH_RETAIN_PROP_NAME = "publish.retain";
	
	// Cloud Application identifier
    private static final String APP_ID = "publisher";
	
	private CloudService m_cloudService;
	private CloudClient m_cloudClient;

	private final ScheduledExecutorService m_worker;
	private ScheduledFuture<?> m_handle;

	private Map<String, Object> m_properties;
	
	private boolean isConnected = false;
	
	public Publisher() {
		super();
		this.m_worker = Executors.newSingleThreadScheduledExecutor();
	}

	public void setCloudService(CloudService cloudService) {
		this.m_cloudService = cloudService;
	}

	public void unsetCloudService(CloudService cloudService) {
		this.m_cloudService = null;
	}

	protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
		
		this.m_properties = properties;

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
		isConnected = true;
	}
	private void getMqttClient() throws KuraException {
		s_logger.info("Getting CloudClient for {}...", APP_ID);
		this.m_cloudClient = this.m_cloudService.newCloudClient(APP_ID);
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
				//doSubscribe();
			}
		}, 0, 2, TimeUnit.SECONDS);
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
	
	
	//CloudClientListener
	@Override
	public void onConnectionEstablished() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionLost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onControlMessageArrived(String arg0, String arg1, KuraPayload arg2, int arg3, boolean arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageArrived(String arg0, String arg1, KuraPayload arg2, int arg3, boolean arg4) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessageConfirmed(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessagePublished(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	//Publisher
	@Override
	public void connect() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return isConnected;
	}

	@Override
	public void publish(String message) throws Exception {
		// fetch the publishing configuration from the publishing properties
        String topic = (String) this.m_properties.get(PUBLISH_TOPIC_PROP_NAME);
        Integer qos = (Integer) this.m_properties.get(PUBLISH_QOS_PROP_NAME);
        Boolean retain = (Boolean) this.m_properties.get(PUBLISH_RETAIN_PROP_NAME);
        //String mode = (String) this.m_properties.get(MODE_PROP_NAME);

        // Allocate a new payload
        KuraPayload payload = new KuraPayload();

        // Timestamp the message
        payload.setTimestamp(new Date());

        // Add the temperature as a metric to the payload
        payload.addMetric("temperature", message);

        Random random = new Random();
        int code = random.nextInt();
        if (random.nextInt() % 5 == 0) {
            payload.addMetric("errorCode", code);
        } else {
            payload.addMetric("errorCode", 0);
        }

        // Publish the message
        try {
            this.m_cloudClient.publish(topic, payload, qos, retain);
            s_logger.info("Published to {} message: {}", topic, payload);
        } catch (Exception e) {
            s_logger.error("Cannot publish topic: " + topic, e);
        }
		
	}

}
