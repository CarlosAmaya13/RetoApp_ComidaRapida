package com.example.reto2.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reto2.R;

import java.util.ArrayList;

public class FavoritoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Favorito> listaFavoritos;

    LayoutInflater inflater;

    public FavoritoAdapter(Context context, ArrayList<Favorito> listaFavoritos) {
        this.context = context;
        this.listaFavoritos = listaFavoritos;
    }

    @Override
    public int getCount() {
        return listaFavoritos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView textView = convertView.findViewById(R.id.grid_name);

        Favorito favorito = listaFavoritos.get(position);
        byte[] image = favorito.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);

        imageView.setImageBitmap(bitmap);
        textView.setText(favorito.getName());


        return convertView;
    }
}
