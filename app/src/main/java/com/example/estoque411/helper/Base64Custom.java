package com.example.estoque411.helper;

import android.util.Base64;

public class Base64Custom {

	//codificar o email
	public static String codificarBase64(String texto){
	return Base64.encodeToString(texto.getBytes(),Base64.DEFAULT ).replaceAll("(\\n|\\r)","");
	}

	//decodificar o email
	public static String decodificarBase64(String textCodificado){
	return new String(Base64.encodeToString(textCodificado.getBytes(),Base64.DEFAULT));
	}

}
