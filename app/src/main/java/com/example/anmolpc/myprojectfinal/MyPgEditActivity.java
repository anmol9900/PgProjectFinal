package com.example.anmolpc.myprojectfinal;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.List;

public class MyPgEditActivity extends AppCompatActivity {
    List<String> mstat=new ArrayList<String>();
    List<String> typelist=new ArrayList<String>();
    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    String roomnumber;
    String name,address,id,roomid,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
    Context context;
    TextView text_pgid;
    EditText myothrservice,myname,myaddress,mycontact,myage,myprice,mysecurity,myttlroom,myavail,myoccupancy;
    RadioGroup rdg;
    RadioButton rdb1,rdb2;
    Spinner mstatusspn,rmtype;
    Button mysubbtn,myaddbtn,myupdateclick;
    CheckBox myfood,myac,myfrdg,mycooler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pg_edit);

        //Incoming Data From Bundle
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        address=bundle.getString("address");
        contact=bundle.getString("contact");
        age=bundle.getString("age");
        id=bundle.getString("id");
        roomid=bundle.getString("roomid");
        mstatus=bundle.getString("mstatus");
        gender=bundle.getString("gender");
        roomtype=bundle.getString("roomtype");
        totalroom=bundle.getString("totalroom");
        avail=bundle.getString("avail");
        occupancy=bundle.getString("occupancy");
        price=bundle.getString("price");
        security=bundle.getString("security");
        services=bundle.getString("services");
        extraservices=bundle.getString("extraservices");


        //Initializing All Activity Components
        myothrservice=(EditText) findViewById(R.id.myothservices);
        myname=(EditText)findViewById(R.id.mynamepg);
        myaddress=(EditText) findViewById(R.id.myaddresspg);
        mycontact=(EditText)findViewById(R.id.mycontactpg);
        myage=(EditText)findViewById(R.id.myage);
        myprice=(EditText) findViewById(R.id.mypricepg);
        mysecurity=(EditText) findViewById(R.id.mysecuritypg);
        myttlroom=(EditText) findViewById(R.id.myttlrooms);
        myavail=(EditText) findViewById(R.id.myavail);
        myoccupancy=(EditText) findViewById(R.id.myoccupancy);
        text_pgid=(TextView) findViewById(R.id.edit_pg_pgid);
        rdg=(RadioGroup) findViewById(R.id.mygenderpg);
        rdb1=(RadioButton) findViewById(R.id.myrb1);
        rdb2=(RadioButton) findViewById(R.id.myrb2);
        mstatusspn=(Spinner) findViewById(R.id.mymstatuspg);
        rmtype=(Spinner) findViewById(R.id.myrmtype);
        mysubbtn=(Button) findViewById(R.id.mysubbtnclick);
        myaddbtn=(Button) findViewById(R.id.myaddbtnclick);
        myupdateclick=(Button) findViewById(R.id.myupdateclick);
        myfood=(CheckBox) findViewById(R.id.myfoodchk);
        myac=(CheckBox) findViewById(R.id.myacchk);
        myfrdg=(CheckBox) findViewById(R.id.myfdgchk);
        mycooler=(CheckBox) findViewById(R.id.myclrchk);

        myothrservice.setText(extraservices);
        myname.setText(name);
        myaddress.setText(address);
        mycontact.setText(contact);
        myage.setText(price);
        mysecurity.setText(security);
        myttlroom.setText(totalroom);
        myavail.setText(avail);
        myoccupancy.setText(occupancy);
        text_pgid.setText("Edit PG: "+id);
        typelist.add(0,"Normal");
        typelist.add(1,"Deluxe");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MyPgEditActivity.this,android.R.layout.simple_spinner_item,typelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rmtype.setAdapter(adapter);
        mstat.add(0,"Yes");
        mstat.add(1,"No");
        ArrayAdapter<String> adapternew=new ArrayAdapter<String>(MyPgEditActivity.this,android.R.layout.simple_spinner_item,mstat);
        mstatusspn.setAdapter(adapternew);
        if(mstatus.equals("yes"))
        {
            mstatusspn.setSelection(0);
        }
        else {
            mstatusspn.setSelection(1);
        }
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        myaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomnumber=myttlroom.getText().toString();
                int a=Integer.parseInt(roomnumber);
                a++;
                myttlroom.setText(Integer.toString(a));
            }
        });
        mysubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomnumber=myttlroom.getText().toString();
                int a=Integer.parseInt(roomnumber);
                if(a>1)
                {
                    a--;
                    myttlroom.setText(Integer.toString(a));
                }
            }
        });

        Log.e("Services",services);
        List<String> serviceList = Arrays.asList(services.split(","));
        for (String s:serviceList
             ) {
            if(s.indexOf("Food") != -1)
            {
                myfood.setChecked(true);
            }
            if(s.indexOf("Air Conditioner") != -1)
            {
                myac.setChecked(true);
            }
            if(s.indexOf("Fridge") != -1)
            {
                myfrdg.setChecked(true);
            }
            if(s.indexOf("Cooler") != -1)
            {
                mycooler.setChecked(true);
            }
        }

        if(gender.equals("Male"))
        {
            rdb1.setChecked(true);
        }
        else if(gender.equals("Female"))
        {
            rdb2.setChecked(true);
        }

        myupdateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int foodch=0,acch=0,coolerch=0,fridgch=0;
                if(myfood.isChecked())
                {
                    foodch=1;
                }
                if(myac.isChecked())
                {
                    acch=1;
                }
                if(mycooler.isChecked())
                {
                    coolerch=1;
                }
                if(myfrdg.isChecked())
                {
                    fridgch=1;
                }
                int id= rdg.getCheckedRadioButtonId();
                View radioButton = rdg.findViewById(id);
                int radioId = rdg.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) rdg.getChildAt(radioId);
                String genderselec = (String) btn.getText();
                String mstatselec=mstatusspn.getSelectedItem().toString();

                try
                {
                    URL url=new URL("http://10.0.2.2/PGdatabase/updatepg.php");
                    urlConnection=(HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                    response="";
                    line="";
                    sb.setLength(0);
                    data="";
                    outputStream = urlConnection.getOutputStream();
                    streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    bufferedWriter = new BufferedWriter(streamWriter);
                    String data = URLEncoder.encode("pgidtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(id), "UTF-8") + "&" +
                        URLEncoder.encode("pgnametxt", "UTF-8") + "=" + URLEncoder.encode(myname.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("agetxt", "UTF-8") + "=" + URLEncoder.encode(myage.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("gendertxt", "UTF-8") + "=" + URLEncoder.encode(genderselec, "UTF-8") + "&" +
                        URLEncoder.encode("pgcontacttxt", "UTF-8") + "=" + URLEncoder.encode(mycontact.getText().toString(), "UTF-8") + "&"+
                        URLEncoder.encode("pgaddresstxt", "UTF-8") + "=" + URLEncoder.encode(myaddress.getText().toString(), "UTF-8")+"&"+
                        URLEncoder.encode("mstatustxt", "UTF-8") + "=" + URLEncoder.encode(mstatselec, "UTF-8") + "&" +
                            URLEncoder.encode("roomidtxt", "UTF-8") + "=" + URLEncoder.encode(roomid, "UTF-8") + "&" +
                            URLEncoder.encode("roomtypetxt", "UTF-8") + "=" + URLEncoder.encode(rmtype.getSelectedItem().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("pricetxt", "UTF-8") + "=" + URLEncoder.encode(myprice.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("securitytxt", "UTF-8") + "=" + URLEncoder.encode(mysecurity.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("roomstxt", "UTF-8") + "=" + URLEncoder.encode(myttlroom.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("availtxt", "UTF-8") + "=" + URLEncoder.encode(myavail.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("occupancytxt", "UTF-8") + "=" + URLEncoder.encode(myoccupancy.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("foodtxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(foodch), "UTF-8") + "&" +
                            URLEncoder.encode("actxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(acch), "UTF-8") + "&" +
                            URLEncoder.encode("fridgetxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(fridgch), "UTF-8") + "&" +
                            URLEncoder.encode("coolertxt", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(coolerch), "UTF-8") + "&" +
                            URLEncoder.encode("servicestxt", "UTF-8") + "=" + URLEncoder.encode(myothrservice.getText().toString(), "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    Log.e("Data Pg Update",data);
                    if (reader != null) {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                            data = sb.toString();
                        }
                        response = data.toString();
                        Log.e("Response Pg Update",response);

                    }
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    if (code.equals("1")) {
                        Toast.makeText(context, "Insert Successfull!!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(context, "Insert Failed!!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Log.e("Exception",e.toString());
                }
            }
        });
    }
}
