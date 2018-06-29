package de.fzi.bwcps.example.com.pubsub;

import de.fzi.bwcps.example.com.Connectable;

/**
 * Publisher publishing to a topic.
 * 
 * @author scheerer
 *
 */
public interface IPublisher extends Connectable {

	public abstract void publish(String message) throws Exception;

}
