package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Model.Produto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.estoque411.Config.IdUsuario.getIdUsuario;

public class ListagemDeProdutosActivity extends AppCompatActivity implements AdapterProduto.OnProdutoListerner {
	private AdapterProduto adapterProduto;
	private List<Produto> produtos = new ArrayList<>();
	private DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
	private RecyclerView recyclerProdutos;
	public Produto produtoSelecionado;
	private DatabaseReference produtoRef;
	private Intent intent;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listagem_de_produtos);
		CarregarLista();

	}

	public void CarregarLista(){

		recyclerProdutos = findViewById(R.id.recyclerProdutos);
		//Referencia do Fire Base
		DatabaseReference fireBaseRef;
		fireBaseRef= ConfigFireBaseReference.getFirebaseDatabase();
		//Config Recycler View
		recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
		recyclerProdutos.setHasFixedSize(true);
		adapterProduto= new AdapterProduto(produtos,this,this);
		recyclerProdutos.setAdapter(adapterProduto);
		//Select no banco de dados

		produtoRef=fireBaseRef.child("armazenamento").child(getIdUsuario()).child("produtos");
		produtoRef.addValueEventListener(new ValueEventListener() {
		@Override
		public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
		produtos.clear();
		for (DataSnapshot ds: dataSnapshot.getChildren()){
			produtos.add(ds.getValue(Produto.class));
		}
			adapterProduto.notifyDataSetChanged();
		}
		@Override
		public void onCancelled(@NonNull DatabaseError databaseError) {
		}});
	}

	@Override
	public void onProdutoClick(int position) {

		produtoSelecionado = produtos.get(position);
		intent= new Intent(getApplicationContext(), EditarProdutoActivity.class);
		intent.putExtra("id",produtoSelecionado.getIdProduto());
		intent.putExtra("nome",produtoSelecionado.getNomeProduto());
		intent.putExtra("precovenda",String.valueOf(produtoSelecionado.getPrecoVendaUnidade()));
		intent.putExtra("precocompra",String.valueOf(produtoSelecionado.getPrecoCompraUnidade()));
		intent.putExtra("quantidade",String.valueOf(produtoSelecionado.getQuantidadeUnidade()));
		intent.putExtra("data",String.valueOf(produtoSelecionado.getDataeHora()));

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(

				"Nome: "+produtoSelecionado.getNomeProduto()+"\n"+
				"Quantidade estocada: "+produtoSelecionado.getQuantidadeUnidade()+"\n"+
				"Preço de compra: "+produtoSelecionado.getPrecoCompraUnidade()+" R$"+"\n"+
				"Valor de Venda: "+produtoSelecionado.getPrecoVendaUnidade()+" R$"+"\n"+
				"Editado em: "+produtoSelecionado.getDataeHora()

				).setPositiveButton(
				"Editar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						startActivity(intent);
						Encerrar();
					}
				}).setNegativeButton(
				"Apagar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Produto produto256=new Produto();
						produto256.setIdProduto(produtoSelecionado.getIdProduto());
						produto256.remover();

					}});

		builder.create();
		builder.show();

	}
	public void Encerrar(){
		this.finish();
	}


	public void chamarCadastrarProduto(View view){
		startActivity(new Intent( getApplicationContext(), CadastrarProdActivity.class));
		this.finish();
	}

	public void voltar (View view){
		startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
		this.finish();
	}



}
