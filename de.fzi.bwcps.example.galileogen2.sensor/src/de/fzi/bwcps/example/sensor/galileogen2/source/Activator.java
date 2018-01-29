package de.fzi.bwcps.example.sensor.galileogen2.source;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.fzi.bwcps.example.sensor.DataSource;
import de.fzi.bwcps.example.sensor.DataSourceManager;


public class Activator implements BundleActivator {

	private final static String PREPROCESSOR_ID = "GALILEO_GEN_2";
	
	private DataSourceManager manager;
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
		
		DataSource source = new GalileoGen2DataSource(new RandomDataGenerator());
		manager = new DataSourceManager(source, PREPROCESSOR_ID);
		manager.startDataProduction();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		
		Activator.context = null;
		manager.stopDataProduction();
		
	}

}
