package de.fzi.bwcps.example.preprocessing.sensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.fzi.bwcps.example.com.sensor.SensorCom;
import de.fzi.bwcps.example.preprocessing.factory.PreprocessorFactory;
import de.fzi.bwcps.example.preprocessing.factory.PreprocessorID;

public class Activator implements BundleActivator {

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
		SensorPreprocessor pre = (SensorPreprocessor) PreprocessorFactory.getPreprocessorBy(PreprocessorID.GALILEO_GEN_2).get();
		pre.register(new SensorCom());
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
