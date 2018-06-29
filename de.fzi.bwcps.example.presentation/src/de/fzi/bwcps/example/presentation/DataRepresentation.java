package de.fzi.bwcps.example.presentation;

/**
 * Representation of data to be presented.
 * 
 * @author scheerer
 *
 */
public class DataRepresentation {

	private final String name;
	private final String value;

	public DataRepresentation(String name, String value) {

		this.name = name;
		this.value = value;

	}

	public String getName() {

		return name;

	}

	public String getValue() {

		return value;

	}

}
