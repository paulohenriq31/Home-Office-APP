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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuariosActivity extends AppCompatActivity {

    EditText editTextNome, editTextEmail, editTextSenha;
    Button buttonCadastar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        editTextEmail = findViewById(R.id.editTextCadastrarEmail);
        editTextNome = findViewById(R.id.editTextCadastrarNome);
        editTextSenha = findViewById(R.id.editTextCadastrarSenha);
        buttonCadastar = findViewById(R.id.buttonSalvarCadastro);
    }
    public void cadastrarUsuario(View view){
        if(validadeCamposCadastro()){

            final String email, senha, nome;
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuario");

            email = editTextEmail.getText().toString();
            senha = editTextSenha.getText().toString();
            nome = editTextNome.getText().toString();

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        ClasseUsuario usuario = new ClasseUsuario();
                        usuario.setId(task.getResult().getUser().getUid());
                        usuario.setEmail(email);
                        usuario.setNome(nome);
                        usuario.setSenha(senha);

                        try {
                            reference.child(usuario.getId()).setValue(usuario);
                        }catch (Exception ex){
                            Toast.makeText(CadastroUsuariosActivity.this, "Pane no Sistema", Toast.LENGTH_SHORT).show();
                        }

                        //Toast.makeText(CadastroUsuariosActivity.this, "olha só", Toast.LENGTH_SHORT).show();

                        Intent atividades = new Intent(CadastroUsuariosActivity.this, AtividadesActivity.class);
                        startActivity(atividades);

                    }else{
                        String mensagem = "Falha ao Cadastrar!";
                        Toast.makeText(CadastroUsuariosActivity.this, mensagem, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    boolean validadeCamposCadastro(){
        String nome, email, senha;
        nome = editTextNome.getText().toString();
        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(nome.isEmpty() || nome.length()<3) {
            Toast.makeText(this, "Preencha o campo Nome corretamente!", Toast.LENGTH_SHORT).show();
            editTextNome.requestFocus();
            return false;

        }else if(email.isEmpty() || !email.contains("@") || email.length()<6){
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
