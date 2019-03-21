package igu;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.pelayo.fragments.R;

import java.util.ArrayList;

import logica.Playa;


/**
 * Created by Pelayo on 09/01/2018.
 */

public class AdapterPlaya extends BaseAdapter {

    private Activity activity;
    private ArrayList<Playa> playas;
    private Context context;    //Para coger las imágenes del Drawable.


    public AdapterPlaya(Activity activity, ArrayList<Playa> playas, Context context){
        this.activity = activity;
        this.playas = playas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return playas.size();
    }

    @Override
    public Object getItem(int i) {
        return playas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.playa_lista, null);
        }

        Playa p = playas.get(i);

        ImageView imagen = (ImageView) view.findViewById(R.id.imagenPlayaLista);
        String fichero=p.getImagen();
        String recurso="drawable";
        int res_imagen = context.getResources().getIdentifier(fichero, recurso, context.getPackageName());
        imagen.setImageResource(res_imagen);

        TextView nombre = (TextView) view.findViewById(R.id.nombrePlayaLista);
        nombre.setText(Html.fromHtml("<b>"+p.getNombre()+"<b>"));

        TextView zona = (TextView) view.findViewById(R.id.zonaPlayaLista);
        zona.setText(p.getZona());

        TextView concejo = (TextView) view.findViewById(R.id.concejoPlayaLista);
        concejo.setText(p.getConcejo());

        TextView longitud = (TextView) view.findViewById(R.id.longitudPlayaLista);
        longitud.setText(p.getLongitud());

        return view;
    }

}
