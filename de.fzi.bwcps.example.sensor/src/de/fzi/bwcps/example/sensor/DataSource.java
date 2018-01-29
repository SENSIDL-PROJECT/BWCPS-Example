package de.fzi.bwcps.example.sensor;

import java.util.Map;

public interface DataSource {
	
	public Map<String,Object> produce();
	
}
