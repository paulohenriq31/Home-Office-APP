package com.paulo.homeoffice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText editTextEmail, editTextSenha;
    Button buttonLogin, buttonNovo;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        if (!auth.getCurrentUser().toString().isEmpty()){
            Intent atividades = new Intent(MainActivity.this, AtividadesActivity.class);
            startActivity(atividades);
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonNovo = findViewById(R.id.buttonNovo);


    }

    public void login(View view){
        if(validadeCamposLogin()) {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email, senha;
            email = editTextEmail.getText().toString();
            senha = editTextSenha.getText().toString();

            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent atividades = new Intent(MainActivity.this, AtividadesActivity.class);
                        startActivity(atividades);
                    } else {
                        Toast.makeText(MainActivity.this, "Falha no Login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void abreCadastro(View view){
        Intent cadastro = new Intent(MainActivity.this, CadastroUsuariosActivity.class);
        startActivity(cadastro);
    }

    boolean validadeCamposLogin(){
        String email, senha;

        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.isEmpty() || !email.contains("@") || email.length()<6){
            Toast.makeText(this, "Preencha o campo E-mail corretamente!", Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return false;

        }else if(senha.isEmpty() || senha.length()<6){
            Toast.makeText(this, "Preencha o campo Senha corretamente!", Toast.LENGTH_SHORT).show();
            editTextSenha.requestFocus();
            return false;

        }else{
            return true;
        }
    }
}
