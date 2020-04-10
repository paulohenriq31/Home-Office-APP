package com.paulo.homeoffice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisualizaAtividadesActivity extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference reference;
    RecyclerView recyclerView;
    List<ClasseAtividade> listaAtividade = new ArrayList<>();
    ClasseAdapter adapter;
    ClasseAtividade atividade;
    final String []dadosAtividade = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_atividades);

        recyclerView = findViewById(R.id.recyclerViewConsulta);

        reference = FirebaseDatabase.getInstance().getReference();
        atividade = new ClasseAtividade();
        adapter = new ClasseAdapter(listaAtividade, getApplicationContext(), atividade);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        verAtividades();


    }



    public void verAtividades(){

        String idUsuarioLogado = auth.getUid();

        DatabaseReference dados = reference.getDatabase().getReference("Atividade Finalizada");

        Query query =  dados.orderByChild("idFuncionario").equalTo(idUsuarioLogado);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0){

                    recyclerView.setVisibility(View.VISIBLE);
                }else{

                    recyclerView.setVisibility(View.GONE);
                }

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ClasseAtividade atividade = ds.getValue(ClasseAtividade.class);
                    listaAtividade.add(atividade);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.menuSair:
                auth.signOut();
                startActivity(new Intent(VisualizaAtividadesActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
