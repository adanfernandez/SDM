package logica;

import android.content.Context;


import com.example.pelayo.fragments.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Procesamiento {

	//Listas con las diferentes partes de una playa.
    private ArrayList<String> nombre = new ArrayList<String>();
    private ArrayList<String> zona = new ArrayList<String>();
    private ArrayList<String> informacion = new ArrayList<String>();
    private ArrayList<String> banderaAzul = new ArrayList<String>();
    private ArrayList<String> concejo = new ArrayList<String>();
	private ArrayList<String> acceso = new ArrayList<String>();
	private ArrayList<String> tipo = new ArrayList<String>();
	private ArrayList<String> servicios = new ArrayList<String>();
	private ArrayList<String> posicion = new ArrayList<String>();
	private ArrayList<String> longitud = new ArrayList<String>();
	private ArrayList<String> imagenes = new ArrayList<String>();

	private Context context;

	public static String nombrePlaya = "";

	static ArrayList<Playa> playas = new ArrayList<Playa>();	//Lista donde se almacenan todas las playas.

    public Procesamiento(Context context){
		this.context = context;
		Ordenacion o = new Ordenacion();
		try {
			playas =  obtenerPlayas();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Ordenamos las playas por nombre.
		o.ordenarPlayasNombre(playas);

    }

	public ArrayList<Playa> obtenerPlayas() throws IOException
	{
		datosSeparados();
		return guardarPlayas();
	}
	
	
	public void datosSeparados() throws IOException {
		try {
			InputStream archivo = context.getResources().openRawResource(R.raw.playas);
			BufferedReader reader = new BufferedReader(new InputStreamReader(archivo));
			while (reader.ready()) {
				String linea = reader.readLine();
				String[] datos = linea.split(";");
				nombre.add(datos[0]);
				zona.add(datos[2]);
				informacion.add(datos[3]);
				banderaAzul.add(datos[4]);
				concejo.add(datos[6]);
				acceso.add(datos[7]);
				tipo.add(datos[8]);
				servicios.add(datos[9]);
				longitud.add(datos[10]);
				posicion.add(datos[24]);
				imagenes.add(datos[29]);
			}
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private ArrayList<Playa> guardarPlayas()
	{
		ArrayList<Playa> playas = new ArrayList<Playa>();
		for(int i=0; i<nombre.size();i++)
		{
			String nombre = this.nombre.get(i);
			String zona= this.zona.get(i);
			String informacion= this.informacion.get(i);
			String banderaAzul= this.banderaAzul.get(i);
			String concejo= this.concejo.get(i);
			String acceso= this.acceso.get(i);
			String tipo= this.tipo.get(i);
			String servicios= this.servicios.get(i);
			String longitud= this.longitud.get(i);
			String posicion= this.posicion.get(i);
			String imagen = this.imagenes.get(i);

			String[] posicionConstructorAuxiliar = posicion.split(",");
			double[] posicionConstructor = new double[posicionConstructorAuxiliar.length];
			
			for(int j = 0; j<posicionConstructorAuxiliar.length; j++)
			{
				posicionConstructor[j] = Double.parseDouble(posicionConstructorAuxiliar[j]);
			}
			
			boolean banderaAzulConstructor=false;
			if(banderaAzul.startsWith("t"))
				banderaAzulConstructor= true;
			
			Playa playa = new Playa(nombre, zona, informacion, banderaAzulConstructor,  concejo, acceso, tipo, servicios, longitud, posicionConstructor, imagen);
			playas.add(playa);
		}
		return playas;
	}

	public static ArrayList<Playa> getPlayas() {
		return playas;
	}

}
