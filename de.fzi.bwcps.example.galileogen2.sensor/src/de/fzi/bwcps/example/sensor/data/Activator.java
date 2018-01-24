package de.fzi.bwcps.example.sensor.data;

import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.fzi.bwcps.example.dataprocess.DataConsumer;
import de.fzi.bwcps.example.dataprocess.DataProducer;
import de.fzi.bwcps.example.preprocessing.factory.PreprocessorFactory;
import de.fzi.bwcps.example.preprocessing.factory.PreprocessorID;

public class Activator implements BundleActivator {

	private DataProducer<Map<String,Object>> producer;
	private PeriodicDataFetchingJob fetchingJob;
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		Activator.context = bundleContext;
		
		producer = new DataSource(new RandomDataGenerator());
		PreprocessorFactory.getDataConsumingPreprocessorBy(PreprocessorID.GALILEO_GEN_2)
						   .ifPresent(p -> producer.register((DataConsumer<Map<String,Object>>) p));
		
		fetchingJob = new PeriodicDataFetchingJob(producer);
		fetchingJob.start();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		
		Activator.context = null;
		fetchingJob.terminate();
		
	}

}
