package igu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.pelayo.fragments.R;
import com.google.android.gms.maps.model.LatLng;

import logica.Clima;
import logica.GestionApis;
import logica.Playa;
import logica.Procesamiento;

public class PlayaTiempo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playa_tiempo);
        Bundle b = getIntent().getExtras();
        double[] cords = b.getDoubleArray("CORDS");
        LatLng lat = new LatLng(cords[0],cords[1]);
        updateWeather(lat);
    }



    public void updateWeather(LatLng lat){
        GestionApis g = new GestionApis();
        Clima c = g.createClima(lat);

        TextView desc = (TextView) findViewById(R.id.tDesc);
        TextView temp = (TextView) findViewById(R.id.tTemp);
        TextView minTemp = (TextView) findViewById(R.id.tMinTemp);
        TextView maxTemp = (TextView) findViewById(R.id.tMaxTemp);
        TextView humedad = (TextView) findViewById(R.id.tHumedad);
        TextView presion = (TextView) findViewById(R.id.tPresion);

        TextView vViento = (TextView) findViewById(R.id.tVviento);
        TextView dViento = (TextView) findViewById(R.id.tDviento);
        TextView nubosidad = (TextView) findViewById(R.id.tNubo);


        desc.setText("Descripción del día: "+c.getDescripcion());
        temp.setText("Temperatura: "+c.getTemp()+ " ºC");
        minTemp.setText("Temperatura mínima: "+c.getMinTemp()+ " ºC");
        maxTemp.setText("Temperatura máxima: "+c.getMaxTemp()+ " ºC");
        humedad.setText("Humedad: "+c.getHumedad()+ "%");
        presion.setText("Presion: "+c.getPresion()+ "hpa");

        vViento.setText("Velocidad del viento: "+c.getvViento()+" metro / seg");
        dViento.setText("Dirección del viento: "+c.getdViento()+"º");
        nubosidad.setText("Nubosidad: "+c.getNubosidad()+"%");

    }


}
