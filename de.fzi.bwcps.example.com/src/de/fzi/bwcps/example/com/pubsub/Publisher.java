package de.fzi.bwcps.example.com.pubsub;

import de.fzi.bwcps.example.com.Connectable;

public interface Publisher extends Connectable {
	
	public abstract void publish(String message) throws Exception;
	
}
