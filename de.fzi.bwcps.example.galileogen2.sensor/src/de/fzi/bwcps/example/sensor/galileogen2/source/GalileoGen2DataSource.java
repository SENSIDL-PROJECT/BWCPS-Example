package de.fzi.bwcps.example.sensor.galileogen2.source;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.kura.KuraException;
import org.osgi.service.component.ComponentContext;

import de.fzi.bwcps.example.sensor.DataSource;

public class GalileoGen2DataSource implements DataSource {

	private DataFetcher dataFetcher;
	
	protected void activate(ComponentContext componentContext) {
		dataFetcher = new RandomDataGenerator();
	}
	
	protected void deactivate(ComponentContext componentContext) throws KuraException {
		dataFetcher = null;
	}
	
	
	@Override
	public Map<String, Object> produce() {
		
		return Stream.of(dataFetcher.getTemperature(), dataFetcher.getLight())
			     	 .collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()));
		
	}
	
}
