package com.example.reto2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.reto2.presentacion.ListaFavoritos;
import com.example.reto2.presentacion.MainActivitySQL;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reto2.databinding.ActivityMainBinding;

/**
 * Clase MainActivity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Atributos
     */
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private LinearLayout linearLayoutMenu;
    private final static int NOTIFICATION_ID = 0;
    private final static String CHANNEL_ID = "NOTIFICACION";

    /**
     * Metodo onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo onClick del boton de la pantalla principal
             * @param view
             */
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Funciones administrativas SQLite", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivitySQL.class);
                startActivity(intent);
                boton_notificacion("GooFood!", "Bienvenido");
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu, R.id.nav_gallery, R.id.mainActivitySQL,
                R.id.fragment_Sucursales, R.id.listaFavoritos, R.id.fragment_Productos)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * Metodo onCreateOptionsMenu
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Metodo onSupportNavigateUp
     * @return NavigationUI
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Metodo boton_proximamente, utilizado para marcar que elementos estaran en proximas actualizaciones
     * @param v
     */
    public void boton_proximamente(View v){
        Toast.makeText(getApplicationContext(),"proximamente", Toast.LENGTH_SHORT).show();

    }

    /**
     * Metodo boton_notificacion, utilizado para crear notificaciones con un titulo y su contenido
     * @param titulo
     * @param contenido
     */
    public void boton_notificacion(String titulo,String contenido){
        createNotificationChannel();
        createNotification(titulo, contenido);
    }

    /**
     * Metodo para la creacion del canal de la notificacion
     */
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Metodo para la creacion de la notificacion
     * @param titulo
     * @param contenido
     */
    @SuppressLint("ResourceAsColor")
    public void createNotification(String titulo, String contenido){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_fastfood_24);
        builder.setContentTitle(titulo);
        builder.setContentText(contenido);
        builder.setDefaults(NotificationCompat.PRIORITY_DEFAULT);
        builder.setColor(R.color.ic_gofood_background);
        builder.setLights(Color.MAGENTA,500,500);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * Metodo boton_dialog, utilizado para la creacion de un Dialog
     * @param v
     */
    public void boton_dialog(View v){
        createDialog("GoFood!", "Contenido del dialogo");
    }

    /**
     * Metodo para la creacion de un Dialog
     * @param titulo
     * @param contenido
     */
    public void  createDialog(String titulo, String contenido){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(titulo);
        builder.setMessage(contenido);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Si presionado", Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "No presionado", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

}