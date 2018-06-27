package de.fzi.bwcps.example.preprocessing;

import java.util.ArrayList;
import java.util.List;

public class DataPipe<T> {

	protected final List<T> data = new ArrayList<T>();
	
	public DataPipe() {
	
		
	}
	
	public List<T> getData() {
		
		return data;
		
	}
	
	public void clear() {
		
		data.clear();
		
	}
	
	public void add(T dataToAdd) {
		
		data.add(dataToAdd);
		
	}
	
}
