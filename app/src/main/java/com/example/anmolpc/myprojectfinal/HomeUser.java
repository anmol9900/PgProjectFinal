package com.example.anmolpc.myprojectfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Anmol Pc on 12/4/2016.
 */

public class HomeUser extends Fragment {

    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    RecyclerView rcy_view;
    String[] roomid,address,name,id,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
    int oid_val,loop;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_user, container, false);
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        rcy_view=(RecyclerView)view.findViewById(R.id.id_rcy_myfav);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcy_view.setLayoutManager(llm);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
        String check=sharedPreferences.getString("LoggedIn","null");
        if(check.equals("Yes")) {
            oid_val=sharedPreferences.getInt("Id",0);
            Log.e("oid_val",Integer.toString(oid_val));
            myPgFav();
        }
        return view;
    }

    public void myPgFav()
    {
        try
        {
            creatCon();
            response="";
            line="";
            sb.setLength(0);
            data="";
            outputStream = urlConnection.getOutputStream();
            streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(streamWriter);
            String data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(oid_val), "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            Log.e("DATA",data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    data = sb.toString();
                }
                response = data.toString();

                Log.e("Response",response);
            }
            JSONArray ja = new JSONArray(response);
            JSONObject jo = null;
            name = new String[ja.length()];
            address = new String[ja.length()];
            id = new String[ja.length()];
            contact = new String[ja.length()];
            age = new String[ja.length()];
            mstatus = new String[ja.length()];
            gender = new String[ja.length()];
            roomtype = new String[ja.length()];
            totalroom = new String[ja.length()];
            price = new String[ja.length()];
            security = new String[ja.length()];
            occupancy = new String[ja.length()];
            extraservices=new String[ja.length()];
            services=new String[ja.length()];
            avail=new String[ja.length()];
            roomid=new String[ja.length()];
            loop=ja.length();
            for (int i = 0; i < ja.length(); i++) {

                jo = ja.getJSONObject(i);
                name[i] = jo.getString("pgname");
                address[i] = jo.getString("pgaddress");
                id[i] = jo.getString("pgid");
                contact[i]=jo.getString("pgcontact");
                age[i]=jo.getString("age");
                mstatus[i]=jo.getString("mstatus");
                gender[i]=jo.getString("gender");
                roomtype[i]=jo.getString("roomtype");
                price[i]=jo.getString("price");
                security[i]=jo.getString("security");
                occupancy[i]=jo.getString("occupancy");
                extraservices[i]=jo.getString("services");
                avail[i]=jo.getString("avail");
                totalroom[i]=jo.getString("rooms");
                roomid[i]=jo.getString("roomid");
                if(jo.getString("food").equals("1"))
                {
                    services[i] = "Food";
                }
                if(jo.getString("ac").equals("1"))
                {
                    if(services[i]!=null) {
                        services[i] += ",Air Conditioner";
                    }
                    else
                    {
                        services[i] += "Air Conditioner";
                    }
                }
                if(jo.getString("fridg").equals("1"))
                {
                    if(services[i]!=null) {
                        services[i] += ",Fridge";
                    }
                    else
                    {
                        services[i] += "Fridge";
                    }
                }
                if(jo.getString("cooler").equals("1"))
                {
                    if(services[i]!=null) {
                        services[i] += ",Cooler";
                    }
                    else
                    {
                        services[i] += "Cooler";
                    }
                }
            }
            CustomAdapter adapter1=new CustomAdapter(Integer.toString(oid_val),"favlist",getContext(),address,name,id);
            rcy_view.setAdapter(adapter1);
        }
        catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }
    }

    public void creatCon() throws Exception
    {
        URL url=new URL("http://10.0.2.2/PGdatabase/mypgfav.php");
        urlConnection=(HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
    }
}
