package com.proyecto.quasaroperation.dtos;

//Clase para el manejo de la respuesta
public class RespuestaDto {
	private PositionDto position = new PositionDto();
	private String message;
	
	public PositionDto getPosition() {
		return position;
	}
	
	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
