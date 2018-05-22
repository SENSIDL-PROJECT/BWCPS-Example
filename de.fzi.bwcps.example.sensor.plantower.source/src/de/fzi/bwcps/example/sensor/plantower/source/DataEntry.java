package de.fzi.bwcps.example.sensor.plantower.source;

import java.util.Map;

public class DataEntry implements Map.Entry<String, Object> {
	
	private String key;
	private Object value;
	
	private DataEntry(String key, Object value) {
		
		setKey(key);
		setValue(value);
		
	}

	public static DataEntry of(String key, Object value) {
		
		return new DataEntry(key, value);
		
	}
	
	private void setKey(String key) {
		
		this.key = key;
		
	}
	
	@Override
	public String getKey() {
		
		return key;
		
	}

	@Override
	public Object getValue() {
		
		return value;
		
	}

	@Override
	public Object setValue(Object value) {
		
		Object old = this.value;
		this.value = value;
		return old;
	}
	
	
	
}
