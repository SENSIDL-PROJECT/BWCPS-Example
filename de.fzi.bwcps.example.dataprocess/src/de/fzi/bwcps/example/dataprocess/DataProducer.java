package de.fzi.bwcps.example.dataprocess;

import java.util.ArrayList;
import java.util.List;

public abstract class DataProducer<T> {

	private final List<DataConsumer<T>> consumer = new ArrayList<DataConsumer<T>>();
	
	public void register(DataConsumer<T> consumerToRegister) {
		
		consumer.add(consumerToRegister);
		
	}
	
	public void unregister(DataConsumer<T> consumerToUnregister) {
		
		consumer.remove(consumerToUnregister);
		
	}
	
	public void pushToConsumer(T data) {
		
		consumer.forEach(con -> con.consume(data));
		
	}
	
	public void produce() {
		
		T data = produceData();
		pushToConsumer(data);
		
	}

	public abstract T produceData();
	
}
