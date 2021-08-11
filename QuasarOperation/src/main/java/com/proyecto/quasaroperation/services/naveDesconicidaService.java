package com.proyecto.quasaroperation.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.quasaroperation.dtos.PositionDto;
import com.proyecto.quasaroperation.dtos.RespuestaDto;
import com.proyecto.quasaroperation.dtos.SateliteDto;
import com.proyecto.quasaroperation.dtos.SatelitesDto;
import com.proyecto.quasaroperation.dtos.VectorDto;
import com.proyecto.quasaroperation.exception.BadLocationException;
import com.proyecto.quasaroperation.exception.BadMessageException;
import com.proyecto.quasaroperation.exception.NoPositionException;

//Servicio con la logica para la ubicacion y decodificacion del mensaje de una nave desconocida
@Service
public class naveDesconicidaService {
	
	private SatelitesDto sat = new SatelitesDto();
	
	public naveDesconicidaService() {
		inicializasatelites("Fully");
	}
	
	private void inicializasatelites(String tipo) {
		SatelitesDto sattemp = new SatelitesDto();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			//se cargan los datos de los satelites, que bien podria venir de una BD
			sattemp = objectMapper.readValue(new File("/satelite.json"), SatelitesDto.class);
			for (SateliteDto x: sattemp.getsatellites()) {
				boolean asignado = false;
				for (int y = 0; y < sat.getsatellites().size(); y++) {
					if (x.getName().equals(sat.getsatellites().get(y).getName())) {
						asignado = true;
						sat.getsatellites().get(y).setPosition(x.getPosition());
						if (tipo.equals("Fully")) {
							sat.getsatellites().get(y).setMessage(null);
							sat.getsatellites().get(y).setDistance(0);
						}
						break;
					}
				}
				//Significa que es un satelite nuevo y no configurado desde la ultima ejecucion
				if (!asignado) {
					sat.getsatellites().add(x);
				}
			}
			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Metodo que saca los datos recibidos de los satelites y obtiene la ubicacion de la nave desconocida y su mensaje
	public RespuestaDto descifradatos(SatelitesDto satelites) throws BadLocationException, BadMessageException, NoPositionException, Exception {
		//Se cargan los datos en los satelites, por si satelite ha cambiado su posicion desde la ultima transmision
		inicializasatelites("Fully");
		//se reciben los datos de distancia y mensaje y se asigan a los satelites en operacion
		for (SateliteDto y: satelites.getsatellites()) {
			AsignadatosSatelite(y);
		}
		
		//crea un objeto con la respuesta que espera el cliente de la app
		RespuestaDto resp = new RespuestaDto();
		
		resp.setPosition(GetLocation()); 
		resp.setMessage(GetMessage());
		
		//Se limpian los datos en los satelites, por si llega una peticion individual
		inicializasatelites("Fully");
		return resp;		
	}
	
	//Metodo que obtiene la ubicacion de la nave desconocida y su mensaje, basado en los mensajes individuales de cada satelite
	public RespuestaDto descifradatosxget() throws BadLocationException, BadMessageException, NoPositionException, Exception {
		//Se hace un limpiado parcial, por si se esperan datos de otro satelite en caso de que este incompleta la peticion
		inicializasatelites("Partial");
		//crea un objeto con la respuesta que espera el cliente de la app
		RespuestaDto resp = new RespuestaDto();
		
		resp.setPosition(GetLocation()); 
		resp.setMessage(GetMessage());
		
		//una vez hace el reponse, limpia los datos de los satelites para esperar la siguiente comunicacion
		
		return resp;
	}
	
	//Metodo que bajo el concepto matematico de triangulos, obtiene la localizacion (aproximada) de la nave
	private PositionDto GetLocation() throws BadLocationException, NoPositionException, Exception {
		List<PositionDto> posiciontemp = new ArrayList<>();
		
		double xtemp, ytemp;
		
		//Bloque for para validar que haya 2 satelites diferentes para comparar informacion con respecto a la nave enemiga
		for (int x=0;x<sat.getsatellites().size(); x ++) {
			
			int SatenX = x, SatenXsig = x + 1;
			
			try {
				//Si el satelite del analisis no recibio informacion se salta
				if (sat.getsatellites().get(SatenX).getDistance() == 0) {
					continue;
				//Si el satelite siguiente no tiene datos, se busca el siguiente y el siguiente hasta que encuentre uno con datos
				} else if(sat.getsatellites().get(SatenXsig).getDistance() == 0){
					boolean encontrodata = false;
					for (int y = SatenXsig + 1; y<sat.getsatellites().size(); y++) {
						if (sat.getsatellites().get(y).getDistance() == 0) {
							continue;
						}else {
							encontrodata = true;
							SatenXsig = y;
							break;
						}
					}
					//Si no encuentra  un satelite de los restantes con data, asigna como satelite siguiente al primero que encuentre con datos entre los satelites que ya se recorrieron
					if (!encontrodata) {
						for (int y = 0; y<SatenXsig - 1; y++) {
							if (sat.getsatellites().get(y).getDistance() == 0) {
								continue;
							}else {
								encontrodata = true;
								SatenXsig = y;
								break;
							}
						}
					}
					
					//si en definitiva no encontro, significa que no hay datos suficientes para el analisis
					if (!encontrodata) {
						throw new NoPositionException();
					}
				}
			//Solo se ejecutara este bloque con el ultimo satelite, dado que la posicion + 1 generara error, por ende, se busca nuevamente desde el primero hacia adelante para hacer la comparacion
			} catch (IndexOutOfBoundsException e) {
				for (int y = 0; y<SatenXsig - 1; y++) {
					if (sat.getsatellites().get(y).getDistance() == 0) {
						continue;
					}else {
						SatenXsig = y;
						break;
					}
				}
			//Genera excepcion por analisis de los satelites
			} catch (Exception e) {
				throw new Exception();
			}
			
			//Valida que el satelite de referencia no sea el mismo satelite siguiente, ni el anterior (porque ya se abria comparado)
			if (SatenX == SatenXsig || SatenXsig == SatenX -1) {
				continue;
			}
			
			
			//Crea vector con los 2 primeros satelites y luego con los satelites 2 y 3 y asi sucesivamente
			VectorDto vector = new VectorDto(sat.getsatellites().get(SatenX).getPosition(), sat.getsatellites().get(SatenXsig).getPosition());
			double part1Ec = ( Math.pow(sat.getsatellites().get(SatenXsig).getDistance(),2) - Math.pow(sat.getsatellites().get(SatenX).getDistance(),2) - Math.pow(vector.getDistance(),2));
			double part2Ec = ( -2 * sat.getsatellites().get(SatenX).getDistance() * vector.getDistance());
			
			//Obtiene el angulo (en PI) que se forma del satelite en cuestion, contra el siguiente satelite en lista y el objeto, para sacar una posicion relativa
			double anguloNaveDesSat = Math.acos(part1Ec/part2Ec);
			
			//Se obtiene la posicion relativa del objeto con respecto al satelite en cuestion, basado en el angulo positivo con respecto a X que se forma con el satelite en cuestion y el siguiente en lista
			xtemp = ((vector.getposicion().getx() < 0) ? -1 : 1) * sat.getsatellites().get(SatenX).getDistance() * Math.cos(anguloNaveDesSat + vector.anguloPosX());
			ytemp = ((vector.getposicion().gety() < 0) ? -1 : 1) * sat.getsatellites().get(SatenX).getDistance() * Math.sin(anguloNaveDesSat + vector.anguloPosX());
			
			//se suma la posicion del satelite en cuestion a la posicion relativa obtenida, para determinar la posicion exacta de la nave en el plano
			posiciontemp.add(new PositionDto((xtemp + sat.getsatellites().get(SatenX).getPosition().getx()),(ytemp + sat.getsatellites().get(SatenX).getPosition().gety())));
			
			//Se ejecuta el mis codigo pero con el angulo negativo, para conocer la posicion contraria de la nave con respecto al plano, ya que podria estar en ambas posiciones
			xtemp = ((vector.getposicion().getx() < 0) ? -1 : 1) * -(sat.getsatellites().get(SatenX).getDistance() * Math.cos(anguloNaveDesSat + vector.anguloNegX()));
			ytemp = ((vector.getposicion().getx() < 0) ? -1 : 1) * (sat.getsatellites().get(SatenX).getDistance() * Math.sin(anguloNaveDesSat + vector.anguloNegX()));
			
			//se suma la posicion del satelite en cuestion a la posicion relativa obtenida, para determinar la posicion exacta de la nave en el plano
			posiciontemp.add(new PositionDto((xtemp + sat.getsatellites().get(SatenX).getPosition().getx()),(ytemp + sat.getsatellites().get(SatenX).getPosition().gety())));
		}
		
		PositionDto posiciondef = new PositionDto();
		List<PositionDto> posiciondeftemp = new ArrayList<>();
		
		//Si no hay al menos 2 pares de posiciones, no sera posible hallar la ubicacion de la nave
		if (posiciontemp.size() >= 4) {
			
			//Se crea una variable temporal para asignar el tamaño total del arreglo y no afectar el recorrido
			int totdatos = posiciontemp.size();
			//Se agrega el primer par de posiciones para hacer la validacion ciclica (el 1 con el 2, el 2 con el 3, el 3 con el 1)
			posiciontemp.add(posiciontemp.get(0));
			posiciontemp.add(posiciontemp.get(1));
			
			//Dado que se saca un par de posiciones por cada par de satelites obtenidos, se analiza ese par contra el siguiente par, para determinar cual es mas parecido entre si
			for (int x = 0; x <totdatos; x += 2) {
				double xt1, xt2, yt1, yt2, desviacion1, desviacion2;
				
				VectorDto vector1 = new VectorDto(posiciontemp.get(x), posiciontemp.get(x+2));
				VectorDto vectorcomp = new VectorDto(posiciontemp.get(x), posiciontemp.get(x+3));
				
				if (vector1.getDistance() < vectorcomp.getDistance()) {
					xt1 = (posiciontemp.get(x).getx() + posiciontemp.get(x+2).getx())/2;
					yt1 = (posiciontemp.get(x).gety() + posiciontemp.get(x+2).gety())/2;
					desviacion1 = vector1.getDistance();
				} else {
					xt1 = (posiciontemp.get(x).getx() + posiciontemp.get(x+3).getx())/2;
					yt1 = (posiciontemp.get(x).gety() + posiciontemp.get(x+3).gety())/2;
					desviacion1 = vectorcomp.getDistance();
				}
				
				vector1 = new VectorDto(posiciontemp.get(x+1), posiciontemp.get(x+2));
				vectorcomp = new VectorDto(posiciontemp.get(x+1), posiciontemp.get(x+3));
				if (vector1.getDistance() < vectorcomp.getDistance()) {
					xt2 = (posiciontemp.get(x+1).getx() + posiciontemp.get(x+2).getx())/2;
					yt2 = (posiciontemp.get(x+1).gety() + posiciontemp.get(x+2).gety())/2;
					desviacion2 = vector1.getDistance();
				} else {
					xt2 = (posiciontemp.get(x+1).getx() + posiciontemp.get(x+3).getx())/2;
					yt2 = (posiciontemp.get(x+1).gety() + posiciontemp.get(x+3).gety())/2;
					desviacion2 = vectorcomp.getDistance();
				}
				//Al terminar la validacion de pares, se obtendran 2 resultados posibles, del cual saldra 1 que correspondera al que tenga el vector mas pequeño (que indicara que los 2 puntos formados por los 2 triangulos son mas cercanos entre si)
				if (desviacion1 < desviacion2) {
					posiciondeftemp.add(new PositionDto(Math.round(xt1*100.0)/100.0, Math.round(yt1*100.0)/100.0));
				} else {
					posiciondeftemp.add(new PositionDto(Math.round(xt2*100.0)/100.0, Math.round(yt2*100.0)/100.0));
				}
			}
		//No hay suficientes datos para analizar
		} else {
			throw new NoPositionException();
		}
		
		//Se hace un promedio de resultados y se entrega el resultado final
		for (PositionDto x: posiciondeftemp) {
			posiciondef.setx(posiciondef.getx() + x.getx());
			posiciondef.sety(posiciondef.gety() + x.gety());
		}
		
		posiciondef.setx(posiciondef.getx() / posiciondeftemp.size());
		posiciondef.sety(posiciondef.gety() / posiciondeftemp.size());
		
		//si en definitiva el analisis no resulta por datos errados, se arroja excepcion.
		if (Double.isNaN(posiciondef.getx()) || Double.isNaN(posiciondef.gety()) ||  posiciondef.getx() == 0 ||  posiciondef.gety() == 0 ) {
			throw new BadLocationException();
		}
		
		return posiciondef;
	}
	
	private String GetMessage() throws BadMessageException, Exception {
		
		String message = "";
		int tamaño = 0;
		//En teoria, todos los mensajes recibidos por los satelites deberian tener el mismo tamaño, en caso de que no, no se podria decodificar correctamente, por ende se arroja error
		for (SateliteDto x: sat.getsatellites()) {
			//Se omiten los satelites que no tienen datos
			if (x.getDistance() != 0) {
				if (tamaño == 0) {
					tamaño = x.getMessage().length;
				} else if(tamaño != x.getMessage().length) {
					throw new BadMessageException();
				}
			}
		}
		
		//Si todos los satelites indican que el mensaje es del mismo tamaño, se analiza
		for (int x = 0; x < tamaño; x++) {
			//Busca en el primer satelite el mensaje, si no lo encuentra, busca en el siguiente, si no en el siguiente, etc, si no encuentra, se omite
			for (SateliteDto y: sat.getsatellites()) {	
				//Se omiten los satelites que no tienen datos
				if (y.getDistance() != 0) {
					if (!y.getMessage()[x].equals("")) {
						message += y.getMessage()[x] + " ";
						break;
					}
				}
			}
		}
		
		return message.trim();
	}
	
	//Metodo que asigna los datos de un satelite recibidos, al valor general de los satelites
	public void AsignadatosSatelite(SateliteDto satelite) {
		for (int x = 0; x < sat.getsatellites().size(); x ++) {
			if (sat.getsatellites().get(x).getName().equals(satelite.getName())) {
				sat.getsatellites().get(x).setDistance(satelite.getDistance());
				sat.getsatellites().get(x).setMessage(satelite.getMessage());
				break;
			}
		}
	}
}
