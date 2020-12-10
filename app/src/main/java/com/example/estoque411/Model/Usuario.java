package com.example.estoque411.Model;

import com.example.estoque411.Config.ConfigFireBaseReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

	// Atributos de Usuario
	private String idUsuario;
	private String nomeUsuario;
	private String email;
	private String senha;


	//Conexão com o banco
	DatabaseReference fireBase= ConfigFireBaseReference.getFirebaseDatabase();

	//Metodo Construtor vazio
	public Usuario() {
	}
	//metodos de acesso

	@Exclude
	public String getIdUsuario() { return idUsuario; }

	public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsario(String nomeEstoque) {
		this.nomeUsuario = nomeEstoque;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	//função de salvar usuario
	public void salvarUsuario(){

		fireBase.child("usuarios")
				.child(this.idUsuario)
				.setValue(this);


}




}
