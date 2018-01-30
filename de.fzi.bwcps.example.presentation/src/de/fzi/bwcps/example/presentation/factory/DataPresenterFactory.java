package de.fzi.bwcps.example.presentation.factory;

import de.fzi.bwcps.example.presentation.DataPresenter;
import de.fzi.bwcps.example.presentation.DefaultDataPresenter;

public class DataPresenterFactory {

	public static DataPresenter getDefaultDataRepresentation() {
		
		return new DefaultDataPresenter();
		
	}
	
}
