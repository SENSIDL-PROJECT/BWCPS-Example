package de.fzi.bwcps.example.com;

/**
 * Defines connect and disconnect to a brooker.
 * 
 * @author scheerer
 *
 */
public interface Connectable {

	public abstract void connect() throws Exception;

	public abstract void disconnect() throws Exception;

	public abstract boolean isConnected();

}
