package de.fzi.bwcps.example.sensor.data;

import java.util.Map;

import de.fzi.bwcps.example.dataprocess.DataProducer;

public class PeriodicDataFetchingJob extends Thread {

	private boolean isAlive = true;
	
	private final DataProducer<Map<String,Object>> producer;
	
	public PeriodicDataFetchingJob(DataProducer<Map<String,Object>> producer) {
		
		this.producer = producer;
		
	}
	
	@Override
	public void run() {
		
		while(isAlive) {
			
			try {
				
				sleep(1000);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				
			}
			
			producer.produce();
			
		}
		
	}
	
	public void terminate() {
		
		isAlive = false;
		
	}

}
