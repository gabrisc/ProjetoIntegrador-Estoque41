package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.Model.Produto;
import com.example.estoque411.helper.Base64Custom;
import com.example.estoque411.helper.DataCustom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarProdActivity extends AppCompatActivity {

	private DatabaseReference mDatabase;
	private EditText data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cadastrar_prod);
		mDatabase = FirebaseDatabase.getInstance().getReference();
		data=findViewById(R.id.editData);
		DataCustom dataCustom=new DataCustom();
		data.setText(dataCustom.dataAtual());

	}

	//############################################################################# Metodo Para Validar Campos #################################################################################################
	public void validandoInfo( View view ){

		EditText nomeProduto = findViewById(R.id.nomeProdutoText);
		EditText valorVendaProduto = findViewById(R.id.valorVendaProdutoText);
		EditText valorCompraProduto = findViewById(R.id.valorCompraProdutoText);
		EditText quantidadeProduto = findViewById(R.id.quantidadeProdutoText);


		if (nomeProduto.getText().toString().equals("")){
			Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
			toast. show();
		} else if(valorCompraProduto.getText().toString().equals("")){
			Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra do produto",Toast. LENGTH_SHORT);
			toast. show();

		}else if (valorVendaProduto.getText().toString().equals("")){
			Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda do produto",Toast. LENGTH_SHORT);
			toast. show();
		}else if (quantidadeProduto.getText().toString().equals("")){
			Toast toast=Toast. makeText(getApplicationContext(),"Entre com a quantidade de produtos",Toast. LENGTH_SHORT);
			toast. show();
		}else if (data.getText().toString().equals("")){
			Toast toast=Toast. makeText(getApplicationContext(),"Entre com a data",Toast. LENGTH_SHORT);
			toast. show();
		}else{

				salvar(
						nomeProduto.getText().toString(),
						Double.parseDouble(valorVendaProduto.getText().toString()),
						Double.parseDouble(valorCompraProduto.getText().toString()),
						Integer.parseInt(quantidadeProduto.getText().toString()),
						data.getText().toString()
				);
		}
	}
	//############################################################################# Metodo Para Salvar #####################################################################################################

	private void salvar(String nome, double  precoVenda,double precoCompra,int quantidade,String dataAtual) {

		FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();

		String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());
		DatabaseReference produtoRef=mDatabase.child("armazenamento");

		Produto produto2= new Produto();
		produto2.setIdProduto(produtoRef.push().getKey());
		produto2.setNomeProduto(nome);
		produto2.setPrecoVendaUnidade(precoVenda);
		produto2.setPrecoCompraUnidade(precoCompra);
		produto2.setQuantidadeUnidade(quantidade);
		produto2.setDataeHora(dataAtual);

		mDatabase.child("armazenamento").child(idUsuario).child("produtos").child(produto2.getIdProduto()).setValue(produto2).addOnCompleteListener(new OnCompleteListener<Void>() {

					@Override
					public void onComplete(@NonNull Task<Void> task) {

						Toast.makeText(CadastrarProdActivity.this, "Produto Adicionado", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(),ListagemDeProdutosActivity.class));

					}
				});

		this.finish();
	}

	//############################################################################# Metodo Para Usabilidade #####################################################################################################

		//Botão cancelar
			public void cancelarCadastro (View view){
				startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
				this.finish();
		}

		//Botão voltar
		public void voltar (View view){
			startActivity(new Intent(getApplicationContext(), ListagemDeProdutosActivity.class));
			this.finish();
		}

	}