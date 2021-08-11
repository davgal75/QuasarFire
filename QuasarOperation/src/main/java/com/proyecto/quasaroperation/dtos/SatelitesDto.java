package com.proyecto.quasaroperation.dtos;

import java.util.ArrayList;
import java.util.List;

//Clase para el manejo del total de los satelites
public class SatelitesDto {
	
	private List<SateliteDto> satellites = new ArrayList<>();

	public List<SateliteDto> getsatellites() {
		return satellites;
	}
	
	public void setsatellites(List<SateliteDto> satelites) {
		this.satellites = satelites;
	}
	
}
