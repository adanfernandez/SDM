package igu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pelayo.fragments.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Random;

import persistencia.FavoritesDataSource;
import logica.Filtrados;
import logica.Geolocalizacion;
import logica.Playa;
import logica.Procesamiento;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class VentanaPrincipal extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private Filtrados filtrado;
    public static String OBJETO_KEY = "OBJETO_KEY";

    // Localización geográfica
    protected Location localizacionActual;
    private GoogleApiClient clienteLocalizacion;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;

    private FavoritesDataSource bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_principal);
        pedirPermisos();
        crearClienteLoc();

        //Cargamos las playas al iniciarse la aplicación.
        final Procesamiento p = new Procesamiento(this);
        filtrado = new Filtrados();

        ListView list = (ListView) findViewById(R.id.listaViewPlayas);
        //ScrollView list = (ScrollView) findViewById(R.id.scroolLista);
        final AdapterPlaya adaptador = new AdapterPlaya(this, playasAleatorias(), getApplicationContext());
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Playa p = (Playa) adaptador.getItem(position);
                Playa playaSeleccionada = filtrado.obtenerPlayaAPartirNombre(Procesamiento.getPlayas(), p.getNombre());
                //Lanzamos la activity de la playa seleccionada.
                final Intent mIntent=new Intent(VentanaPrincipal.this, PlayaActivity.class);
                mIntent.putExtra("OBJETO_KEY",playaSeleccionada);
                mIntent.putExtra("POSITION_KEY",position);
                startActivity(mIntent);

            }
        });
        vamosSolucionarEsto();
    }

    /**
     * Ver un mapa con las playas
     * @param view
     */
    public void cambiarMapa(View view)
    {
        final Intent mIntent=new Intent(VentanaPrincipal.this, MapsActivity.class);
        startActivity(mIntent);
    }

    public void verListadoPlayas(View view){
        final Intent mIntent=new Intent(VentanaPrincipal.this, ListadoPlayas.class);
        startActivity(mIntent);
        ArrayList<Playa> lista = filtrado.obtenerTodasPlayas(Procesamiento.getPlayas());
        ListadoPlayas.especificarLista(lista);
    }

    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ventana_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.acercade) {
            final Intent mIntent=new Intent(VentanaPrincipal.this, infoApp.class);
            startActivity(mIntent);
            return true;
        }else if(id == R.id.favoritas){
            openDB();
            ArrayList<Playa> fav = filtrado.getFavoritas(Procesamiento.getPlayas(),bd);
            if(fav.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No tiene playas como favoritas.", Toast.LENGTH_LONG).show();
            }
            else {
                ListadoPlayas.especificarLista(fav);
                final Intent mIntent = new Intent(VentanaPrincipal.this, ListadoPlayas.class);
                startActivity(mIntent);
            }
            closeDB();
            return true;
        }else if (id == R.id.distancia) {
            if(isGPSProvider(getApplicationContext())) {
                vamosSolucionarEsto();
                if (Geolocalizacion.geo == false) {
                    Toast.makeText(getApplicationContext(), "Es necesario otorgar los permisos de geolocalización", Toast.LENGTH_LONG).show();
                } else {
                    ArrayList<Playa> dist = filtrado.filtradoDistancia(Procesamiento.getPlayas(), distancia());
                    ListadoPlayas.especificarLista(dist);
                    final Intent mIntent = new Intent(VentanaPrincipal.this, ListadoPlayas.class);
                    startActivity(mIntent);
                }
                return true;
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Debe activar la geolocalización", Toast.LENGTH_LONG).show();
                return true;
            }
        }

        final Intent mIntent=new Intent(VentanaPrincipal.this, ListadoPlayas.class);
        startActivity(mIntent);
        opcionEscogida(id);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Busca las diferentes playas según la opción seleccionada
     * @param opcion elegida
     */
    public void opcionEscogida(int opcion) {
        ArrayList<Playa> lista = null;
        ArrayList<Playa> playas = Procesamiento.getPlayas();
        if (opcion == R.id.oriente) {
            lista = filtrado.filtrarPorZona(playas, "Oriente");
            ListadoPlayas.especificarLista(lista);
        } else if (opcion == R.id.occidente) {
            lista = filtrado.filtrarPorZona(playas, "Occidente");
            ListadoPlayas.especificarLista(lista);
        } else if (opcion == R.id.centro) {
            lista = filtrado.filtrarPorZona(playas, "Centro");
            ListadoPlayas.especificarLista(lista);
        }
        else if (opcion == R.id.banderaAzul) {
            lista = filtrado.filtrarBanderaAzul(playas);
            ListadoPlayas.especificarLista(lista);
        }
    }


    private ArrayList<Playa> playasAleatorias(){
        ArrayList<Playa> playasElegidas = new ArrayList<Playa>();
        Random r = new Random();
        for(int i=0; i<5; i++){
            int valorDado = r.nextInt(Procesamiento.getPlayas().size());
            if(playasElegidas.contains(Procesamiento.getPlayas().get(valorDado)))
                i--;
            else
                playasElegidas.add(Procesamiento.getPlayas().get(valorDado));
        }
        return playasElegidas;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        localizacionActual = LocationServices.FusedLocationApi.getLastLocation(clienteLocalizacion);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        localizacionActual = location;
    }

    private void vamosSolucionarEsto()
    {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        localizacionActual = LocationServices.FusedLocationApi.getLastLocation(clienteLocalizacion);
        Geolocalizacion.geo = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        clienteLocalizacion.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        clienteLocalizacion.disconnect();
    }

    /* Esto es un fragmento de código que se corresponde con la petición de permisos
   necesarios para realizar la geolocalización. Este fragmento de código es necesario
   a partir de android 5. Se debe incluir en el onCreate() antes de utilizar cualquiera
   de los métodos relacionados. */
    private void pedirPermisos() {
        if (ContextCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            }
        }
    }

    private void crearClienteLoc() {

        if (clienteLocalizacion == null) {
            clienteLocalizacion = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        }
    }

    /**
     * Posicion actual
     *
     * @return
     */
    public ArrayList<Double> distancia() {
        crearClienteLoc();
        double latitud = localizacionActual.getLatitude();
        double longitud = localizacionActual.getLongitude();

        ArrayList<Double> posiciones = new ArrayList<Double>();

        posiciones.add(latitud);
        posiciones.add(longitud);

        return posiciones;
    }

    /**
     * GPS activado
     * @param context
     * @return
     */
    private boolean isGPSProvider(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * Método que se conecta a la base de datos
     */
    public void openDB(){
        bd = new FavoritesDataSource(getApplicationContext());
        bd.open();
    }

    /**
     * Método que cierra la base de datos
     */
    public void closeDB(){
        bd.close();
    }

    /**
     * Ver todas las playas
     * @param view
     */
    public void verTodasLasPlayas(View view)
    {
        ArrayList<Playa> lista = null;
        ArrayList<Playa> playas = Procesamiento.getPlayas();
        lista = filtrado.obtenerTodasPlayas(playas);
        ListadoPlayas.especificarLista(lista);
        final Intent mIntent=new Intent(VentanaPrincipal.this, ListadoPlayas.class);
        startActivity(mIntent);
    }


}
