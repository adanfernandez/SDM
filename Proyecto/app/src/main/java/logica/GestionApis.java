package logica;

import android.os.StrictMode;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by UO252406 on 05/01/2018.
 */

public class GestionApis {


    private static final String MAREA_KEY="4a4761e6f1334acbaa6125613181201";
    private static final String TIEMPO_KEY="faccc0773995ebd8186ae19aa458aafc";


    private Clima clima;


    /**
     * Crea y retorna el objeto clima para una latitud indicada
     * @param latitud
     * @return
     */
    public Clima createClima(LatLng latitud){

        try {

            URL urlMareas = new URL("http://api.worldweatheronline.com/premium/v1/marine.ashx?key=" + MAREA_KEY +
                    "&format=json&q=" + latitud.latitude + "," + latitud.longitude+"&tide=yes");
            URL urlTiempo = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+latitud.latitude+"&lon="+latitud.longitude+
                    "&APPID="+TIEMPO_KEY+"&units=metric&lang=es");


            writeData(urlMareas,urlTiempo);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return clima;
    }



    public void writeData(URL urlMareas,URL urlTiempo){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.
                Builder().permitNetwork().build());
        String sMareas = null;
        String sTiempo = null;
        try {
            sMareas = getData(urlMareas);
            Log.d("STM",sMareas);
            sTiempo = getData(urlTiempo);
            Log.d("STT",sTiempo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        convertToClima(sMareas,sTiempo);
    }


    /**
     * Accede a la api para obtener los datos y los devuelve en forma de String
     * @param url
     * @return
     * @throws IOException
     */
    private String getData(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String cadFinal = "";
        try {
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            cadFinal = convertToString(input);

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }

        return cadFinal;
    }


    /**
     * Convierte un InputStream en un String
     * @param input
     * @return
     */
    private String convertToString(InputStream input) {
        BufferedReader bR = new BufferedReader(new InputStreamReader(input));
        StringBuilder sB = new StringBuilder();
        String linea = null;
        try {
            while ((linea = bR.readLine()) != null) {
                sB.append(linea).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sB.toString();
    }


    /**
     * Este método es el encargado de crear el objeto Clima con todos sus datos (Marea y Tiempo)
     */
    private Clima convertToClima(String sMareas,String sTiempo){
        JSONObject jsonObject = null;
        try {
            /*
            //Mareas
            jsonObject =  new JSONObject(sMareas);

            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray weather = data.getJSONArray("weather");
            JSONObject weatherObject = (JSONObject) weather.get(0);

            JSONArray tides = weatherObject.getJSONArray("tides");
            JSONObject tidesObject = (JSONObject) tides.get(0);

            JSONArray tideData = tidesObject.getJSONArray("tide_data");
            JSONObject tideDataObject = (JSONObject) tideData.get(0);
            String tipoMarea = tideDataObject.getString("tide_type");
            if(tipoMarea.equals("LOW"))
                tipoMarea = "Baja";
            else if(tipoMarea.equals("HIGH"))
                tipoMarea = "Alta";
            JSONArray hourly = weatherObject.getJSONArray("hourly");
            JSONObject hourlyObject = (JSONObject) hourly.get(0);
            double alturaOleaje = hourlyObject.getDouble("swellHeight_m");
            double periodo = hourlyObject.getDouble("swellPeriod_secs");
            int tempAgua = hourlyObject.getInt("waterTemp_C");
            int velocidadViento = hourlyObject.getInt("windspeedKmph");
            int visibilidad = hourlyObject.getInt("visibility");


            */

            //Tiempo
            jsonObject = new JSONObject(sTiempo);

            Log.d("DATOS",jsonObject.toString());

            JSONObject main = jsonObject.getJSONObject("main");
            int temp = main.getInt("temp");
            int minTemp = main.getInt("temp_min");
            int maxTemp = main.getInt("temp_max");
            int humedad = main.getInt("humidity");
            int presion = main.getInt("pressure");

            JSONArray weather = jsonObject.getJSONArray("weather");
            JSONObject  weatherObject = (JSONObject) weather.get(0);
            String descripcion = weatherObject.getString("description");
            String icon=weatherObject.getString("icon");





            JSONObject wind = jsonObject.getJSONObject("wind");
            int vViento = wind.getInt("speed");
            int dViento = wind.getInt("deg");


            JSONObject clouds = jsonObject.getJSONObject("clouds");
            int nubosidad = clouds.getInt("all");


            Log.d("DATOS",""+temp+"\t"+minTemp+"\t"+maxTemp+"\t"+humedad+"\t"+presion+"\t"+descripcion+"\t"+icon+"\t"+vViento+"\t"+dViento+"\t"+nubosidad);

            clima = new Clima(temp,minTemp,maxTemp,humedad,presion,descripcion,icon,vViento,dViento,nubosidad);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return clima;
    }



}
