package com.example.anmolpc.myprojectfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anmol Pc on 11/20/2016.
 */

public class AddPG extends Fragment {
    List<String> mstat=new ArrayList<String>();
    Spinner mstatspin;
    EditText pgid,pgname,contactpg,addresspg,age1,age2,pgedroomid,pgprice,pgsecurity,totalrooms,avail,occupancy,otherservices;
    CheckBox food,ac,fridge,cooler;
    Spinner rmtype;
    List<String> typelist=new ArrayList<String>();
    String roomNumber;
    Button insertClick,addbtnclick,subbtnclick;
    RadioGroup rdggender;
    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="",newcode="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    int getpgid,oid_val,getroomnew;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_addpg, container, false);
        getpgid=0;
        mstatspin=(Spinner)view.findViewById(R.id.mstatuspg);
        pgid=(EditText)view.findViewById(R.id.pgid);
        pgname=(EditText)view.findViewById(R.id.namepg);
        contactpg=(EditText)view.findViewById(R.id.contactpg);
        addresspg=(EditText)view.findViewById(R.id.addresspg);
        age1=(EditText)view.findViewById(R.id.age1);
        age2=(EditText)view.findViewById(R.id.age2);
        rdggender=(RadioGroup)view.findViewById(R.id.genderpg);
        insertClick=(Button)view.findViewById(R.id.insertClick);
        rmtype=(Spinner)view.findViewById(R.id.rmtype);
        typelist.add(0,"Normal");
        typelist.add(1,"Deluxe");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,typelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rmtype.setAdapter(adapter);
        totalrooms=(EditText)view.findViewById(R.id.ttlrooms);
        pgedroomid=(EditText)view.findViewById(R.id.pgedroomid);
        pgprice=(EditText)view.findViewById(R.id.pricepg);
        pgsecurity=(EditText)view.findViewById(R.id.securitypg);
        avail=(EditText)view.findViewById(R.id.avail);
        occupancy=(EditText)view.findViewById(R.id.occupancy);
        otherservices=(EditText)view.findViewById(R.id.othservices);
        food=(CheckBox)view.findViewById(R.id.foodchk);
        ac=(CheckBox)view.findViewById(R.id.acchk);
        fridge=(CheckBox)view.findViewById(R.id.fdgchk);
        cooler=(CheckBox)view.findViewById(R.id.clrchk);
        mstat.add(0,"Yes");
        mstat.add(1,"No");
        ArrayAdapter<String> adapternew=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,mstat);
        mstatspin.setAdapter(adapternew);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
        String check=sharedPreferences.getString("LoggedIn","null");
        if(check.equals("Yes")) {
            oid_val=sharedPreferences.getInt("Id",0);
        }
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        addbtnclick=(Button)view.findViewById(R.id.addbtnclick);
        subbtnclick=(Button)view.findViewById(R.id.subbtnclick);

        addbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber=totalrooms.getText().toString();
                int a=Integer.parseInt(roomNumber);
                a++;
                totalrooms.setText(Integer.toString(a));
            }
        });
        subbtnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomNumber=totalrooms.getText().toString();
                int a=Integer.parseInt(roomNumber);
                if(a>1)
                {
                    a--;
                    totalrooms.setText(Integer.toString(a));
                }
            }
        });

        findPgId();
        findroomid();
        insertClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    response="";
                    line="";
                    sb.setLength(0);
                    data="";
                    creatCon();
                    int id= rdggender.getCheckedRadioButtonId();
                    View radioButton = rdggender.findViewById(id);
                    int radioId = rdggender.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) rdggender.getChildAt(radioId);
                    String genderselec = (String) btn.getText();
                    String mstatselec=mstatspin.getSelectedItem().toString();
                    String fullage=age1.getText().toString();

                    outputStream = urlConnection.getOutputStream();
                    streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    bufferedWriter = new BufferedWriter(streamWriter);
                    String data = URLEncoder.encode("oidtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(oid_val), "UTF-8") + "&" +
                            URLEncoder.encode("pgidtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(getpgid), "UTF-8") + "&" +
                            URLEncoder.encode("nametxt", "UTF-8") + "=" + URLEncoder.encode(pgname.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("agetxt", "UTF-8") + "=" + URLEncoder.encode(fullage, "UTF-8") + "&" +
                            URLEncoder.encode("gendertxt", "UTF-8") + "=" + URLEncoder.encode(genderselec, "UTF-8") + "&" +
                            URLEncoder.encode("contacttxt", "UTF-8") + "=" + URLEncoder.encode(contactpg.getText().toString(), "UTF-8") + "&"+
                            URLEncoder.encode("addresstxt", "UTF-8") + "=" + URLEncoder.encode(addresspg.getText().toString(), "UTF-8")+"&"+
                            URLEncoder.encode("mstatustxt", "UTF-8") + "=" + URLEncoder.encode(mstatselec, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    Log.e("Data Pg Inst",data);
                    if (reader != null) {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                            data = sb.toString();
                        }
                        response = data.toString();
                        Log.e("Response Pg Inst",response);

                    }
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    insrtPGEdit();
                    if (code.equals("1") && newcode.equals("1")) {
                        Toast.makeText(getContext(), "Insert Successfull!!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(getContext(), "Insert Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e)
                {
                    Log.e("Exceptiin: ",e.toString());
                }

            }
        });


        return view;
    }


    public void creatCon() throws Exception
    {
        URL url=new URL("http://10.0.2.2/PGdatabase/pgadd.php");
        urlConnection=(HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
    }


    public void findPgId()
    {
        try
        {
            response="";
            line="";
            sb.setLength(0);
            data="";
            URL url=new URL("http://10.0.2.2/PGdatabase/getpgid.php");
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(streamWriter);
            String data = URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            Log.e("Data",data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    data = sb.toString();
                }
                response = data.toString();
            }
            Log.e("Response",response);
            getpgid=Integer.parseInt(response.trim());
            getpgid++;
            pgid.setText("Pg ID: "+Integer.toString(getpgid));
        }
        catch (Exception e)
        {
            Log.e("Exception: ",e.toString());
        }
    }

    public void findroomid()
    {
        try
        {
            response="";
            line="";
            sb.setLength(0);
            data="";
            URL url=new URL("http://10.0.2.2/PGdatabase/getroomid.php");
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            outputStream = urlConnection.getOutputStream();
            streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(streamWriter);
            String data = URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            Log.e("Data",data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    data = sb.toString();
                }
                response = data.toString();
            }
            Log.e("Response",response);
            getroomnew=Integer.parseInt(response.trim());
            getroomnew++;
            pgedroomid.setText("Room ID: "+Integer.toString(getroomnew));
        }
        catch (Exception e)
        {
            Log.e("Exception: ",e.toString());
        }
    }

    public void insrtPGEdit()
    {
        int foodch=0,acch=0,coolerch=0,fridgch=0;
        try
        {
            URL url=new URL("http://10.0.2.2/PGdatabase/pgedit.php");
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            response="";
            line="";
            sb.setLength(0);
            data="";
            if(food.isChecked())
            {
                foodch=1;
            }
            if(ac.isChecked())
            {
                acch=1;
            }
            if(cooler.isChecked())
            {
                coolerch=1;
            }
            if(fridge.isChecked())
            {
                fridgch=1;
            }

            outputStream = urlConnection.getOutputStream();
            streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            bufferedWriter = new BufferedWriter(streamWriter);
            String data = URLEncoder.encode("roomidtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(getroomnew), "UTF-8") + "&" +
                    URLEncoder.encode("roomtypetxt", "UTF-8") + "=" + URLEncoder.encode(rmtype.getSelectedItem().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("pricetxt", "UTF-8") + "=" + URLEncoder.encode(pgprice.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("securitytxt", "UTF-8") + "=" + URLEncoder.encode(pgsecurity.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("totalroomstxt", "UTF-8") + "=" + URLEncoder.encode(totalrooms.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("availabletxt", "UTF-8") + "=" + URLEncoder.encode(avail.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("occupancytxt", "UTF-8") + "=" + URLEncoder.encode(occupancy.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("foodtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(foodch), "UTF-8") + "&" +
                    URLEncoder.encode("actxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(acch), "UTF-8") + "&" +
                    URLEncoder.encode("fridgetxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(fridgch), "UTF-8") + "&" +
                    URLEncoder.encode("coolertxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(coolerch), "UTF-8") + "&" +
                    URLEncoder.encode("otherservicestxt", "UTF-8") + "=" + URLEncoder.encode(otherservices.getText().toString(), "UTF-8") + "&" +
                    URLEncoder.encode("pgidtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(getpgid), "UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            Log.e("Data Edit Pg Inst",data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    data = sb.toString();
                }
                response = data.toString();
                Log.e("Response Edit Pg Inst",response);
            }
            JSONObject jsonObject = new JSONObject(response);
            newcode = jsonObject.getString("code");
            if(newcode.equals("1"))
            {
                Log.d("pginsrt","Yes");
            }

        }catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }

    }
}
