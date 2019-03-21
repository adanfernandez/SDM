package igu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pelayo.fragments.R;

import java.util.List;

import logica.Conectividad;
import persistencia.FavoritesDataSource;
import logica.Playa;
import logica.Procesamiento;

import static com.example.pelayo.fragments.R.id.img;

public class PlayaActivity extends AppCompatActivity {

    private String name;

    private  FavoritesDataSource bd;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mBundleRecibido = getIntent().getExtras();
        setContentView(R.layout.playa_iformacion_fragment);

        Playa p = mBundleRecibido.getParcelable(VentanaPrincipal.OBJETO_KEY);
       // position = mBundleRecibido.getInt("POSITION_KEY");

        ImageView imagen = (ImageView) findViewById(img);
        TextView nombre = (TextView) findViewById(R.id.nombre);
        TextView zona = (TextView) findViewById(R.id.Zona);
        TextView concejo = (TextView) findViewById(R.id.concejo);
        TextView informacion = (TextView) findViewById(R.id.informacion);
        TextView longitud = (TextView) findViewById(R.id.longitud);
        TextView servicios = (TextView) findViewById(R.id.servicios);
        TextView accesos = (TextView) findViewById(R.id.accesos);
        TextView tipo = (TextView) findViewById(R.id.tipo);

        String fichero=p.getImagen();
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(fichero, recurso, getPackageName());

        Bitmap bm = BitmapFactory.decodeResource(getResources(),res_imagen);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 1000, 600, true);

        imagen.setImageResource(res_imagen);
        // imagen.setImageBitmap(resized);


        name = p.getNombre();
        /*
        nombre.setText("Nombre "+p.getNombre());
        zona.setText("Zona: "+p.getZona());
        concejo.setText("Concejo: "+p.getConcejo());
        informacion.setText(p.getInformacion());
        longitud.setText("Longitud: "+p.getLongitud());
        servicios.setText("Servicios: "+p.getServicios());
        accesos.setText("Accesos: "+p.getAcceso());
        tipo.setText("Tipo de playa: "+p.getTipo());

        */
        nombre.setText(""+p.getNombre());
        zona.setText(""+p.getZona());
        concejo.setText(""+p.getConcejo());
        informacion.setText(p.getInformacion());
        longitud.setText(""+p.getLongitud());
        servicios.setText(""+p.getServicios());
        accesos.setText(""+p.getAcceso());
        tipo.setText(""+p.getTipo());

        final View fragmentView = (View) findViewById(R.id.playa_informacion);

        Button mapa = (Button) findViewById(R.id.btMapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View v) {
                verMapa(fragmentView);
            }
        });

        final Playa playa = p;
        Button tiempo = (Button) findViewById(R.id.btnTiempo);
        tiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verTiempo(fragmentView,playa);
            }
        });

        Button favoritos = (Button) fragmentView.findViewById(R.id.btnFavoritos);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                actualizarFavoritos(fragmentView, playa.getNombre());
            }
        });

        checkBotones(fragmentView);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void verMapa(View view)
    {
        if(new Conectividad(getApplicationContext()).CompruebaConexion()) {
            final Intent mIntent = new Intent(getApplicationContext(), MapsActivity.class);

            Procesamiento.nombrePlaya = name;
            startActivity(mIntent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No posee conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void verTiempo(View view,Playa p)
    {
        if(new Conectividad(getApplicationContext()).CompruebaConexion()) {
            final Intent mIntent = new Intent(getApplicationContext(), PlayaTiempo.class);

            Bundle bundle = new Bundle();
            bundle.putDoubleArray("CORDS",p.getPosicion());
            mIntent.putExtras(bundle);

            Procesamiento.nombrePlaya = name;
            startActivity(mIntent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No posee conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Método encargado de insertar o borrar una playa de favoritos, dependiendo de la situación en la que se encuentre
     * @param view
     * @param name
     */
    public void actualizarFavoritos(View view,String name){
        openDB();
        if(!isFavorita(name)){
            bd.addFavorite(name,position(name));
            checkBotones(view);
           Toast.makeText(getApplicationContext(), "Se ha añadido a favoritos", Toast.LENGTH_SHORT).show();
        }else{
            bd.removeFavorite(position(name));
            checkBotones(view);
            Toast.makeText(getApplicationContext(), "Se ha eliminado de favoritos", Toast.LENGTH_SHORT).show();
        }
        closeDB();
    }


    private int position(String name){
        for(int i = 0; i<Procesamiento.getPlayas().size(); i++){
            if(Procesamiento.getPlayas().get(i).getNombre().equals(name))
                return i;
        }
        return 0;
    }



    /**
     * Método que comprueba si la playa ya está en favoritos
     * @param name
     * @return
     */
    public boolean isFavorita(String name){
        List<String> favorites =  bd.getAllFavorites();
        for(String i:favorites){
            if(i.equals(name)){
                return true;
            }
        }
        return false;
    }


    /**
     * Método encargado de cambiar el botón de favoritos en función de si hay que añadir o eliminar
     * @param fragmentView
     */
    public void checkBotones(View fragmentView){
        Button favoritos = (Button) fragmentView.findViewById(R.id.btnFavoritos);
        openDB();
        if(!isFavorita(name)){
            favoritos.setText("Añadir a favoritos");
        }else{
            favoritos.setText("Eliminar de favoritos");
        }
        closeDB();

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



}
