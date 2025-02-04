package de.fzi.bwcps.example.sensor;

import java.util.Map;

import org.osgi.service.component.ComponentContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import de.fzi.bwcps.example.preprocessing.DataProcessor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Manages data sources and produces sensor data.
 * 
 * @author scheerer, czogalik
 *
 */
@Component
public class DataSourceManager {

	/**
	 * Produces sensor data.
	 * 
	 * @author sheerer, czogalik
	 *
	 */
	private class DataProductionJob extends Thread {

		public boolean isAlive = true;
		private final DataSource source;
		private final DataProcessor<Map<String, Object>> preproc;

		public DataProductionJob(DataSource source, DataProcessor<Map<String, Object>> preproc) {

			this.source = source;
			this.preproc = preproc;

		}

		@Override
		public void run() {

			while (isAlive) {

				try {

					sleep(1000);

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

				Map<String, Object> measurements = source.produce();
				preproc.process(measurements);

			}

		}

		public void terminate() {

			isAlive = false;

		}

	}

	private DataProductionJob productionJob;
	private DataProcessor<Map<String, Object>> preproc;
	private DataSource source;

	@Reference
	public synchronized void setDataProcessor(DataProcessor<Map<String, Object>> preproc) {
		this.preproc = preproc;
	}

	public synchronized void unsetDataProcessor(DataProcessor<Map<String, Object>> preproc) {
		if (this.preproc == preproc) {
			this.preproc = null;
		}
	}

	@Reference
	public synchronized void setDataSource(DataSource source) {
		this.source = source;
	}

	public synchronized void unsetDataSource(DataSource source) {
		if (this.source == source) {
			this.source = null;
		}
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		productionJob = new DataProductionJob(source, preproc);
		startDataProduction();
	}

	public void startDataProduction() {

		productionJob.start();

	}

	public void stopDataProduction() {

		productionJob.terminate();

	}

}
