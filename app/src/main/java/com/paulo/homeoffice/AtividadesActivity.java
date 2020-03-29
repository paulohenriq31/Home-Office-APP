package com.paulo.homeoffice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AtividadesActivity extends AppCompatActivity {

    TextView textViewData, textViewHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);

        textViewData = findViewById(R.id.textViewData);
        textViewHora = findViewById(R.id.textViewHora);

        textViewHora.setText(hora());
        textViewData.setText(data());

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
}
