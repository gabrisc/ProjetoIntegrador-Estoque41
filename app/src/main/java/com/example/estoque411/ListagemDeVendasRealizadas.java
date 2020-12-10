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
import android.widget.Toast;
import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Config.IdUsuario;
import com.example.estoque411.Model.Produtovendido;
import com.example.estoque411.Model.Venda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListagemDeVendasRealizadas extends AppCompatActivity implements AdapterVendaListagem.OnVendaListerner{

	private AdapterVendaListagem adapterVendaListagem;
	private List<Venda> vendasRealizadas=new ArrayList<>();
	private DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
	private RecyclerView recyclerViewVendas;
	private Venda VendaSelecionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listagem_de_vendas_realizadas);

		recuperarVenda();
	}



	private void recuperarVenda() {
			DatabaseReference fireBaseRef;
			fireBaseRef= ConfigFireBaseReference.getFirebaseDatabase();
			recyclerViewVendas=findViewById(R.id.recyclerViewVendas);
			recyclerViewVendas.setLayoutManager(new LinearLayoutManager(this));
			recyclerViewVendas.setHasFixedSize(true);
			adapterVendaListagem= new AdapterVendaListagem(vendasRealizadas,this,this);
			recyclerViewVendas.setAdapter(adapterVendaListagem);
			DatabaseReference vendaRef=fireBaseRef.child("armazenamento").child(IdUsuario.getIdUsuario()).child("vendas");
			vendaRef.addValueEventListener(new ValueEventListener() {

			@Override

			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				vendasRealizadas.clear();
				for (DataSnapshot ds: dataSnapshot.getChildren()){
					vendasRealizadas.add(ds.getValue(Venda.class));
				}
				adapterVendaListagem.notifyDataSetChanged();
			}
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			}});
	}

	public void CadastrarVenda(View view){
		startActivity(new Intent(getApplicationContext(), ListagemDeProdutosParaCompras.class));
		this.finish();
	}

	@Override
	public void onVendaClick(int position) {
		VendaSelecionada=vendasRealizadas.get(position);


		if (VendaSelecionada.getDesconto()==null){
			VendaSelecionada.setDesconto(0.0);
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Cliente: " + VendaSelecionada.getNomeCliente()+"\n"+
				"Valor: " +VendaSelecionada.getValorTotal()+"\n"+
				"Data: " +VendaSelecionada.getDataCompra()+"\n"+
				"Desconto: " +VendaSelecionada.getDesconto()+"\n"+
				"Forma de pagamento: "+VendaSelecionada.getFormaDePagamento());

		builder.setNegativeButton("Apagar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Venda venda=new Venda();
				venda.setIdVenda(VendaSelecionada.getIdVenda());
				venda.removerVenda();

				Toast.makeText(ListagemDeVendasRealizadas.this, "Deletado", Toast.LENGTH_SHORT).show();
			}
		});

		builder.setPositiveButton("Produtos vendidos", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				produtosCompradosDialog();
			}
		});

		builder.setNeutralButton("Voltar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {


			}
		});
		builder.create();
		builder.show();

	}
	public void produtosCompradosDialog(){
		List<Produtovendido>lista=VendaSelecionada.getLista();

		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		ArrayList<String> nome = null;
		ArrayList<Integer> quantidade = null;
		

		builder2.setTitle("Produtos comprados");
		Iterator<Produtovendido> iterator=lista.iterator();
		while(iterator.hasNext()){
			Produtovendido pv= iterator.next();

		builder2.setMessage(""+pv.getNomeProduto()+"\n"+"Quantidade: "+pv.getQuantidadeVendida()+"\n\n");
			builder2.setNeutralButton("próximo", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder2.create();
			builder2.show();


		}




	
}



}
