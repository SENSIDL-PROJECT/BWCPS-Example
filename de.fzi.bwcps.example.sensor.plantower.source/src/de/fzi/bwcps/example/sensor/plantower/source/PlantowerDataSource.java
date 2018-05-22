package de.fzi.bwcps.example.sensor.plantower.source;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.kura.KuraException;
import org.osgi.service.component.ComponentContext;

import de.fzi.bwcps.example.sensor.DataSource;

public class PlantowerDataSource implements DataSource {

	private DataFetcher dataFetcher;
	
	protected void activate(ComponentContext componentContext) {
		dataFetcher = new RandomDataGenerator();
	}
	
	protected void deactivate(ComponentContext componentContext) throws KuraException {
		dataFetcher = null;
	}
	
	
	@Override
	public Map<String, Object> produce() {
		
		return Stream.of(dataFetcher.getPMSx0031(), dataFetcher.getPMSx0032(), dataFetcher.getPMSx0033())
			     	 .collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()));
		
	}
	
}
