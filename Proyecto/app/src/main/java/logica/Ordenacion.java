package logica;

import android.location.Location;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pelayo on 07/01/2018.
 */

public class Ordenacion {

    public void ordenarPlayasNombre(List<Playa> playas){
        Collections.sort(playas, new Comparator<Playa>() {

            @Override
            public int compare(Playa arg0, Playa arg1) {
                return arg0.getNombre().compareTo(arg1.getNombre());
            }

        });
    }

    public static void ordenarPlayasDistancia(List<Playa> playas, ArrayList<Double> distancia){

        final Location localizacion = new Location("Localización del usuario");

        localizacion.setLatitude(distancia.get(0));
        localizacion.setLongitude(distancia.get(1));


        Collections.sort(playas, new Comparator<Playa>() {

            @Override
            public int compare(Playa arg0, Playa arg1) {

                Location loc0 = new Location("Localizacion1");
                loc0.setLatitude(arg0.getPosicion()[0]);
                loc0.setLongitude(arg0.getPosicion()[1]);

                Location loc1 = new Location("Localizacion2");
                loc1.setLatitude(arg1.getPosicion()[0]);
                loc1.setLongitude(arg1.getPosicion()[1]);


                return (int) (loc0.distanceTo(localizacion)- loc1.distanceTo(localizacion));
            }
        });
    }


}
