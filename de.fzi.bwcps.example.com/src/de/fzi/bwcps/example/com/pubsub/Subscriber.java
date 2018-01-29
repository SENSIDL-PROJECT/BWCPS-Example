package de.fzi.bwcps.example.com.pubsub;

import java.util.function.Consumer;

import de.fzi.bwcps.example.com.Connectable;

public interface Subscriber extends Connectable {
	
	public abstract void subscribe() throws Exception;
	public abstract void subscribe(String topic) throws Exception;
	public abstract void setArrivedMessageHandler(Consumer<String> arrivedMessageHandler);
	
}
