package de.fzi.bwcps.example.dataprocessing.util;

import java.util.List;
import java.util.Optional;

import de.fzi.bwcps.example.preprocessing.AbstractFilter;
import de.fzi.bwcps.example.preprocessing.DataPipe;

public class DataProcessorManager<T,U> {

	private DataPipe<T> input;
	private DataPipe<U> output;
	private List<AbstractFilter<?,?>> dataFilter;
	
	public DataProcessorManager(DataPipe<T> input, DataPipe<U> output, List<AbstractFilter<?,?>> dataFilter) {
		
		this.input = input;
		this.output = output;
		this.dataFilter = dataFilter;
		
	}
	
	public void resetWithNew(List<T> inputData) {
		
		this.output.clear();
		this.input.clear();
		inputData.forEach(data -> this.input.add(data));
		
	}
	
	public void applyAllFilter() {
		
		this.dataFilter.forEach(filter -> filter.apply());
		
	}
	
	public Optional<U> getFirstResult() {
		
		return getResult().map(result -> result.get(0));
		
	}
	
	public Optional<List<U>> getAllResults() {
		
		return getResult();
		
	}
	
	private Optional<List<U>> getResult() {
		
		return this.output.getData().isEmpty() ? Optional.empty() : Optional.of(this.output.getData());
		
	}
	
}
