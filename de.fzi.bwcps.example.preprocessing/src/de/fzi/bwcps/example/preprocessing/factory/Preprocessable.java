package de.fzi.bwcps.example.preprocessing.factory;

import de.fzi.bwcps.example.dataprocess.DataConsumer;
import de.fzi.bwcps.example.dataprocess.DataProducer;

public abstract class Preprocessable<U,T> extends DataProducer<T> implements DataConsumer<U> {

	private T processedData;
	
	public abstract T process(U data);
	
	@Override
	public void consume(U data) {
		
		setProcessedData(process(data));
		produce();
		
	}

	@Override
	public T produceData() {
		
		return this.processedData;
		
	}
	
	private void setProcessedData(T processedData) {
		
		this.processedData = processedData;
		
	}
	
}
