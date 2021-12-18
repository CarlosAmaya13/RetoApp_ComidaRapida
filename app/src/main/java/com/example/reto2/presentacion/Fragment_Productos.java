package com.example.reto2.presentacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto2.R;
import com.example.reto2.datos.Adaptador;
import com.example.reto2.datos.Entidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class Fragment_Productos extends Fragment {


    int [] imagen = {R.drawable.chorizo, R.drawable.burger, R.drawable.pizza, R.drawable.tacos,
            R.drawable.costillas, R.drawable.hotdog, R.drawable.lasagna, R.drawable.tacos2,
            R.drawable.cupon, R.drawable.logo, R.drawable.menu};

    String TAG = "Fragment_Productos";

    View v;

    ListView listaProductos;
    Adaptador adaptador;

    TextView prueba;

    // CONEXION A LA BASE DE DATOS: instancia APEX de SQL en Oracle Cloud


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment__productos, container, false);
        //-----------------------------------------------------------------------------
        listaProductos = (ListView) v.findViewById(R.id.lista_productos);
        adaptador = new Adaptador(getTablaProductos(), getContext());

        listaProductos.setAdapter(adaptador);

        prueba = (TextView) v.findViewById(R.id.prueba);
        Toast.makeText(getContext(), "Iniciando conexion con la base de datos de Oracle", Toast.LENGTH_SHORT).show();

        //-----------------------------------------------------------------------------
        return v;
    }

    private ArrayList<Entidad> getTablaProductos(){
        ArrayList<Entidad> listaProductos = new ArrayList<>();

        /* ================================================================================================== */
        //String url = "https://g1379f5424c7d37-dbgofood.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/tiendas/tiendas";
        String url = "https://g960d18944e6f07-db202109231955.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/productos/productos";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //***********************************************************
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String titulo = jsonObject.getString("nombre");
                        String descripcion = "Deliciosa comida";

                        listaProductos.add(new Entidad(imagen[i], titulo, descripcion));
                        prueba.setText("Conexion con Oracle realizada con exito" + '\n');

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //***********************************************************
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
        /* ================================================================================================== */

        return listaProductos;
    }

}