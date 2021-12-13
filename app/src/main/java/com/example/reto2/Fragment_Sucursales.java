package com.example.reto2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


public class Fragment_Sucursales extends Fragment {


    View v;


    private MapView myOpenMapView;
    private MapController myMapController;

    GeoPoint Bogota, SanAndresito, ColinaCC, Portal80;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment__sucursales, container, false);
        //-----------------------------------------------------------------------------

        myOpenMapView = (MapView) v.findViewById(R.id.openmapview);

        /* ---- necesitamos establecer el valor del agente de usuario en la configuración ------- */
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        /*   punto de geolocalizacion de ejemplo */
        Bogota = new GeoPoint(4.6351, -74.0703);
        SanAndresito = new GeoPoint(4.615462204303617, -74.10092216250449);
        ColinaCC = new GeoPoint(4.732534, -74.067284);
        Portal80 = new GeoPoint(4.709725, -74.111606);

        myOpenMapView.setBuiltInZoomControls(true);

        myMapController = (MapController) myOpenMapView.getController();
        myMapController.setCenter(Bogota);
        myMapController.setZoom(12);

        myOpenMapView.setMultiTouchControls(true);

        /* -------------------------------------------------------------------------------------------------- */
        final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getContext()), myOpenMapView);
        myOpenMapView.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
        myLocationoverlay.enableMyLocation();

        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                myMapController.animateTo(myLocationoverlay.getMyLocation());
            }
        });
        /* -------------------------------------------------------------------------------------------------- */

        /* MARCAS EN EL MAPA */

        ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
        puntos.add(new OverlayItem("Bogota", "Capital de Colombia", Bogota));
        puntos.add(new OverlayItem("SanAndresito", "Sucursal de SanAndresito", SanAndresito));
        puntos.add(new OverlayItem("Colina", "Sucursal de Centro Comercial Colina", ColinaCC));
        puntos.add(new OverlayItem("Portal 80", "Sucursal de Centro Comercial Portal 80", Portal80));

        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                return false;
            }
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }
        };

        ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(getContext(), puntos, tap);
        capa.setFocusItemsOnTap(true);
        myOpenMapView.getOverlays().add(capa);



        //-----------------------------------------------------------------------------
        return v;
    }

}