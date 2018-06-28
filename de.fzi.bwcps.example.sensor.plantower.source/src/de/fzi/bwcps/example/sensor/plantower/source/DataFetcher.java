package de.fzi.bwcps.example.sensor.plantower.source;

public interface DataFetcher {
	
	public void produce();
	
	public DataEntry getChecksum();

	public DataEntry getPMSx0031();
	
	public DataEntry getPMSx0032();
	
	public DataEntry getPMSx0033();
	
}
