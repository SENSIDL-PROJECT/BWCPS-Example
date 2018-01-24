package de.fzi.bwcps.example.sensor.data;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.fzi.bwcps.example.dataprocess.DataProducer;

public class DataSource extends DataProducer<Map<String,Object>> {

	private final DataFetcher dataFetcher;
	
	public DataSource(DataFetcher dataFetcher) {
		
		this.dataFetcher = dataFetcher;
		
	}

	@Override
	public Map<String, Object> produceData() {
		
		return Stream.of(dataFetcher.getTemperature(), dataFetcher.getLight())
				     .collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()));
		
	}
	
}
