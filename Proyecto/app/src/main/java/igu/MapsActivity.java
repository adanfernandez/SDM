package igu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.pelayo.fragments.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import logica.Playa;
import logica.Procesamiento;


/**
 * Created by Adán on 04/01/2018.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        double lat =  43.135828;
        double lon = -5.887064;
        CameraPosition cameraPosition = new
                CameraPosition.Builder()
                .target(new LatLng(lat, lon)) // Centro del mapa
                .zoom((float) 7.5) // Establece nivel de zoom
                .bearing(360) // Orientación cámara al sur
                .tilt(30) // Inclinación cámara 30 grados
                .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition));
        añadirPlayas();
    }

    /**
     * Se añaden las playas. Si
     */
    private void añadirPlayas()
    {
        if(Procesamiento.nombrePlaya.equals("")) {
            for (Playa playa : Procesamiento.getPlayas())
            {
                double lat = playa.getPosicion()[0];
                double lon = playa.getPosicion()[1];
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title(playa.getNombre())
                        .snippet(playa.getConcejo() + ", longitud: " + playa.getLongitud())
                );
            }
        }
        else {
            for (Playa playa : Procesamiento.getPlayas()) {
                if (playa.getNombre().equals(Procesamiento.nombrePlaya)) {
                    double lat = playa.getPosicion()[0];
                    double lon = playa.getPosicion()[1];
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(playa.getNombre())
                            .snippet(playa.getConcejo() + ", longitud: " + playa.getLongitud())
                    );
                }
            }
            Procesamiento.nombrePlaya = "";
        }
    }



}
