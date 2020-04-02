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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AtividadesActivity extends AppCompatActivity {

    TextView textViewData, textViewHora;
    Button buttonSalvar;
    EditText editTextAtividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        textViewData = findViewById(R.id.textViewData);
        textViewHora = findViewById(R.id.textViewHora);
        editTextAtividades = findViewById(R.id.editTextAtividades);
        buttonSalvar = findViewById(R.id.buttonSalvarAtividade);

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

            //Criar o arquivo com o nome atividadeArquivoLocal
            String arquivo = "atividadeArquivoLocal";
            SharedPreferences sharedPreferences = getSharedPreferences(arquivo, MODE_PRIVATE);
            //Habilitando editor
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //salvando os dados em um arquivo cocatenado
            String dados = textViewData.getText().toString() + "|";
            dados += textViewHora.getText().toString() + "|";
            dados += editTextAtividades.getText().toString();
            //salvar dados
            editor.putString(arquivo, dados);
            editor.commit();

            SharedPreferences preferences = getSharedPreferences(arquivo, MODE_PRIVATE);
            String d = preferences.getString(arquivo, null);
            if (!d.isEmpty()){
                Toast.makeText(this, d, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
