package com.example.anmolpc.myprojectfinal;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
 * Created by Anmol Pc on 11/26/2016.
 */

public class UpdateAvail {
    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    String name,address,id,roomid,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
    Context context;
    public UpdateAvail(Context context,String address,String name,String contact,String age,String mstatus,String gender,String roomtype,String totalroom,String avail,String occupancy,String price,String security,String services,String extraservices,String roomid,String id)
    {
        this.context=context;
        this.address=address;
        this.name=name;
        this.id=id;
        this.contact=contact;
        this.age=age;
        this.mstatus=mstatus;
        this.gender=gender;
        this.roomtype=roomtype;
        this.totalroom=totalroom;
        this.avail=avail;
        this.occupancy=occupancy;
        this.price=price;
        this.security=security;
        this.services=services;
        this.extraservices=extraservices;
        this.roomid=roomid;
    }

    public void creatCon() throws Exception
    {
        URL url=new URL("http://10.0.2.2/PGdatabase/savedata.php");
        urlConnection=(HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
    }

    public void update()
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
                    String data = URLEncoder.encode("roomidtxt", "UTF-8") + "=" + URLEncoder.encode(roomid, "UTF-8") + "&" +
                            URLEncoder.encode("updatedata", "UTF-8") + "=" + URLEncoder.encode(avail, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
            Log.e("Data",data);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    if (reader != null) {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                            data = sb.toString();
                        }
                    }
                    response = data.toString();
            Log.e("Response",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("1")) {
                        Toast.makeText(context, "Update Successfull!!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(context, "Update Failed!!", Toast.LENGTH_SHORT).show();
                    }

        }catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }
    }
}
