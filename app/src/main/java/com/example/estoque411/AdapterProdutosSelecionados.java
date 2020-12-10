package com.example.estoque411;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estoque411.Model.Produtovendido;
import java.util.List;

public class AdapterProdutosSelecionados extends RecyclerView.Adapter<AdapterProdutosSelecionados.MyViewHolder> {

	private List<Produtovendido> produtosSelecionados;
	private Context context;
	private OnProdutoSelecionadoListerner monProdutoSelecionadoListerner;

	public AdapterProdutosSelecionados(List<Produtovendido> pS, Context context,OnProdutoSelecionadoListerner onProdutoSelecionadoListerner) {

		this.produtosSelecionados = pS;
		this.context = context;
		this.monProdutoSelecionadoListerner=onProdutoSelecionadoListerner;
	}


	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View itemListado= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_selecionados,parent,false);
		return new MyViewHolder(itemListado,monProdutoSelecionadoListerner);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

		Produtovendido produtoV=produtosSelecionados.get(position);
		holder.nome.setText(String.valueOf(produtoV.getNomeProduto()));
		holder.quantidade.setText("Quantidade: "+produtoV.getQuantidadeVendida());
		holder.valorTotal.setText(String.valueOf(produtoV.getPrecoVendaUnidade()));
	}

	@Override
	public int getItemCount() {

		return produtosSelecionados.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView nome, quantidade,valorTotal;
		OnProdutoSelecionadoListerner onProdutoSelecionadoListerner;

		public MyViewHolder(@NonNull View itemView, OnProdutoSelecionadoListerner onProdutoSelecionadoListerner){

			super(itemView);
			nome=itemView.findViewById(R.id.textViewNomeProduto);
			quantidade=itemView.findViewById(R.id.textViewQuantidade);
			valorTotal=itemView.findViewById(R.id.textViewvalorMultiplicado);
			this.onProdutoSelecionadoListerner=onProdutoSelecionadoListerner;
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

			onProdutoSelecionadoListerner.onProdutoSelecionadoClick(getAdapterPosition());
		}
	}
	public interface OnProdutoSelecionadoListerner{

		void onProdutoSelecionadoClick(int position);
	}

}
