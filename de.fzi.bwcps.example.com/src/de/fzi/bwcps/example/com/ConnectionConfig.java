package de.fzi.bwcps.example.com;

public class ConnectionConfig {

	public String topic;
	public int qos;
	public String broker;
	public String clientId;
	
	public static ConnectionConfig defaultConfig() {
		
		ConnectionConfig config = new ConnectionConfig();
		config.topic = "GalileoGen2";
		config.qos = 2;
		config.broker = "tcp://iot.eclipse.org:1883";
		config.clientId = "MQTT Client";
		return config;
		
	}
	
}
