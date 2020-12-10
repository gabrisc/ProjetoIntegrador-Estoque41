package com.example.estoque411.Config;

import com.google.firebase.auth.FirebaseAuth;

public class configFireBase {

	//criando objeto do Firebase
	private static FirebaseAuth autenticacao;

	//Metodo de retorno
	public static FirebaseAuth getFireBaseAutenticacao(){

		if (autenticacao==null){
			autenticacao=FirebaseAuth.getInstance();
		}
		return autenticacao;
	}

}
