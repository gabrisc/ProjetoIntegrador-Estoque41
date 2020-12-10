package com.example.estoque411;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.example.estoque411.Config.configFireBase;
import com.google.firebase.auth.FirebaseAuth;


public class PrincipalActivity extends AppCompatActivity {

	//Objeto do tipo Firebase
	private FirebaseAuth autenticacao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_principal);


	}

	//Funçoes de usabilidade
	public void deslogar(View view){
		autenticacao= configFireBase.getFireBaseAutenticacao();
		autenticacao.signOut();
		startActivity(new Intent( getApplicationContext(), MainActivity.class));
		this.finish();
	}

	public void chamarCadastrarProduto(View view){
		startActivity(new Intent( getApplicationContext(), CadastrarProdActivity.class));

	}

	public void chamarListagemVendas(View view){
		startActivity(new Intent( getApplicationContext(), ListagemDeVendasRealizadas.class));
	}

	public void chamarVerResultados(View view){
		startActivity(new Intent( getApplicationContext(), Resultados.class));
	}

	public void chamarVerListaDeProdutos(View view){
		startActivity(new Intent( getApplicationContext(), ListagemDeProdutosActivity.class));
	}

}
