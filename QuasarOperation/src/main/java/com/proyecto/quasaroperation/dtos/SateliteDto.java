package com.proyecto.quasaroperation.dtos;

//Clase para el manejo de la informacion individual de cada satelite
public class SateliteDto {
	private String name;
	private PositionDto position;
	private double distance;
	private String[] message;
	
	public SateliteDto() {
	}
	
	public SateliteDto(String name, PositionDto position) {
		this.name = name;
		this.position = position;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}
}
