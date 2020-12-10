package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.Model.Usuario;
import com.example.estoque411.helper.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CadastroActivity extends AppCompatActivity {

	private Usuario usuario;
	private String textoNome,textoEmail,textoSenha;
	private FirebaseAuth autenticacao;
	public String idUsuario;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_cadastro);
	}

	public void validar( View view ){

		//identificando as Views
		EditText nome = findViewById(R.id.editNome);
		EditText email = findViewById(R.id.editEmail);
		EditText senha = findViewById(R.id.editSenha);

		//passando para uma variavel de tipo primitivo
		textoNome = nome.getText().toString();
		textoEmail = email.getText().toString();
		textoSenha = senha.getText().toString();

		//Tratamento de execeção
		if(textoNome.isEmpty()){
					Toast toast=Toast. makeText(getApplicationContext(),"O nome está vazio",Toast. LENGTH_LONG);
					toast. show();
			}else if (textoEmail.isEmpty()){
						Toast toast=Toast. makeText(getApplicationContext(),"O E-mail está vazio",Toast. LENGTH_LONG);
						toast. show();
				}else if (textoSenha.isEmpty()) {
							Toast toast=Toast. makeText(getApplicationContext(),"A senha está vazia",Toast. LENGTH_LONG);
							toast. show();
					}else {
								//caso esteja tudo ok, cria uma instancia de usuario e add os valores
								usuario = new Usuario();
								usuario.setNomeUsario(textoNome);
								usuario.setEmail(textoEmail);
								usuario.setSenha(textoSenha);
								Cadastrar();
						}
	}


	public void Cadastrar(){
		autenticacao= configFireBase.getFireBaseAutenticacao();
		autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {

				//cadastrando usuario
				if(task.isSuccessful()){
				String idUsuario= Base64Custom.codificarBase64(usuario.getEmail());
				usuario.setIdUsuario(idUsuario);
				usuario.salvarUsuario();

				finish();

				//tratamento de execeçao
				}else{
					String excecao="";
					try {
						throw task.getException();
					}catch (FirebaseAuthWeakPasswordException e){
					excecao="Senha fraca";
					}catch (FirebaseAuthInvalidCredentialsException e){
						excecao="Formato errado de e-mail";
					}catch (FirebaseAuthUserCollisionException e){
						excecao="E-mail ja cadastrado";
					}catch (Exception e){
						excecao="Erro ao cadastrar: "+e.getMessage();
						e.printStackTrace();
					}
					Toast toast=Toast. makeText(getApplicationContext(),excecao,Toast. LENGTH_LONG);
					toast. show();
				}
			}
		});

	}

}



