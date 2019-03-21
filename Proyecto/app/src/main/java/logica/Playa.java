package logica;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pelayo on 26/12/2017.
 */

public class Playa implements Parcelable{

    @Override
	public String toString() {
		return "Playa [nombre=" + nombre + ", imagen=" + imagen + "]";
	}

	//Nombre de la playa
    private String nombre;

    //Zona donde se encuentra
    private String zona;

    //Información de la playa
    private String informacion;

    //true si tiene bandera azul, false en caso contrario
    private boolean banderaAzul;

    //Concejo al que pertenece
    private String concejo;

    //Acceso a la playa
    private String acceso;

    //Tipo de playa
    private String tipo;

    //Servicios que ofrece
    private String servicios;

    //Longitud de la playa
    private String longitud;

    //Localización de la playa
    private double [] posicion;
    
    private String imagen;

    //Constructor
    public Playa(String nombre, String zona, String informacion, boolean banderaAzul, String concejo,
                 String acceso, String tipo, String servicios, String longitud, double [] posicion, String imagen){
        this.nombre = nombre;
        this.zona = zona;
        this.informacion = informacion;
        this.banderaAzul = banderaAzul;
        this.concejo = concejo;
        this.acceso = acceso;
        this.tipo = tipo;
        this.servicios = servicios;
        this.longitud = longitud;
        this.posicion = posicion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getZona() {
        return zona;
    }

    public String getInformacion() {
        return informacion;
    }

    public boolean isBanderaAzul() {
        return banderaAzul;
    }

    public String getConcejo() {
        return concejo;
    }

    public String getAcceso() {
        return acceso;
    }

    public String getTipo() {
        return tipo;
    }

    public String getServicios() {
        return servicios;
    }

    public String getLongitud() {
        return longitud;
    }

    public double[] getPosicion() {
        return posicion;
    }
    
    public String getImagen()
    {
    	return imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(zona);
        parcel.writeString(informacion);
        parcel.writeString(concejo);
        parcel.writeString(acceso);
        parcel.writeString(tipo);
        parcel.writeString(servicios);
        parcel.writeString(longitud);
        parcel.writeDoubleArray(posicion);
        parcel.writeString(imagen);

    }

    protected Playa(Parcel in){
        nombre = in.readString();
        zona = in.readString();
        informacion = in.readString();
        concejo = in.readString();
        acceso = in.readString();
        tipo = in.readString();
        servicios = in.readString();
        longitud = in.readString();
        posicion = in.createDoubleArray();
        imagen = in.readString();
    }

    public final static  Creator<Playa> CREATOR = new Creator<Playa>() {
        @Override
        public Playa createFromParcel(Parcel parcel) {
            return new Playa(parcel);
        }

        @Override
        public Playa[] newArray(int i) {
            return new Playa[0];
        }
    };
}
