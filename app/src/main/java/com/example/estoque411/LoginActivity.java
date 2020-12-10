package com.example.estoque411;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque411.Config.configFireBase;
import com.example.estoque411.Model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private String excecao;
    private EditText email,senha;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Localizando as views para o login
        email=findViewById(R.id.editEmail);
        senha=findViewById(R.id.editSenha);
    }

    public void logar(View view){

        String textoEmail,textoSenha;

        //passando par ama variavel de tipo primitivo
        textoEmail = email.getText().toString();
        textoSenha = senha.getText().toString();

        //tratamento de excecao
        if (textoEmail.isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"O E-mail está vazio",Toast. LENGTH_LONG);
            toast. show();
        }else if (textoSenha.isEmpty()) {
            Toast toast=Toast. makeText(getApplicationContext(),"A senha está vazia",Toast. LENGTH_LONG);
            toast. show();
        }else {

        //criando usuario para validar
        usuario = new Usuario();
        usuario.setEmail(textoEmail);
        usuario.setSenha(textoSenha);
        validar();
        }
    }


public void validar(){
    FirebaseAuth autenticacao;
    autenticacao= configFireBase.getFireBaseAutenticacao();
    autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()){
                abrirTela();
                //tratamento de exceção
                }else{
                try {
                    throw task.getException();
                }catch (FirebaseAuthInvalidCredentialsException e){
                    excecao="E-mail e senha estão errados";
                }catch (FirebaseAuthInvalidUserException e){
                    excecao="E-mail não cadastrado";
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

    //metodo chamar tela principal
    public void abrirTela(){
        startActivity(new Intent( getApplicationContext(), PrincipalActivity.class));
        finish();
    }


}
