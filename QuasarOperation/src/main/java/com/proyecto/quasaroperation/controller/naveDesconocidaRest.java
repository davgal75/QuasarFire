package com.proyecto.quasaroperation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.quasaroperation.dtos.RespuestaDto;
import com.proyecto.quasaroperation.dtos.SateliteDto;
import com.proyecto.quasaroperation.dtos.SatelitesDto;
import com.proyecto.quasaroperation.exception.BadLocationException;
import com.proyecto.quasaroperation.exception.BadMessageException;
import com.proyecto.quasaroperation.exception.NoPositionException;
import com.proyecto.quasaroperation.services.naveDesconicidaService;

@RestController
@RequestMapping(value="/")
public class naveDesconocidaRest {
	
	@Autowired
	private naveDesconicidaService navedesconocidaSrv;
	
	//Metodo que recibe varios satelites al tiempo con el fin de obtener un mensaje y la posicion de la nave
	@CrossOrigin
	@PostMapping("topsecret")
    public ResponseEntity<RespuestaDto> topsecret(
    		@RequestBody SatelitesDto satelitesDto){
		try {
			return new ResponseEntity<RespuestaDto>(navedesconocidaSrv.descifradatos(satelitesDto), HttpStatus.OK);
		} catch (BadMessageException | BadLocationException | NoPositionException e) {
			RespuestaDto res = new RespuestaDto();
			res.setMessage("Datos insuficientes");
			return new ResponseEntity<RespuestaDto>(res, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RespuestaDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	//Metodo que recibe la informacion de un solo satelite
	@CrossOrigin
	@PostMapping("topsecret_split/{satellite_name}")
    public void topsecretsplitpost(
    		@PathVariable String satellite_name,   @RequestBody SateliteDto sateliteDto){
		try {
			sateliteDto.setName(satellite_name);
			navedesconocidaSrv.AsignadatosSatelite(sateliteDto);
		} catch (Exception e) {
			
		}
    }
	
	//Metodo que retorna el mensaje y posicion de la nave
	@CrossOrigin
	@GetMapping("topsecret_split/")
    public ResponseEntity<RespuestaDto> topsecretsplitget(){
		try {
			return new ResponseEntity<RespuestaDto>(navedesconocidaSrv.descifradatosxget(), HttpStatus.OK);
		} catch (BadMessageException | BadLocationException | NoPositionException e) {
			RespuestaDto res = new RespuestaDto();
			res.setMessage("Datos insuficientes");
			return new ResponseEntity<RespuestaDto>(res, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RespuestaDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
