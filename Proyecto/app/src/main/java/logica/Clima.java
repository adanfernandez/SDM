package logica;

/**
 * Created by UO252406 on 05/01/2018.
 */

public class Clima {


    /**
     * Datos marea
     */
    private String tipoMarea;
    private double periodo;
    private double alturaOleaje;
    private int tempAgua;
    private int velocidadViento;

    /**
     * Datos tiempo
     */
    private int temp;
    private int minTemp;
    private int maxTemp;
    private int humedad;
    private int presion;
    private int vViento;
    private int dViento;
    private int nubosidad;


    private String descripcion;
    private String icon;

    public Clima(String tipoMarea, double periodo, double alturaOleaje, int tempAgua, int velocidadViento,
                 int visibilidad,int temp,int minTemp,int maxTemp,int humedad,int presion, String descripcion,String icon) {

        this.tipoMarea = tipoMarea;
        this.periodo = periodo;
        this.alturaOleaje = alturaOleaje;
        this.tempAgua = tempAgua;
        this.velocidadViento = velocidadViento;

        this.temp=temp;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.humedad=humedad;
        this.presion=presion;

        this.descripcion=descripcion;
    }

    public Clima(int temp,int minTemp,int maxTemp,int humedad,int presion, String descripcion,String icon) {

        this.temp=temp;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.humedad=humedad;
        this.presion=presion;

        this.descripcion=descripcion;



    }


    public Clima(int temp, int minTemp, int maxTemp, int humedad, int presion, String descripcion, String icon, int vViento,int dViento, int nubosidad) {

        this.temp=temp;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.humedad=humedad;
        this.presion=presion;

        this.vViento=vViento;
        this.dViento = dViento;
        this.nubosidad=nubosidad;

        this.descripcion=descripcion;

    }

    public String getTipoMarea() {
        return tipoMarea;
    }

    public void setTipoMarea(String tipoMarea) {
        this.tipoMarea = tipoMarea;
    }

    public double getPeriodo() {
        return periodo;
    }

    public void setPeriodo(double periodo) {
        this.periodo = periodo;
    }

    public double getAlturaOleaje() {
        return alturaOleaje;
    }

    public void setAlturaOleaje(double alturaOleaje) {
        this.alturaOleaje = alturaOleaje;
    }

    public int getTempAgua() {
        return tempAgua;
    }

    public void setTempAgua(int tempAgua) {
        this.tempAgua = tempAgua;
    }

    public int getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(int velocidadViento) {
        this.velocidadViento = velocidadViento;
    }


    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public int getPresion() {
        return presion;
    }

    public void setPresion(int presion) {
        this.presion = presion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIcon() {return icon;}

    public void setIcon(String icon) {this.icon = icon;}

    public int getvViento() {
        return vViento;
    }

    public void setvViento(int vViento) {
        this.vViento = vViento;
    }


    public int getNubosidad() {
        return nubosidad;
    }

    public void setNubosidad(int nubosidad) {
        this.nubosidad = nubosidad;
    }


    public int getdViento() {
        return dViento;
    }

    public void setdViento(int dViento) {
        this.dViento = dViento;
    }

    @Override
    public String toString() {
        return "Clima{" +
                ", temp=" + temp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                ", humedad=" + humedad +
                ", presion=" + presion +
                ", vViento=" +
                ", dViento=" +
                ", nubosidad=" +
                ", lluvia=" +
                ", descripcion='" + descripcion + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
