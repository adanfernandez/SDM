package igu;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.pelayo.fragments.R;

import java.util.ArrayList;

import logica.Playa;

public class ListadoPlayas extends AppCompatActivity implements PlayaListFragment.OnPlayaSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_playas);

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }
            PlayaListFragment primerFragmento = new PlayaListFragment(lista, getApplicationContext());
            primerFragmento.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, primerFragmento).commit();
        }
    }

    /**
     public boolean onCreateOptionsMenu(final Menu menu) {
     getMenuInflater().inflate(R.menu.menu_ventana_principal, menu);
     return true;
     }

     public boolean onOptionsItemSelected(MenuItem item) {
     int id = item.getItemId();
     if (id == R.id.action_settings) {
     return true;
     }
     final Intent mIntent=new Intent(ListadoPlayas.this, ListadoPlayas.class);
     startActivity(mIntent);
     menu.opcionEscogida(id);
     return super.onOptionsItemSelected(item);
     }**/

    @Override
    public void onPlayaSeleccionado(int position){

        PlayaFragmento newFragment = new PlayaFragmento(getApplicationContext(), lista);
        Bundle args = new Bundle();
        args.putInt(PlayaFragmento.ARG_POSITION, position);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private static ArrayList<Playa> lista;

    /**
     * Especifica que playas serán mostradas al usuario.
     * @param lista
     */
    public static void especificarLista(ArrayList<Playa> lista){
        ListadoPlayas.lista = lista;
    }
}
