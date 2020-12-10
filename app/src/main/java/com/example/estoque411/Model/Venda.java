package com.example.estoque411.Model;

import androidx.annotation.NonNull;

import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.ListagemDeProdutosParaCompras;
import com.example.estoque411.helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


import java.util.Iterator;
import java.util.List;

import static com.example.estoque411.Config.IdUsuario.getIdUsuario;

public class Venda {
	private List<Produtovendido> lista;
	private String idVenda,nomeCliente,mesAno,dataCompra,formaDePagamento;
	private Double valorTotal,desconto;


	//Metodo construtor vazio

	public Venda() {
	}
	//Metodo Crud

	public void subtrairVendas(){

			Iterator<Produtovendido> iterator=ListagemDeProdutosParaCompras.produtosSelecionados.iterator();
			while(iterator.hasNext()){
			Produtovendido produtovendido = iterator.next();

			Produto produto=new Produto();
			produto.setNomeProduto(produtovendido.getNomeProduto());
			produto.setIdProduto(produtovendido.getIdProduto());
			produto.setQuantidadeUnidade(produtovendido.getQuantidadeUnidade()-produtovendido.getQuantidadeVendida());
			produto.setPrecoVendaUnidade(produtovendido.getPrecoVendaUnidade());
			produto.setDataeHora(produtovendido.getDataeHora());
			produto.setPrecoCompraUnidade(produtovendido.getPrecoCompraUnidade());

				DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
				FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();
				String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());

				fireBaseRef.child("armazenamento").child(idUsuario).child("produtos").child(produto.getIdProduto()).setValue(produto).addOnCompleteListener(new OnCompleteListener<Void>() {

					@Override
					public void onComplete(@NonNull Task<Void> task) {


					}
				});

	}
		ListagemDeProdutosParaCompras.produtosSelecionados.clear();
	}

	public void removerVenda(){

		DatabaseReference firebaseRef2= ConfigFireBaseReference.getFirebaseDatabase();
		DatabaseReference produtoRef=firebaseRef2.child("armazenamento").child(getIdUsuario()).child("vendas").child(this.getIdVenda());
		produtoRef.removeValue();
	}


	//Metodos de acesso


	public String getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(String formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public String getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(String idVenda) {
		this.idVenda = idVenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public String getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public List<Produtovendido> getLista() {
		return lista;
	}

	public void setLista(List<Produtovendido> lista) {
		this.lista = lista;
	}
}

