package com.example.reto2.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto2.R;
import com.example.reto2.datos.BaseDatos.MotorBaseDatosSQLite;

import java.util.ArrayList;



public class Adaptador extends BaseAdapter {

    ArrayList<Entidad> lista_items;
    Context context;

    // CONEXION A LA BASE DE DATOS: SQLite
    MotorBaseDatosSQLite conectar;



    public Adaptador(ArrayList<Entidad> lista_items, Context context) {
        this.lista_items = lista_items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lista_items.size(); // Devuelve cuntos elelmentos hay en la lista
    }

    @Override
    public Object getItem(int posicion) {
        return lista_items.get(posicion); // devuelve la posicion del item
    }

    @Override
    public long getItemId(int posicion) {
        return 0; // No lo vamos a trabajar
    }

    /*
    Este es el metodo mas importante, aqui vamos a asignar el item y lo elementos y valores a
    cada item.
     */
    @Override
    public View getView(int posicion, View v, ViewGroup viewGroup) {

        Entidad datosItem = (Entidad) getItem(posicion);

        v = LayoutInflater.from(context).inflate(R.layout.item, null);
        //-------------------------------------------------------------------

        ImageView imagen = (ImageView)v.findViewById(R.id.imagen1_item);
        TextView titulo = (TextView)v.findViewById(R.id.titulo_item);
        TextView descripcion = (TextView)v.findViewById(R.id.descripcion_item);

        TextView descripcion2 = (TextView)v.findViewById(R.id.descripcion2_item);

        //---------------------------------------------------------------------------------
        conectar = new MotorBaseDatosSQLite(context,"TiendaProductos", null, 1);
        SQLiteDatabase db_escribir = conectar.getWritableDatabase();
        conectar.onUpgrade(db_escribir, 1, 2);
        //---------------------------------------------------------------------------------

        Button boton1 = (Button) v.findViewById(R.id.boton1_item);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "No implementado", Toast.LENGTH_LONG).show();
                //db_escribir.execSQL("INSERT INTO favoritos VALUES (1, '"+datosItem.getTitulo()+"', '"+datosItem.getTitulo()+"')");
            }
        });

        CheckBox favoritos = (CheckBox) v.findViewById(R.id.favoritos_item);
        CheckBox clasificacion = (CheckBox) v.findViewById(R.id.clasificacion_item);

        /*
        Pongo los datos de cada item desde la clase Entidad dentro de cada elemento xml
         */
        imagen.setImageResource(datosItem.getImagen());
        titulo.setText(datosItem.getTitulo());
        descripcion.setText(datosItem.getDescripcion());

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "item:" + titulo.getText(), Toast.LENGTH_LONG ).show();
            }
        });

        //-------------------------------------------------------------------
        return v;
    }

}
