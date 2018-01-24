package de.fzi.bwcps.example.com.gateway;

import de.fzi.bwcps.example.preprocessing.factory.PreprocessorFactory;
import de.fzi.bwcps.example.preprocessing.factory.PreprocessorID;
import de.fzi.bwcps.example.preprocessing.gateway.GatewayPreprocessor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private GatewayCom gatewayCom;
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
		gatewayCom = new GatewayCom();
		gatewayCom.register((GatewayPreprocessor) PreprocessorFactory.getDataConsumingPreprocessorBy(PreprocessorID.GATEWAY).get());		
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
