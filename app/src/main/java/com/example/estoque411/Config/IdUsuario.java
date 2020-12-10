package com.example.estoque411.Config;

import com.example.estoque411.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;

public class IdUsuario {


public static String getIdUsuario(){

	FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();
	String	idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());

	return idUsuario;
}
}
