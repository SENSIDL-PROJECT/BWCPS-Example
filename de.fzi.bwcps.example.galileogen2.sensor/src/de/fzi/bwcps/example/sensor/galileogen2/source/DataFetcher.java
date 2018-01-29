package de.fzi.bwcps.example.sensor.galileogen2.source;

public interface DataFetcher {

	public DataEntry getTemperature();
	
	public DataEntry getLight();
	
}
