package logica;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import persistencia.FavoritesDataSource;

/**
 * Created by Pelayo on 08/01/2018.
 */

public class Filtrados {

    /**
     * Devuelve una lista de las playas filtradas por zona.
     * @param playas lista de playas que se le pasa.
     * @param zona, donde se encuentran
     * @return una lista con el nombre de las playas.
     */
    public ArrayList<Playa> filtrarPorZona(ArrayList<Playa> playas, String zona){
        ArrayList<Playa> playasFiltradas = new ArrayList<Playa>();
        for(Playa p : playas){
            if(p.getZona().contains(zona)) {
                playasFiltradas.add(p);
            }
        }
        return playasFiltradas;
    }

    /**
     * Obtiene el nombre de todas las playas
     * @param playas, lista de playas
     * @return nombre de todas las playas
     */
    public ArrayList<Playa> obtenerTodasPlayas(ArrayList<Playa> playas){
        ArrayList<Playa> playasFiltradas = new ArrayList<Playa>();
        for(Playa p : playas){
            playasFiltradas.add(p);
        }
        return playasFiltradas;
    }

    /**
     * Devuelve una playa a partir de su nombre
     * @param playas la lista de playas
     * @param nombre, nombre de la playa
     * @return la playa buscada
     */
    public Playa obtenerPlayaAPartirNombre(ArrayList<Playa> playas, String nombre){
        for(Playa p : playas){
            if(p.getNombre().equals(nombre))
                return p;
        }
        return null;
    }

    /**
     * Playas con bandera azul
     * @param playas
     * @return
     */
    public ArrayList<Playa> filtrarBanderaAzul(ArrayList<Playa> playas){
        ArrayList<Playa> playasFiltradas = new ArrayList<Playa>();
        for(Playa p : playas){
            if(p.isBanderaAzul()) {
                playasFiltradas.add(p);
            }
        }
        return playasFiltradas;
    }

    public ArrayList<Playa> filtradoDistancia(ArrayList<Playa> playas, ArrayList<Double> posicion)
    {
        ArrayList<Playa> playasFiltradas = new ArrayList<Playa>();
        Ordenacion.ordenarPlayasDistancia(playas, posicion);
        for(int i=0; i<10; i++)
        {
            playasFiltradas.add(playas.get(i));
        }

        return playasFiltradas;
    }


    /**
     * Filtra y devuelve las playas favoritas
     * @param allPlayas
     * @return
     */
    public ArrayList<Playa> getFavoritas(ArrayList<Playa> allPlayas, FavoritesDataSource bd){
        List<String> positions = bd.getAllFavorites();
        ArrayList<Playa> favoritas = new ArrayList<Playa>();

        for(int i=0;i<positions.size();i++){
            for(int j=0;j<allPlayas.size();j++){
                Log.d("SSP",positions.get(i));
                Log.d("SSA",allPlayas.get(j).getNombre());
                if(positions.get(i).equals(allPlayas.get(j).getNombre())){
                    favoritas.add(allPlayas.get(j));
                }
            }
        }

        return favoritas;
    }


}
