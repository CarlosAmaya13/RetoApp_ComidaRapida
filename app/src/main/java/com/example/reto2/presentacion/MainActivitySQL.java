package com.example.reto2.presentacion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.reto2.MainActivity;
import com.example.reto2.R;
import com.example.reto2.casos_uso.CasoUsoFavorito;
import com.example.reto2.datos.DBHelper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivitySQL extends AppCompatActivity {

    private Button btnInsertar, btnConsultar, btnEliminar, btnChoose, btnListaPersonajes, btnExit, btnActualizar;
    private EditText edtName, edtId;
    private ImageView imgSelected;
    private DBHelper dbHelper;
    private CasoUsoFavorito casoUsoFavorito;

    private final static String CHANNEL_ID = "NOTICACION";
    private final static int  NOTIFICATION_ID = 0;
    private final static int REQUEST_CODE_GALLERY = 999;

    private LinearLayout linearLayout;


    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray =stream.toByteArray();
        return byteArray;
    }

    public void limpiarCampos(){
        edtName.setText("");
        edtId.setText("");
        imgSelected.setImageResource(R.mipmap.ic_gofood);
    }

    public void showById(Cursor cursor){
        if(cursor.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Elemento no encontrado",Toast.LENGTH_SHORT).show();
        }else{
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()){
                edtName.setText(cursor.getString(1));
                byte[] image = cursor.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,image.length);
                imgSelected.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsql);

        btnInsertar = (Button) findViewById(R.id.btnInsertar);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnExit = (Button) findViewById(R.id.btnExit);

        btnListaPersonajes = (Button) findViewById(R.id.btnListaPersonajes);

        edtName = (EditText) findViewById(R.id.edtName);
        edtId = (EditText) findViewById(R.id.edtId);

        imgSelected = (ImageView) findViewById(R.id.imgSelected);

        linearLayout = (LinearLayout) findViewById(R.id.Mainlayout);

        dbHelper = new DBHelper(getApplicationContext());
        casoUsoFavorito = new CasoUsoFavorito();

        btnListaPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivitySQL.this, ListaFavoritos.class);
                startActivity(intent);
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainActivitySQL.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Toast.makeText(getApplicationContext(), edtName.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    dbHelper.insertFavorito(
                            edtName.getText().toString().trim(),
                            imageViewToByte(imgSelected)
                    );
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Entidad Creada", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Toast.makeText(getApplicationContext(), edtName.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    dbHelper.updateFavoritos(
                            edtId.getText().toString().trim(),
                            edtName.getText().toString().trim(),
                            imageViewToByte(imgSelected)
                    );
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Entidad Actualizada", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                if(id.equals("")){
                    Cursor cursor = dbHelper.getFavoritos();
                    String result = casoUsoFavorito.cursorToString(cursor);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }else{
                    Cursor cursor = dbHelper.getFavoritoID(id);
                    showById(cursor);
                }

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                if(id.equals("")){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }else{
                    dbHelper.deleteFavorito(id);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();

                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(getApplicationContext(), "Sin Permisos", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgSelected.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}