package com.paulo.homeoffice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AtividadesActivity extends AppCompatActivity {

    TextView textViewData, textViewHora;
    Button buttonSalvar, buttonFinalizarAtividade;
    EditText editTextAtividades;
    ClasseAtividade atividadeClasse = new ClasseAtividade();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        textViewData = findViewById(R.id.textViewData);
        textViewHora = findViewById(R.id.textViewHora);
        editTextAtividades = findViewById(R.id.editTextAtividades);
        buttonSalvar = findViewById(R.id.buttonSalvarAtividade);
        buttonFinalizarAtividade = findViewById(R.id.buttonFinalizarAtividade);

        SharedPreferences preferencesAtividades = getSharedPreferences("Atividades", MODE_PRIVATE);
        String Atividades = preferencesAtividades.getString("Atividades", null);
        editTextAtividades.setText(Atividades);

        textViewHora.setText(hora());
        textViewData.setText(data());

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Construtor de alerta
                AlertDialog.Builder builder = new AlertDialog.Builder(AtividadesActivity.this);
                //habilitar button cancelar
                builder.setCancelable(true);
                builder.setTitle("Gravador de Atividades");
                builder.setMessage("Deseja salvar as atividades?");
                //button confirmar
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        salvarAtividadeLocal();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonFinalizarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AtividadesActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Gravador de atividade no banco de dados da empresa");
                builder.setMessage("Deseja salvar as atividades no banco de dados da empresa");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        salvarAtividadeFirebase();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public String data(){

        String formatacao = "dd/MM/yyyy";

        SimpleDateFormat formataData = new SimpleDateFormat(formatacao);

        formataData.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date hoje = Calendar.getInstance().getTime();

        String hojeFormatado = formataData.format(hoje);

        return  hojeFormatado;
    }

    public String hora(){

        String formatacao = "HH:mm";

        SimpleDateFormat formataHora = new SimpleDateFormat(formatacao);

        formataHora.setTimeZone(TimeZone.getDefault());

        Date hora = Calendar.getInstance().getTime();

        String horaFormatado = formataHora.format(hora);

        return  horaFormatado;
    }

    public void salvarAtividadeLocal(){

        String atividades = editTextAtividades.getText().toString();

        if (atividades.isEmpty() || atividades.length() < 6){

            Toast.makeText(this, "Preencha o campo atividades", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences sharedPreferencesData = getSharedPreferences("dataInicio", MODE_PRIVATE);
            SharedPreferences.Editor editorData = sharedPreferencesData.edit();
            editorData.putString("dataInicio", textViewData.getText().toString());
            editorData.commit();

            SharedPreferences sharedPreferencesHora = getSharedPreferences("horaInicio", MODE_PRIVATE);
            SharedPreferences.Editor editorHora = sharedPreferencesHora.edit();
            editorHora.putString("horaInicio", textViewHora.getText().toString());
            editorHora.commit();

            SharedPreferences sharedPreferencesAtividade = getSharedPreferences("Atividades", MODE_PRIVATE);
            SharedPreferences.Editor editorAtividades = sharedPreferencesAtividade.edit();
            editorAtividades.putString("Atividades", editTextAtividades.getText().toString());
            editorAtividades.commit();

            String msg = "Sua atividade foi salva localmente";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }

    }

    public void salvarAtividadeFirebase(){

        SharedPreferences preferencesAtividades = getSharedPreferences("Atividades", MODE_PRIVATE);
        String Atividades = preferencesAtividades.getString("Atividades", null);

        SharedPreferences preferencesDataInicial = getSharedPreferences("dataInicio", MODE_PRIVATE);
        String dataInicial = preferencesDataInicial.getString("dataInicio", null);

        SharedPreferences preferencesHoraInicial = getSharedPreferences("horaInicio", MODE_PRIVATE);
        String horaInicial = preferencesHoraInicial.getString("horaInicio", null);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String idUsuarioLogado = auth.getUid();
        String emailUsuarioLogado = auth.getCurrentUser().getEmail();

        String data = data();
        String hora = hora();


        atividadeClasse.setDataInicio(dataInicial);
        atividadeClasse.setHoraInicio(horaInicial);
        atividadeClasse.setAtividade(Atividades);

        atividadeClasse.setDataFim(data);
        atividadeClasse.setHoraFim(hora);
        atividadeClasse.setEmail(emailUsuarioLogado);
        atividadeClasse.setIdFuncionario(idUsuarioLogado);

        atividadeClasse.setAtividadeFinalizada();


        Toast.makeText(this, idUsuarioLogado + " " + emailUsuarioLogado, Toast.LENGTH_SHORT).show();
    }
}
