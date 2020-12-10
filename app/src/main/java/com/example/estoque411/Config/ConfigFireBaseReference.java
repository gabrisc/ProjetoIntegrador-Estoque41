package com.example.estoque411.Config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFireBaseReference {

	// objeto de referencia do FB
	private static DatabaseReference firebase;

	//retorna a instancia do FirebaseDatabase
	public static DatabaseReference getFirebaseDatabase(){
		if ( firebase == null ){
			firebase = FirebaseDatabase.getInstance().getReference();
		}
		return firebase;
	}

}

