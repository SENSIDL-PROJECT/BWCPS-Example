package de.fzi.bwcps.example.sensor;

import java.util.Map;

import de.fzi.bwcps.example.preprocessing.DataProcessor;
import de.fzi.bwcps.example.preprocessing.factory.SensorDataProcessorFactory;

public class DataSourceManager {

	private class DataProductionJob extends Thread {

		public boolean isAlive = true;
		private final DataSource source;
		private final DataProcessor<Map<String,Object>> preproc;
		
		public DataProductionJob(DataSource source, DataProcessor<Map<String,Object>> preproc) {
			
			this.source = source;
			this.preproc = preproc;
			
		}
		
		@Override
		public void run() {
			
			while(isAlive) {
				
				try {
					
					sleep(1000);
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
					
				}
				
				Map<String,Object> measurements = source.produce();
				preproc.process(measurements);
				
			}
			
		}
		
		public void terminate() {
			
			isAlive = false;
			
		}

	}
	
	private final DataProductionJob productionJob;
	
	public DataSourceManager(DataSource source, String id) {
		
		DataProcessor<Map<String,Object>> preproc = SensorDataProcessorFactory.getInstanceWith(id)
												 						.orElseThrow(() -> new RuntimeException("Data processor does not exist"));
		productionJob = new DataProductionJob(source, preproc);
		
	}
	
	public void startDataProduction() {
		
		productionJob.start();
		
	}
	
	public void stopDataProduction() {
		
		productionJob.terminate();
		
	}
	
}
