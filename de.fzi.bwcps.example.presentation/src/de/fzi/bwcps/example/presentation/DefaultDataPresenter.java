package de.fzi.bwcps.example.presentation;

import java.util.List;

public class DefaultDataPresenter implements DataPresenter {
	
	@Override
	public void display(List<DataRepresentation> dataToDisplay) {
		
		dataToDisplay.forEach(data -> display(data));
		
	}

	private void display(DataRepresentation data) {
		
		System.out.println(String.format("Measurement %1s with value: %2s", data.getName(), data.getValue()));
		
	}

}
