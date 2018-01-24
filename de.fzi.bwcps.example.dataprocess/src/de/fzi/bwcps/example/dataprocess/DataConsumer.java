package de.fzi.bwcps.example.dataprocess;

public interface DataConsumer<T> {

	public void consume(T data);
	
}
