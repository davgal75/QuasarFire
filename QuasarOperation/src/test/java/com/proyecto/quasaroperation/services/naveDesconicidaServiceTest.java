package com.proyecto.quasaroperation.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.quasaroperation.dtos.SateliteDto;
import com.proyecto.quasaroperation.dtos.SatelitesDto;
import com.proyecto.quasaroperation.exception.BadLocationException;
import com.proyecto.quasaroperation.exception.BadMessageException;
import com.proyecto.quasaroperation.exception.NoPositionException;

public class naveDesconicidaServiceTest {
	
	@Autowired
	naveDesconicidaService desconicidaService;
	
	//Test que deberia arrojar la posicion OK - Metodo topsecret
	@Test
	void descifraDatosOkTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		sato.setName("sato");
		sato.setDistance(1044.03);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		SatelitesDto satelites = new SatelitesDto();
		satelites.getsatellites().add(kenobi);
		satelites.getsatellites().add(Skywalker);
		satelites.getsatellites().add(sato);
		
		Assertions.assertEquals(-500, Math.round(desconicidaService.descifradatos(satelites).getPosition().getx()));
		Assertions.assertEquals(400, Math.round(desconicidaService.descifradatos(satelites).getPosition().gety()));
		Assertions.assertEquals("Este es un mensaje secreto", desconicidaService.descifradatos(satelites).getMessage());
	}
	
	//Test que deberia arrojar error por el mensaje faltante - Metodo topsecret
	@Test
	void descifraDatosErrorMessageTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "", "mensaje",""});
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		sato.setName("sato");
		sato.setDistance(1044.03);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		SatelitesDto satelites = new SatelitesDto();
		satelites.getsatellites().add(kenobi);
		satelites.getsatellites().add(Skywalker);
		satelites.getsatellites().add(sato);
		try {
			desconicidaService.descifradatos(satelites);
			Assertions.assertTrue(false);
		}catch (BadMessageException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			Assertions.assertTrue(false);
		}
	}
	
	//Test que deberia arrojar error por falta de posicion - Metodo topsecret
	@Test
	void descifraDatosNoPositionTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		sato.setName("sato");
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		SatelitesDto satelites = new SatelitesDto();
		satelites.getsatellites().add(kenobi);
		satelites.getsatellites().add(Skywalker);
		satelites.getsatellites().add(sato);
		try {
			desconicidaService.descifradatos(satelites);
			Assertions.assertTrue(false);
		}catch (NoPositionException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			Assertions.assertTrue(false);
		}
	}
	
	//Test que deberia arrojar error, por posiciones que no son correctas - Metodo topsecret
	@Test
	void descifraDatosBadPositionTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(100);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(100);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		sato.setName("sato");
		sato.setDistance(100);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		SatelitesDto satelites = new SatelitesDto();
		satelites.getsatellites().add(kenobi);
		satelites.getsatellites().add(Skywalker);
		satelites.getsatellites().add(sato);
		try {
			System.out.println(desconicidaService.descifradatos(satelites).getMessage());
			System.out.println(desconicidaService.descifradatos(satelites).getPosition().getx());
			System.out.println(desconicidaService.descifradatos(satelites).getPosition().gety());
			Assertions.assertTrue(false);
		}catch (BadLocationException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.assertTrue(false);
		}
	}
	
	//Test que deberia arrojar la posicion OK - Metodo topsecretsplit
	@Test
	void descifraDatosByGetOkTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		desconicidaService.AsignadatosSatelite(kenobi);
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		desconicidaService.AsignadatosSatelite(Skywalker);
		
		sato.setName("sato");
		sato.setDistance(1044.03);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		desconicidaService.AsignadatosSatelite(sato);
		
		Assertions.assertEquals(-500, Math.round(desconicidaService.descifradatosxget().getPosition().getx()));
		Assertions.assertEquals(400, Math.round(desconicidaService.descifradatosxget().getPosition().gety()));
		Assertions.assertEquals("Este es un mensaje secreto", desconicidaService.descifradatosxget().getMessage());
	}
	
	//Test que deberia arrojar error por el mensaje faltante - Metodo topsecretsplit
	@Test
	void descifraDatosByGetErrorMessageTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "", "mensaje",""});
		
		desconicidaService.AsignadatosSatelite(kenobi);
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		desconicidaService.AsignadatosSatelite(Skywalker);
		
		sato.setName("sato");
		sato.setDistance(1044.03);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		desconicidaService.AsignadatosSatelite(sato);
		
		try {
			desconicidaService.descifradatosxget();
			Assertions.assertTrue(false);
		}catch (BadMessageException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			Assertions.assertTrue(false);
		}
	}
	
	//Test que deberia arrojar error por falta de posicion - Metodo topsecretsplit
	@Test
	void descifraDatosByGetNoPositionTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(600);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		desconicidaService.AsignadatosSatelite(kenobi);
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(781.02);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		desconicidaService.AsignadatosSatelite(Skywalker);
		
		sato.setName("sato");
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		desconicidaService.AsignadatosSatelite(sato);
		
		try {
			desconicidaService.descifradatosxget();
			Assertions.assertTrue(false);
		}catch (NoPositionException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			Assertions.assertTrue(false);
		}
	}
	
	//Test que deberia arrojar error, por posiciones que no son correctas - Metodo topsecretsplit
	@Test
	void descifraDatosByGetBadPositionTest() throws Exception {
		
		desconicidaService = new naveDesconicidaService();
		
		SateliteDto kenobi = new SateliteDto();
		SateliteDto Skywalker = new SateliteDto();
		SateliteDto sato = new SateliteDto();
		
		kenobi.setName("kenobi");
		kenobi.setDistance(100);
		kenobi.setMessage(new String[] {"Este", "es", "", "mensaje",""});
		
		desconicidaService.AsignadatosSatelite(kenobi);
		
		Skywalker.setName("skywalker");
		Skywalker.setDistance(200);
		Skywalker.setMessage(new String[] {"", "", "un", "",""});
		
		desconicidaService.AsignadatosSatelite(Skywalker);
		
		sato.setName("sato");
		sato.setDistance(100);
		sato.setMessage(new String[] {"", "", "", "","secreto"});
		
		desconicidaService.AsignadatosSatelite(sato);
		
		try {
			desconicidaService.descifradatosxget();
			Assertions.assertTrue(false);
		}catch (BadLocationException e) {			
			Assertions.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.assertTrue(false);
		}
	}
}
