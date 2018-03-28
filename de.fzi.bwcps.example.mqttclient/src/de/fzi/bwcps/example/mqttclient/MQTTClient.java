package de.fzi.bwcps.example.mqttclient;

import java.util.Date;

import org.eclipse.kura.cloud.CloudClient;
import org.eclipse.kura.cloud.CloudClientListener;
import org.eclipse.kura.cloud.CloudService;
import org.eclipse.kura.message.KuraPayload;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MQTTClient implements CloudClientListener {
	private static final Logger s_logger = LoggerFactory.getLogger(MQTTClient.class);

    private static final String APP_ID = "de.fzi.bwcps.example.mqttclient";   
	
	private CloudService m_cloudService;
    private CloudClient m_cloudClient;

    public void setCloudService(CloudService cloudService) {
        this.m_cloudService = cloudService;
    }

    public void unsetCloudService(CloudService cloudService) {
        this.m_cloudService = null;
    }
    
    protected void activate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has started!");

        s_logger.debug(APP_ID + ": This is a debug message.");
        
        try {

            // Acquire a Cloud Application Client for this Application
            s_logger.info("Getting CloudClient for {}...", APP_ID);
            this.m_cloudClient = this.m_cloudService.newCloudClient(APP_ID);
            this.m_cloudClient.addCloudClientListener(this);

            // Don't subscribe because these are handled by the default
            // subscriptions and we don't want to get messages twice
           // doUpdate(false);
        } catch (Exception e) {
            s_logger.error("Error during component activation", e);
            throw new ComponentException(e);
        }

    }

    protected void deactivate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has stopped!");

        // shutting down the worker and cleaning up the properties
        //this.m_worker.shutdown();

        // Releasing the CloudApplicationClient
        s_logger.info("Releasing CloudApplicationClient for {}...", APP_ID);
        this.m_cloudClient.release();

        s_logger.debug("Deactivating Heater... Done.");
    }
    
    public void publish(String message) throws Exception {
		String topic = "GalileoGen2/";				
        Integer qos = 2;
        
     // Allocate a new payload
        KuraPayload payload = new KuraPayload();

        // Timestamp the message
        payload.setTimestamp(new Date());

        // Add the temperature as a metric to the payload
        payload.addMetric("temperature", message);
        
     // Publish the message
        try {
            this.m_cloudClient.publish(topic, payload, qos, true);
            s_logger.info("Published to {} message: {}", topic, payload);
        } catch (Exception e) {
            s_logger.error("Cannot publish topic: " + topic, e);
        }
		
	}

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
}
