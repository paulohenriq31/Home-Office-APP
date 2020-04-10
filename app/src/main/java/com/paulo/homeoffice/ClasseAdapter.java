package com.paulo.homeoffice;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClasseAdapter  extends RecyclerView.Adapter<ClasseAdapter.MyViewHolder> {

    private List<ClasseAtividade> listaAtividade;
    private Context context;
    private ClasseAtividade atividade;

    public ClasseAdapter(List<ClasseAtividade> listaAtividade, Context context, ClasseAtividade atividade)
    {
        this.listaAtividade = listaAtividade;
        this.context = context;
        this.atividade = atividade;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.consulta_atividade, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        ClasseAtividade atividade = listaAtividade.get(position);

        try
        {
            holder.emailFuncionario.setText("Email: " + atividade.getEmail());
            holder.dataInicial.setText("Data Inicial: " + atividade.getDataInicio());
            holder.dataFinal.setText("Data Final: " + atividade.getDataFim());
            holder.horaInicial.setText("Hora Inicio: " + atividade.getHoraInicio());
            holder.horaFinal.setText("Hora Final: " + atividade.getDataFim());
            holder.atividade.setText("Atividade: " + atividade.getAtividade());

        }
        catch (Exception ex)
        {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount()
    {
        return listaAtividade.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dataInicial, dataFinal, horaInicial, horaFinal, emailFuncionario, atividade;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            dataInicial = itemView.findViewById(R.id.textViewDataInicioV);
            dataFinal = itemView.findViewById(R.id.textViewDataFinalV);
            horaInicial = itemView.findViewById(R.id.textViewHoraInicioV);
            horaFinal = itemView.findViewById(R.id.textViewHoraFinalV);
            emailFuncionario = itemView.findViewById(R.id.textViewEmailUsuarioV);
            atividade = itemView.findViewById(R.id.textViewAtividadeV);

        }
    }
}
