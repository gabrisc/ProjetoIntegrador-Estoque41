package com.example.estoque411.Model;

import androidx.annotation.NonNull;
import com.example.estoque411.Config.ConfigFireBaseReference;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import static com.example.estoque411.Config.IdUsuario.getIdUsuario;

public class Produto {

	private String idProduto;
	private String nomeProduto;
	private double precoVendaUnidade;
	private double precoCompraUnidade;
	private int quantidadeUnidade;
	private String dataeHora;
	//construtor abstrato

	//############################################################################# Metodos Construtor ##########################################################################
	public Produto() {
	}

	public void remover(){

		DatabaseReference firebaseRef2= ConfigFireBaseReference.getFirebaseDatabase();
		DatabaseReference produtoRef=firebaseRef2.child("armazenamento").child(getIdUsuario()).child("produtos").child(this.getIdProduto());
		produtoRef.removeValue();
	}
	public void updateProduto(){
		DatabaseReference fireBaseRef=ConfigFireBaseReference.getFirebaseDatabase();
		FirebaseAuth autenticacion = configFireBase.getFireBaseAutenticacao();
		String idUsuario = Base64Custom.codificarBase64(autenticacion.getCurrentUser().getEmail());

		DatabaseReference produtoRef=fireBaseRef.child("armazenamento");

		Produto produto2=this;

		fireBaseRef.child("armazenamento").child(idUsuario).child("produtos").child(produto2.getIdProduto()).setValue(produto2).addOnCompleteListener(new OnCompleteListener<Void>() {

			@Override
			public void onComplete(@NonNull Task<Void> task) {


			}
		});
	}




	//############################################################################# Metodos de acesso ##########################################################################


	public String getDataeHora() {
		return dataeHora;
	}

	public void setDataeHora(String dataeHora) {
		this.dataeHora = dataeHora;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public double getPrecoVendaUnidade() {
		return precoVendaUnidade;
	}

	public void setPrecoVendaUnidade(double precoVendaUnidade) {
		this.precoVendaUnidade = precoVendaUnidade;
	}

	public double getPrecoCompraUnidade() {
		return precoCompraUnidade;
	}

	public void setPrecoCompraUnidade(double precoCompraUnidade) {
		this.precoCompraUnidade = precoCompraUnidade;
	}
	public int getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(int quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;
	}



}
