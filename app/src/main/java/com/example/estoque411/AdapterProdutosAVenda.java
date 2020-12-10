package com.example.estoque411;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estoque411.Model.Produto;
import java.util.List;

public class AdapterProdutosAVenda extends RecyclerView.Adapter<AdapterProdutosAVenda.MyViewHolder>{
	private List<Produto> produtosAVenda;
	private Context context;
	private OnProdutoAVendaListener onProdutoAVendaListenerm;

	public AdapterProdutosAVenda(List<Produto> p, Context context, OnProdutoAVendaListener l) {

		this.produtosAVenda = p;
		this.context = context;
		this.onProdutoAVendaListenerm=l;
	}


	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View itemListado=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_a_venda,parent,false);
		return new MyViewHolder(itemListado,onProdutoAVendaListenerm);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
		Produto produto = produtosAVenda.get(i);
		holder.nameProdutoItem.setText(String.format("Nome: %s", produto.getNomeProduto()));
		holder.quantidadeProdutoItem.setText(String.format("Quantidade: %d", produto.getQuantidadeUnidade()));
		holder.valorProdutoItem.setText(String.format("Valor: %s", produto.getPrecoVendaUnidade()));

	}

	@Override
	public int getItemCount() {
		return produtosAVenda.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView nameProdutoItem,quantidadeProdutoItem,valorProdutoItem;
		OnProdutoAVendaListener onProdutoAVendaListener;

			public MyViewHolder(final View itemView,OnProdutoAVendaListener onProdutoAVendaListener) {

				super(itemView);
				nameProdutoItem=itemView.findViewById(R.id.nameProdutoItem);
				quantidadeProdutoItem=itemView.findViewById(R.id.quantidadeProdutoItem);
				valorProdutoItem=itemView.findViewById(R.id.valorProdutoItem);
				this.onProdutoAVendaListener=onProdutoAVendaListener;
				itemView.setOnClickListener(this);
			}


			@Override
			public void onClick(View v) {

				onProdutoAVendaListener.onProdutoAVendaClick(getAdapterPosition());
			}
	}

	public interface OnProdutoAVendaListener{
		void onProdutoAVendaClick(int position);
	}

}
