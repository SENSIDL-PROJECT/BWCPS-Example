package de.fzi.bwcps.example.com.sensor;

import de.fzi.bwcps.example.com.pubsub.Publisher;
import de.fzi.bwcps.example.com.pubsub.mqtt.MQTTClient;
import de.fzi.bwcps.example.dataprocess.DataConsumer;

public class SensorCom implements DataConsumer<String> {

	private final Publisher publisher;
	
	public SensorCom() {
		
		this.publisher = new MQTTClient();
		
	}
	
	@Override
	public void consume(String data) {
		
		try {
			
			publisher.publish(data);
			
		} catch (Exception e) {

			//Logging...
			
		}
		
	}

}
