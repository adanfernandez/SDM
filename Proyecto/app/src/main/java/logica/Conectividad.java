package logica;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

/**
 * Created by ascii on 09/01/2018.
 */

public class Conectividad {
    private Context mContext;

    public Conectividad(Context context) {
        this.mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean CompruebaConexion() {


        boolean connected = false;

        //ConnectivityManager es la clase que pregunta por el estado de la conexión de las redes
        //mContext.getSystemService(Context.CONNECTIVITY_SERVICE): Instancia de la clase ConnectivityManager
        //getSystemService: Permite manejar servicios del sistema. El parámetro que se le pasa es la clase del servicio que
        //se desea manejar



        ConnectivityManager connectivityManager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);


        Network[] networks = connectivityManager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network mNetwork : networks) {
            networkInfo = connectivityManager.getNetworkInfo(mNetwork);
            if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                connected = true;
            }

        }
        Log.i("Conectividad", "Paso por Comprueba Conexion");
        return connected;
    }
}

/*
    public void compartir (){

        Conectividad conexion=new Conectividad(getApplicationContext());
        if (!conexion.CompruebaConexion())
            Toast.makeText(getApplicationContext(), R.string.actividadCancelada, Toast.LENGTH_SHORT).show();

        else {

            Intent itSend = new Intent(android.content.Intent.ACTION_SEND);

            itSend.setType("text/plain");

            itSend.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.NuevoLibro));


            //AQUI MODIFICAR PARA VUESTRO CASO
            itSend.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.Titulo)
                    +" "+titulo.getText().toString()+" "+
                    getString(R.string.Autor)+" "+autor.getText().toString());

            startActivity(itSend);
        }

    }

    */