package com.example.estoque411;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.estoque411.Config.configFireBase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Para deixar em tela cheia
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    }
//############################################################################# Metodos usados no login e verificação #################################################################################################

    //Ao abrir o app ele verifica se tem alguem logado
    @Override
    protected void onStart() {
        super.onStart();
        verificarLogin();
    }

    //Verifica se tem login
    public void verificarLogin(){
        autenticacao= configFireBase.getFireBaseAutenticacao();
        //autenticacao.signOut();
        if (autenticacao.getCurrentUser() != null){
            abrirTela();
        }}

    //Metodos de usabilidade
    public void abrirTela(){
        startActivity(new Intent( getApplicationContext(), PrincipalActivity.class));
    }

    public void btCadastrar(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void btLogar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }
}
