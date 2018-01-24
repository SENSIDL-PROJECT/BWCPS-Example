package de.fzi.bwcps.example.com.gateway;

import java.util.function.Consumer;

import de.fzi.bwcps.example.com.pubsub.Subscriber;
import de.fzi.bwcps.example.com.pubsub.mqtt.MQTTClient;
import de.fzi.bwcps.example.dataprocess.DataProducer;

public class GatewayCom extends DataProducer<String> {

	private final Subscriber subscriber;
	
	private String receivedMessageCache;
	
	public GatewayCom() {
		
		this.subscriber = new MQTTClient();
		this.subscriber.setArrivedMessageHandler(getMessagePropagationHandler());  
		
	}
	
	private Consumer<String> getMessagePropagationHandler() {
		
		return message -> {
			
			setReceivedMessageCache(message);
			produce();
			
		};
		
	}

	private void setReceivedMessageCache(String message) {
		
		receivedMessageCache = message;
		
	}
	
	@Override
	public String produceData() {
		
		return receivedMessageCache;
		
	}

}
