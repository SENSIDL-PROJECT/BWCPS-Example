package de.fzi.bwcps.example.preprocessing.sensor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.fzi.bwcps.example.preprocessing.DataPipe;
import de.fzi.bwcps.example.preprocessing.MeasuredData;
import de.fzi.bwcps.example.preprocessing.factory.Preprocessable;
import de.fzi.sensidl.GalileoGen2.GalileoGen2Data;

public class SensorPreprocessor extends Preprocessable<Map<String,Object>,String> {
	
	private final RawToGalileoGen2DataConverter converter;
	private final DataSerializer serializer;
	private final DataPipe<String> outputPipe;
	private final DataPipe<MeasuredData<Object>> inputDataToConverter;
	
	public SensorPreprocessor() {
		
		DataPipe<MeasuredData<GalileoGen2Data>> converterToSerializer = new DataPipe<MeasuredData<GalileoGen2Data>>();
		
		inputDataToConverter = new DataPipe<MeasuredData<Object>>();
		outputPipe = new DataPipe<String>();
		
		converter = new RawToGalileoGen2DataConverter(inputDataToConverter, converterToSerializer);
		serializer = new DataSerializer(converterToSerializer, outputPipe);
		
	}

	@Override
	public String process(Map<String, Object> data) {
		
		clearInputPipe();
		addToInputPipe(data);
		applyPreprocessors();
		return getResult();
				
	}

	private void clearInputPipe() {
		
		inputDataToConverter.clear();
		
	}

	private void addToInputPipe(Map<String, Object> data) {
		
		transform(data.entrySet().stream()).forEach(measurement -> inputDataToConverter.add(measurement));
		
	}

	private void applyPreprocessors() {
		
		Arrays.asList(converter, serializer).forEach(preprocessor -> preprocessor.apply());
		
	}

	public String getResult() {
		
		if (outputPipe.getData().isEmpty()) {
			
			throw new RuntimeException("There is no output");
			
		}
		
		return outputPipe.getData().get(0);
		
	}

	private List<MeasuredData<Object>> transform(Stream<Map.Entry<String,Object>> measurements) {
		
		return measurements.map(each -> new MeasuredData<Object>(each.getKey(), each.getValue()))
						   .collect(Collectors.toList());
											   
		
	}

	
}
