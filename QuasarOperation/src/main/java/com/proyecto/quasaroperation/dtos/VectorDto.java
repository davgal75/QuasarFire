package com.proyecto.quasaroperation.dtos;

//Clase para el manejo de los vectores formados entre un par de satelites
public class VectorDto {
	private PositionDto posicion = new PositionDto();
	private double distancia;
	
	public VectorDto(PositionDto posSat1, PositionDto posSat2) {
		posicion.setx(posSat2.getx() - posSat1.getx());
		posicion.sety(posSat2.gety() - posSat1.gety());
		distancia = Math.pow((Math.pow(posicion.getx(), 2) + Math.pow(posicion.gety(), 2)),(0.5));
	}
	
	public PositionDto getposicion() {
		return this.posicion;
	}
	
	public double anguloPosX() {
		return Math.atan(posicion.gety() / posicion.getx());
	}
	
	public double anguloNegX() {
		return Math.PI-anguloPosX();
	}
	
	public double getDistance() {
		return distancia;
	}
}
