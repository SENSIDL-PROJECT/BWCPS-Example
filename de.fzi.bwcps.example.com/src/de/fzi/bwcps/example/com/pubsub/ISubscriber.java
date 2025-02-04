package de.fzi.bwcps.example.com.pubsub;

import java.util.function.Consumer;

import de.fzi.bwcps.example.com.Connectable;

/**
 * Subscriber listening on a topic.
 * 
 * @author scheerer
 *
 */
public interface ISubscriber extends Connectable {

	public abstract void subscribe(String topic) throws Exception;

	public abstract void setArrivedMessageHandler(Consumer<String> arrivedMessageHandler);

}
