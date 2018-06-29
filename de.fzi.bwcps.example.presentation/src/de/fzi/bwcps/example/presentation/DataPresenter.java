package de.fzi.bwcps.example.presentation;

import java.util.List;

/**
 * Presents given data.
 * 
 * @author scheerer
 *
 */
public interface DataPresenter {

	/**
	 * Presents the given data.
	 * 
	 * @param dataToDisplay
	 *            the given data to display.
	 */
	public void display(List<DataRepresentation> dataToDisplay);

}
