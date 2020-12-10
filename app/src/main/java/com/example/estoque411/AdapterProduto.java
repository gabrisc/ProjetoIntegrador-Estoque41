package com.example.estoque411;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estoque411.Model.Produto;
import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder> {
		private List<Produto> produtos;
		private Context context;
		private OnProdutoListerner mOnProdutoListerner;


		public AdapterProduto(List<Produto> produtos, Context context,OnProdutoListerner onProdutoListerner) {

			this.produtos = produtos;
			this.context = context;
			this.mOnProdutoListerner=onProdutoListerner;
		}

		@NonNull
		@Override
		public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

			View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
			return new MyViewHolder(itemLista,mOnProdutoListerner);
		}

		@Override
		public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

			Produto produto = produtos.get(i);
			holder.nome.setText(produto.getNomeProduto());
		}

		@Override
		public int getItemCount() {

			return produtos.size();
		}

		public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

			TextView nome;
			OnProdutoListerner onProdutoListerner;

				public MyViewHolder(final View itemView,OnProdutoListerner onProdutoListerner) {
					super(itemView);
					nome = itemView.findViewById(R.id.nameItem);


					this.onProdutoListerner=onProdutoListerner;

					itemView.setOnClickListener(this);
				}


				@Override
				public void onClick(View v) {

					onProdutoListerner.onProdutoClick(getAdapterPosition());
				}
		}


		public interface OnProdutoListerner{

			void onProdutoClick(int position);
		}


}

