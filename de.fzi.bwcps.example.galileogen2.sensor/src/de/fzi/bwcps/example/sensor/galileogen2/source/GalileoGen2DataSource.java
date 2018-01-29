package de.fzi.bwcps.example.sensor.galileogen2.source;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.fzi.bwcps.example.sensor.DataSource;

public class GalileoGen2DataSource implements DataSource {

	private final DataFetcher dataFetcher;
	
	public GalileoGen2DataSource(DataFetcher dataFetcher) {
		
		this.dataFetcher = dataFetcher;
		
	}

	@Override
	public Map<String, Object> produce() {
		
		return Stream.of(dataFetcher.getTemperature(), dataFetcher.getLight())
			     	 .collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()));
		
	}
	
}
