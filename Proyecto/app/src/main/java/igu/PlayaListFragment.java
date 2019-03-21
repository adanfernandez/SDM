package igu;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import logica.Playa;


/**
 * Created by Pelayo on 21/11/2017.
 */

public class PlayaListFragment extends ListFragment {

    OnPlayaSelectedListener playaItem;
    public interface OnPlayaSelectedListener {
        public void onPlayaSeleccionado(int value);
    }

    private ArrayList<Playa> lista;
    private Context context;

    //Constructor para pasarle la lista que va a mostrar.
    public PlayaListFragment(ArrayList<Playa> lista, Context context){
        this.lista = lista;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        int layout = android.R.layout.simple_list_item_1;
        //final AdapterPlaya adaptador = new AdapterPlaya(getActivity(), lista, context.getApplicationContext());
        //setListAdapter(new ArrayAdapter<>(getActivity(), layout, lista));
        //setListAdapter(adaptador);
        TareaAsincrona tarea = new TareaAsincrona();
        tarea.execute();

    }

    private class TareaAsincrona extends AsyncTask<Void,Void, AdapterPlaya>{

        @Override
        protected AdapterPlaya doInBackground(Void... voids) {
            final AdapterPlaya adaptador = new AdapterPlaya(getActivity(), lista, context.getApplicationContext());
            return adaptador;
        }

        @Override
        protected void onPostExecute(AdapterPlaya adapter){
            setListAdapter(adapter);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            playaItem = (OnPlayaSelectedListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException((getActivity().toString() + "Debes implementar onConcejoSeleccionado"));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //Notificamos a la actividad padre del libro seleccionado.
        playaItem.onPlayaSeleccionado(position);
    }
}
