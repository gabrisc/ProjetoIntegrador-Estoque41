package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.Model.Produto;
import com.example.estoque411.helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class EditarProdutoActivity extends AppCompatActivity{

	private EditText nome,valorVenda,valorCompra,quantidade,data;
	private Produto produtoSelecionado=new Produto();

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_produto);
		carregarValores();
	}

	private void carregarValores(){

//########################################################################################   Recebendo o produto selecionado  ####################################################################################################

		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		produtoSelecionado.setIdProduto(bundle.getString("id"));
		produtoSelecionado.setNomeProduto(bundle.getString("nome"));
		produtoSelecionado.setQuantidadeUnidade(Integer.parseInt(bundle.getString("quantidade")));
		produtoSelecionado.setPrecoVendaUnidade(Double.parseDouble(bundle.getString("precovenda")));
		produtoSelecionado.setPrecoCompraUnidade(Double.parseDouble(bundle.getString("precocompra")));
		produtoSelecionado.setDataeHora(String.valueOf(bundle.getString("data")));

//####################################################################################   Capturando os campos   #############################################################################################

		nome=findViewById(R.id.editNomeEditar);
		valorVenda=findViewById(R.id.editValorVendaEditar);
		valorCompra=findViewById(R.id.editValorComprarEditar);
		quantidade=findViewById(R.id.editQuantidadeEditar);
		data=findViewById(R.id.editData);
//####################################################################################   Mostrando o produto selecionado   #############################################################################################

		nome.setText(produtoSelecionado.getNomeProduto());
		valorCompra.setText(String.valueOf(produtoSelecionado.getPrecoCompraUnidade()));
		valorVenda.setText(String.valueOf(produtoSelecionado.getPrecoVendaUnidade()));
		quantidade.setText(String.valueOf(produtoSelecionado.getQuantidadeUnidade()));
		data.setText(String.valueOf(produtoSelecionado.getDataeHora()));
	}

	public void validarCampos(View view) {

		if (nome.getText().equals("")) {
			Toast.makeText(EditarProdutoActivity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
		} else if (valorCompra.getText().equals("")){
			Toast.makeText(EditarProdutoActivity.this, "Preencha o valor da compra", Toast.LENGTH_SHORT).show();
		}else if (valorVenda.getText().equals("")){
			Toast.makeText(EditarProdutoActivity.this, "Preencha o valor de venda", Toast.LENGTH_SHORT).show();
		}else if (quantidade.getText().equals("")){
			Toast.makeText(EditarProdutoActivity.this, "Preencha a quantidade", Toast.LENGTH_SHORT).show();
		}else if (data.getText().equals("")){
			Toast.makeText(EditarProdutoActivity.this, "Preencha a data", Toast.LENGTH_SHORT).show();
		}else{
			produtoSelecionado.setNomeProduto(nome.getText().toString());
			produtoSelecionado.setPrecoCompraUnidade(Double.parseDouble(valorCompra.getText().toString()));
			produtoSelecionado.setQuantidadeUnidade(Integer.parseInt(quantidade.getText().toString()));
			produtoSelecionado.setDataeHora((data.getText().toString()));
			produtoSelecionado.setPrecoVendaUnidade(Double.parseDouble(valorVenda.getText().toString()));

			DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
			FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();
			String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());

			fireBaseRef.child("armazenamento").child(idUsuario).child("produtos").child(produtoSelecionado.getIdProduto()).setValue(produtoSelecionado).addOnCompleteListener(new OnCompleteListener<Void>() {

				@Override
				public void onComplete(@NonNull Task<Void> task) {}

			});

			startActivity(new Intent(getApplicationContext(),ListagemDeProdutosActivity.class));
			this.finish();
		}
	}
}



