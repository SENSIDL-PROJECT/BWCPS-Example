package de.fzi.bwcps.example.preprocessing;

public abstract class AbstractFilter<U,T> {

	protected final DataPipe<U> input;
	protected final DataPipe<T> output;
	
	public AbstractFilter(DataPipe<U> input, DataPipe<T> output) {
		
		this.input = input;
		this.output = output;
		
	}
	
	public abstract void apply();
	
}
