package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Model.Produto;
import com.example.estoque411.Model.Produtovendido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.example.estoque411.Config.IdUsuario.getIdUsuario;

public class ListagemDeProdutosParaCompras extends AppCompatActivity implements AdapterProdutosAVenda.OnProdutoAVendaListener {

	private AdapterProdutosAVenda adapterProdutosAVenda;
	public static Double Vtotal=0.0;
	private List<Produto> produtos=new ArrayList<>();
	public static List<Produtovendido> produtosSelecionados = new ArrayList<>();
	private Produto produtoSelecionado;
	private EditText campo;
private int x;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listagem_de_produtos_para_compras);
		DatabaseReference produtoRef;
		DatabaseReference fireBaseRef;
		fireBaseRef= ConfigFireBaseReference.getFirebaseDatabase();

		produtoRef=fireBaseRef.child("armazenamento").child(getIdUsuario()).child("produtos");
		produtoRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				produtos.clear();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					produtos.add(ds.getValue(Produto.class));
				}
				adapterProdutosAVenda.notifyDataSetChanged();
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}});

	CarregarLista();
	}

	private void CarregarLista(){

		RecyclerView recyclerViewP = findViewById(R.id.recyclerViewProdutosVenda);
		//Referencia do Fire Base

		//Config Recycler View
		recyclerViewP.setLayoutManager(new LinearLayoutManager(this));
		recyclerViewP.setHasFixedSize(true);
		adapterProdutosAVenda= new AdapterProdutosAVenda(produtos,this,this);
		recyclerViewP.setAdapter(adapterProdutosAVenda);
		//Select no banco de dados



	}




	@Override
	public void onProdutoAVendaClick(final int position) {
		produtoSelecionado = produtos.get(position);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(""+produtoSelecionado.getNomeProduto());
		builder.setMessage("Entre com a quantidade:");
		campo= new EditText(this);
		campo.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(campo);
		final int qtd=Integer.parseInt(String.valueOf(produtoSelecionado.getQuantidadeUnidade()));
		x=produtoSelecionado.getQuantidadeUnidade();
		//AlertDialog dialog = builder.create();

		builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (campo.getText().toString().equals("")||campo.getText().toString().equals("0")){
					Toast.makeText(ListagemDeProdutosParaCompras.this, "Preencha a quantidade", Toast.LENGTH_SHORT).show();
				}else if(Integer.parseInt(campo.getText().toString()) >qtd){
					Toast.makeText(ListagemDeProdutosParaCompras.this, "Você possui apenas "+qtd+" produtos", Toast.LENGTH_SHORT).show();
				}else{

					if ( Integer.parseInt(campo.getText().toString())==qtd){
						AdicionarProduto();
						produtos.remove(position);

						CarregarLista();
					}else{
						produtos.get(position).setQuantidadeUnidade((qtd - Integer.parseInt(campo.getText().toString())));
						AdicionarProduto();
						CarregarLista();
					}

					}


			}
		});
		builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}});
		builder.create();
		builder.show();

	}


	public void AdicionarProduto(){

		Produtovendido produtovendido=new Produtovendido();
		produtovendido.setIdProduto(produtoSelecionado.getIdProduto());
		produtovendido.setNomeProduto(produtoSelecionado.getNomeProduto());

		produtovendido.setQuantidadeVendida(Integer.parseInt(campo.getText().toString()));
		produtovendido.setQuantidadeUnidade(x);
		produtovendido.setPrecoVendaUnidade(produtoSelecionado.getPrecoVendaUnidade());
		produtovendido.setDataeHora(produtoSelecionado.getDataeHora());
		produtosSelecionados.add(produtovendido);
		Vtotal+=produtovendido.getPrecoVendaUnidade()*produtovendido.getQuantidadeVendida();

	}

public void chamarCadastrarVenda(View view){
	if (produtosSelecionados.size()==0){
		Toast.makeText(ListagemDeProdutosParaCompras.this, "Você não possui itens selecionados", Toast.LENGTH_SHORT).show();
	}else {
		startActivity(new Intent(getApplicationContext(), CadastrarVendaActivity.class));
		this.finish();
	}

}



}


