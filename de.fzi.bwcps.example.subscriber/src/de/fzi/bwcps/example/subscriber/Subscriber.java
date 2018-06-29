package de.fzi.bwcps.example.subscriber;

import de.fzi.bwcps.example.com.ConnectionConfig;
import de.fzi.bwcps.example.com.pubsub.ISubscriber;
import de.fzi.bwcps.example.subscriber.adapter.DataServiceAdapter;

import java.util.function.Consumer;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.data.DataService;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Subscriber that listens to a given topic.
 * 
 * @author czogalik
 *
 */
@Component(service = ISubscriber.class, property = { "service.pid = de.fzi.bwcps.example.subscriber.Subscriber" })
public class Subscriber implements DataServiceAdapter, ISubscriber {

	private static final Logger s_logger = LoggerFactory.getLogger(Subscriber.class);

	/**
	 * Provided by Kura.
	 */
	private DataService dataService;
	private Consumer<String> arrivedMessageHandler = null;

	public Subscriber() {
		super();
	}

	@Reference
	protected void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	protected void unsetDataService(DataService dataService) {
		this.dataService = null;
	}

	@Activate
	protected void activate(ComponentContext componentContext) throws Exception {
		dataService.addDataServiceListener(this);
		subscribe(ConnectionConfig.defaultConfig().topic);
	}

	protected void deactivate() {
		if (dataService != null) {
			dataService.removeDataServiceListener(this);
		}
	}

	@Override
	public boolean isConnected() {
		return this.dataService.isConnected();
	}

	@Override
	public void connect() throws Exception {
		dataService.connect();
	}

	@Override
	public void disconnect() throws Exception {
		dataService.disconnect(0);
	}

	@Override
	public void subscribe(String topic) throws Exception {
		try {
			dataService.subscribe(topic, 1);
			s_logger.info("subscription done to topic: " + ConnectionConfig.defaultConfig().topic);
		} catch (KuraException e) {
			s_logger.info("failed to subscribe: " + e);
		}
	}

	@Override
	public void setArrivedMessageHandler(Consumer<String> arrivedMessageHandler) {
		this.arrivedMessageHandler = arrivedMessageHandler;

	}

	@Override
	public void onMessageArrived(String topic, byte[] payload, int qos, boolean retained) {
		if (arrivedMessageHandler != null) {
			arrivedMessageHandler.accept(new String(payload));
		}
	}

	@Override
	public void onConnectionEstablished() {
		s_logger.info("connection has been established");
		try {
			subscribe(ConnectionConfig.defaultConfig().topic);
		} catch (Exception e) {
			s_logger.error(e.getMessage());
		}
	}

}
