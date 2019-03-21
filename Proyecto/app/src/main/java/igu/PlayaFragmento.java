package igu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pelayo.fragments.R;

import java.util.ArrayList;
import java.util.List;

import logica.Conectividad;
import persistencia.FavoritesDataSource;
import logica.Filtrados;
import logica.Playa;
import logica.Procesamiento;

import static com.example.pelayo.fragments.R.id.img;


/**
 * Created by Pelayo on 21/11/2017.
 */

public class PlayaFragmento extends Fragment {
    final static String ARG_POSITION="position";
    int mCurrentPosition =-1;

    private Context context;
    private ArrayList<Playa> lista;
    private Filtrados f = new Filtrados();
    private String nombre;

    private Playa playa;
    private  FavoritesDataSource bd;
    private int position;

    public PlayaFragmento(Context context, ArrayList<Playa> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View fragmentView = inflater.inflate(R.layout.playa_iformacion_fragment, container, false);

        Button mapa = (Button) fragmentView.findViewById(R.id.btMapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View v) {
                verMapa(fragmentView);
            }
        });

        Button tiempo = (Button) fragmentView.findViewById(R.id.btnTiempo);
        tiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                verTiempo(fragmentView, playa);
            }
        });

        Button favoritos = (Button) fragmentView.findViewById(R.id.btnFavoritos);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                actualizarFavoritos(fragmentView, nombre);
            }
        });

        return fragmentView;
    }

    @Override
    public void onStart(){
        super.onStart();
        //Empieza el fragmento
        Bundle args = getArguments();
        if(args != null) {
            mCurrentPosition = args.getInt(ARG_POSITION);
            //Le paso al libro los datos.
            updatePlayaView(mCurrentPosition);
            checkBotones(getActivity().findViewById(R.id.playa_informacion));
        }
    }

    public void updatePlayaView(int position){
        Playa p = lista.get(position);
        playa = p;

        ImageView imagen = (ImageView) getActivity().findViewById(img);
        TextView nombre = (TextView) getActivity().findViewById(R.id.nombre);
        TextView zona = (TextView) getActivity().findViewById(R.id.Zona);
        TextView concejo = (TextView) getActivity().findViewById(R.id.concejo);
        TextView informacion = (TextView) getActivity().findViewById(R.id.informacion);
        TextView longitud = (TextView) getActivity().findViewById(R.id.longitud);
        TextView servicios = (TextView) getActivity().findViewById(R.id.servicios);
        TextView accesos = (TextView) getActivity().findViewById(R.id.accesos);
        TextView tipo = (TextView) getActivity().findViewById(R.id.tipo);

        String fichero=p.getImagen();
        String recurso="drawable";
        int res_imagen = getResources().getIdentifier(fichero, recurso, context.getPackageName());

        Bitmap bm = BitmapFactory.decodeResource(getResources(),res_imagen);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 1200, 1000, true);

        imagen.setImageResource(res_imagen);
       // imagen.setImageBitmap(resized);

        /*
        nombre.setText("Nombre: "+p.getNombre());
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


        this.nombre = p.getNombre();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void verMapa(View view)
    {
        if(new Conectividad(getActivity()).CompruebaConexion()) {
            final Intent mIntent = new Intent(getActivity(), MapsActivity.class);

            Procesamiento.nombrePlaya = nombre;
            startActivity(mIntent);
        }
        else
        {
            Toast.makeText(getActivity(), "No posee conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void verTiempo(View view,Playa p)
    {
        if(new Conectividad(getActivity()).CompruebaConexion()) {
            final Intent mIntent = new Intent(getActivity(), PlayaTiempo.class);


            Bundle bundle = new Bundle();
            bundle.putDoubleArray("CORDS",p.getPosicion());
            mIntent.putExtras(bundle);

            Procesamiento.nombrePlaya = nombre;
            startActivity(mIntent);
        }
        else
        {
            Toast.makeText(getActivity(), "No posee conexión a internet", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Se ha añadido a favoritos", Toast.LENGTH_SHORT).show();
        }else{
            bd.removeFavorite(position(name));
            checkBotones(view);
            Toast.makeText(getActivity(), "Se ha eliminado de favoritos", Toast.LENGTH_SHORT).show();
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
        if(!isFavorita(nombre)){
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
        bd = new FavoritesDataSource(getActivity().getApplicationContext());
        bd.open();
    }

    /**
     * Método que cierra la base de datos
     */
    public void closeDB(){
        bd.close();
    }


}
