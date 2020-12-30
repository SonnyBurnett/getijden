package com.sonnyburnettfun.getijden;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



//
// Verschil tussen IJmuiden en Bergen is gemiddeld ongeveer 49 minuten, maar de modus is 30!
// Ik kies voor een correctie van 35 minuten later dan IJmuiden.
//
// Het hoogteverschil is gemiddeld 11, dat is ook ongeveer de top van de bell curve.
// Dat wil zeggen: IJmuiden is 11 hoger dan Bergen
//

public class JSONfile {

    static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);


            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    static List<Waterstand> convertJSONtoArrayList(String jsonFileString) throws JSONException, IOException {
        ArrayList<String> list = new ArrayList<String>();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Waterstand> waterstanden = new ArrayList<>();
        Waterstand w = null;
        JSONArray array = null;
        JSONObject json= null;

        json = new JSONObject(jsonFileString);
        array = json.getJSONArray("waterstanden"); /* eerste veld uit het JSON file!!!!! */
        Gson g = new Gson();


        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
            w = g.fromJson(array.getString(i), Waterstand.class);
            waterstanden.add(w);
        }

        return waterstanden;

    }

    static String convertListOfObjectsToJSONstring(List<Waterstand> tides) {
        Gson g = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(tides);
        return json;
    }

    static List<Waterstand> convertJSONStringToListOfObjects(String jsonString) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Waterstand> waterstanden = new ArrayList<>();

        try {
            waterstanden = mapper.readValue(jsonString, new TypeReference<List<Waterstand>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("msg", "waterstanden is groot: " + waterstanden.size());
        return waterstanden;


    }

    static List<Waterstand> getWaterstanden(Context con, String plaats) {
        String fileNaam = "";

        Log.e("msg", "We laden nu de data van " + plaats);

        switch(plaats) {
            case "Bergen":
                //fileNaam = "bergen2021.json";
                break;
            case "Texel":
                fileNaam = "texel2021.json";
                break;
            case "IJmuiden":
                fileNaam = "ijmuiden2021.json";
                break;
            case "Den Helder":
                fileNaam = "denhelder2021.json";
                break;
        }

        String jsonFileString = JSONfile.getJsonFromAssets(con, fileNaam);

        List<Waterstand> waterstanden = null;
        try {
            waterstanden = JSONfile.convertJSONtoArrayList(jsonFileString);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return waterstanden;
    }


}
