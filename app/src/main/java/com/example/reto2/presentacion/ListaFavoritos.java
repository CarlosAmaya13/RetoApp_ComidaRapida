package com.example.reto2.presentacion;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2.R;
import com.example.reto2.datos.DBHelper;
import com.example.reto2.modelo.Favorito;
import com.example.reto2.modelo.FavoritoAdapter;

import java.util.ArrayList;

public class ListaFavoritos extends AppCompatActivity {
    private DBHelper dbHelper;
    private ArrayList<Favorito> listaPersonajes;
    private GridView gridView;


    public ArrayList<Favorito> llenarLista(Cursor cursor){
        ArrayList<Favorito> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                Favorito favorito = new Favorito(
                        cursor.getString(1),
                        cursor.getBlob(2)
                );
                list.add(favorito);
            }
            return list;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_favoritos);
        Toast.makeText(getApplicationContext(), "Se ha conectado con la base de datos local", Toast.LENGTH_SHORT).show();
        dbHelper = new DBHelper(getApplicationContext());
        gridView = (GridView) findViewById(R.id.gridView);

        Cursor cursor = dbHelper.getFavoritos();
        listaPersonajes = llenarLista(cursor);
        FavoritoAdapter favoritoAdapter = new FavoritoAdapter(getApplicationContext(),listaPersonajes);
        gridView.setAdapter(favoritoAdapter);

    }



}
