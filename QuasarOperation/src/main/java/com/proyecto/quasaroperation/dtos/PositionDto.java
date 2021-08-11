package com.proyecto.quasaroperation.dtos;

//Clase para manejo de las posiciones tanto de satelites como de vectores

public class PositionDto {
	private double x;
	private double y;
	
	public PositionDto() {
	}
	
	public PositionDto(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getx() {
		return x;
	}
	
	public void setx(double x) {
		this.x = x;
	}

	public double gety() {
		return y;
	}

	public void sety(double y) {
		this.y = y;
	}
}
