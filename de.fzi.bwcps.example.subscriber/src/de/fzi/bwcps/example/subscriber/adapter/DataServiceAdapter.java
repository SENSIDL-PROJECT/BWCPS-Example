package de.fzi.bwcps.example.subscriber.adapter;

import org.eclipse.kura.data.listener.DataServiceListener;

public interface DataServiceAdapter extends DataServiceListener {

	@Override
	default void onConnectionEstablished() {
		// TODO Auto-generated method stub
	}

	@Override
	default void onConnectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void onDisconnecting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	default void onMessageArrived(String arg0, byte[] arg1, int arg2, boolean arg3) {
		// TODO Auto-generated method stub		
	}

	@Override
	default void onMessageConfirmed(int arg0, String arg1) {
		// TODO Auto-generated method stub	
	}

	@Override
	default void onMessagePublished(int arg0, String arg1) {
		// TODO Auto-generated method stub	
	}

}
