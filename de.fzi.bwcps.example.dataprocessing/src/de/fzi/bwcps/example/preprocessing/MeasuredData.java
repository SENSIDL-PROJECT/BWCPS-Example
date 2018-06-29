package de.fzi.bwcps.example.preprocessing;

public class MeasuredData<T> {

	private String id;
	private T measuredData;

	public MeasuredData(String id, T measuredData) {

		setId(id);
		setMeasuredData(measuredData);

	}

	public String getId() {

		return id;

	}

	public void setId(String id) {

		this.id = id;

	}

	public T getMeasuredData() {

		return measuredData;

	}

	public void setMeasuredData(T measuredData) {

		this.measuredData = measuredData;

	}

}
