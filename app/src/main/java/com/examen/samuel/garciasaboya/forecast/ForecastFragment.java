package com.examen.samuel.garciasaboya.forecast;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Samuel on 12/12/2014.
 */


    public class ForecastFragment extends Fragment {

    private final static String TAG = ForecastFragment.class.getName();
    /*
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private  final static String[] tiempo = {
            "hoy soleado", "Mañana tormentas",
            "Pasado mañana lluvia de meteoritos"
    };
    */


    public ForecastFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        // Creamos la ruta adecuada
        Resources res = getResources();
        String[] valores = res.getStringArray(R.array.weekforecast);

        // Creamos el ArrayAdapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), R.layout.listitemforecast, R.id.list_item_forecast_textview, valores);

        ListView lv1 = (ListView) rootView.findViewById(R.id.listView1);
        lv1.setAdapter(adapter);


        //These two need to be declared outside the try/catch
        //so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        //Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        //Construct the URL for the OpenWeather Map query
        //Possible parameters are avaiable at OWM's forecast API page, at http://openweathermap.org/API#forecast
        URL url = null;
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Madrid&mode=json&units=metric&cnt=7";
            url = new URL(urlString);
            //Create the request to OpenWeather Map, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                //Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                //Since it's JSON, adding a new line isn't necessary (it won't affect parsing)
                //But it does make debugginga *lot* easier if youpr into ut the completed
                //buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                //Stream was empty. No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error", e);
        } catch (ProtocolException e) {
            Log.e(TAG, "Error", e);
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
            //If the code didn't successfully get the weather data,there's no point in attemping to parse it.
            forecastJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Errorclosingstream", e);
                }
            }

            return rootView;
        }



    }

    class FetchWeatherTask extends AsyncTask<URL, Integer, Long> {

        private final String TAG = FetchWeatherTask.class.getName();

        @Override
        protected Long doInBackground(URL... params) {

            //  These two need to be declared outside the try/catch
            // so that they can be closed in the finally block
            // Sigo aquí

            //These two need to be declared outside the try/catch
            //so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            //Construct the URL for the OpenWeather Map query
            //Possible parameters are avaiable at OWM's forecast API page, at http://openweathermap.org/API#forecast
            URL url = null;
            try {
                String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Madrid&mode=json&units=metric&cnt=7";
                url = new URL(urlString);
                //Create the request to OpenWeather Map, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    //Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    //Since it's JSON, adding a new line isn't necessary (it won't affect parsing)
                    //But it does make debugginga *lot* easier if youpr into ut the completed
                    //buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    //Stream was empty. No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Error", e);
            } catch (ProtocolException e) {
                Log.e(TAG, "Error", e);
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
                //If the code didn't successfully get the weather data,there's no point in attemping to parse it.
                forecastJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Errorclosingstream", e);
                    }
                }

                return null;
            }
        }

    }
}
