package com.example.estoque411;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.estoque411.Model.Venda;
import java.util.List;

public class AdapterVendaListagem extends RecyclerView.Adapter<AdapterVendaListagem.MyViewholderVenda>{
	private List<Venda> vendasRealizadas;
	private Context context;
	private OnVendaListerner mOnVendaListerner;

	public AdapterVendaListagem(List<Venda> vendasRealizadas, Context context,OnVendaListerner onVendaListerner){

		this.vendasRealizadas=vendasRealizadas;
		this.context=context;
		this.mOnVendaListerner=onVendaListerner;
	}

	@NonNull
	@Override
	public MyViewholderVenda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View itemVendaLista=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venda_listagem,parent,false);
		return new MyViewholderVenda(itemVendaLista,mOnVendaListerner);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewholderVenda holder, int position) {

		Venda venda=vendasRealizadas.get(position);
		holder.nomeCliente.setText(String.format(" %s", venda.getNomeCliente()));
		holder.diaMesAno.setText(String.format(" %s", venda.getDataCompra()));
		holder.valorTotal.setText(String.format("Valor: %s", venda.getValorTotal()));

	}

	@Override
	public int getItemCount() {

		return vendasRealizadas.size();
	}


	public class MyViewholderVenda extends RecyclerView.ViewHolder implements View.OnClickListener {

		TextView nomeCliente;
		TextView valorTotal;
		TextView diaMesAno;
		OnVendaListerner onVendaClickListerner;

		public MyViewholderVenda(final View itemView,OnVendaListerner onVendaClickListerner) {
			super(itemView);
			nomeCliente=itemView.findViewById(R.id.nomeCliente);
			valorTotal=itemView.findViewById(R.id.valorTotal);
			diaMesAno=itemView.findViewById(R.id.diaMesAno);
			this.onVendaClickListerner=onVendaClickListerner;
			itemView.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {

			onVendaClickListerner.onVendaClick(getAdapterPosition());
		}
	}

	public interface OnVendaListerner{
		void onVendaClick(int position);

	}


}
