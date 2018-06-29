package de.fzi.bwcps.example.subscriber.adapter;

import org.eclipse.kura.data.listener.DataServiceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapter that hides unused data service methods.
 * 
 * @author czogalik
 *
 */
public interface DataServiceAdapter extends DataServiceListener {

	static final Logger s_logger = LoggerFactory.getLogger(DataServiceAdapter.class);

	@Override
	default void onConnectionEstablished() {
		s_logger.info("connection established");
	}

	@Override
	default void onConnectionLost(Throwable arg0) {
		s_logger.info("connection lost");
	}

	@Override
	default void onDisconnected() {
		s_logger.info("disconnected");
	}

	@Override
	default void onDisconnecting() {
		s_logger.info("disconnection");
	}

	@Override
	default void onMessageArrived(String arg0, byte[] arg1, int arg2, boolean arg3) {
		s_logger.info("message arrived");
	}

	@Override
	default void onMessageConfirmed(int arg0, String arg1) {
		s_logger.info("message confirmed");
	}

	@Override
	default void onMessagePublished(int arg0, String arg1) {
		s_logger.info("message published");
	}

}
