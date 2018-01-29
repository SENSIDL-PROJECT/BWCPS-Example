package de.fzi.bwcps.example.preprocessing;

public interface DataProcessor<T> {

	public void process(T measurements);
	
}
