package com.proyecto.quasaroperation.exception;

public class BadMessageException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final StringBuilder mensaje = new StringBuilder();
	
	public BadMessageException(String ... exceptionMessage) {
		super(llenarMensaje(exceptionMessage));
	}
	
	private static String llenarMensaje(String ... exceptoinMessage) {
		mensaje.setLength(0);
		for (String data: exceptoinMessage) {
			mensaje.append(data);
		}
		return mensaje.toString();
	}
}
