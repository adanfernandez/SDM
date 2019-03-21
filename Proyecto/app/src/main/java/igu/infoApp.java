package igu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.pelayo.fragments.R;

public class infoApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_app);
        rellenarInfo();
    }

    /**
     * Rellena la información del acerca de
     */
    private void rellenarInfo(){
        TextView info = (TextView) findViewById(R.id.tInfo);
        info.setText("Esta aplicación ha sido desarrollada por Adán Fernández Sánchez, Pelayo García Torre y Pablo Menéndez Suárez " +
                "para la asignatura de Software para Dispositivos Móviles en el curso 2017/2018. \nSe trata de una aplicación que permite localizar " +
                "todas las playas de Asturias así como ver la información y características de cada una de ellas, situarlas en el mapa y ver sus datos metereológicos en tiempo real." +
                " También podrás guardar tus playas favoritas y buscarlas en función de la distancia o de su zona.");
    }

}
