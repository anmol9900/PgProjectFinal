package com.example.anmolpc.myprojectfinal;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Anmol Pc on 11/25/2016.
 */

//public class CustomDialogEditClass extends Dialog {
//    BufferedWriter bufferedWriter;
//    OutputStreamWriter streamWriter;
//    OutputStream outputStream;
//    HttpURLConnection urlConnection;
//    String line="";
//    String data="";
//    StringBuilder sb=new StringBuilder();
//    String response;
//    String roomid,address,name,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
//    Context context;
//    List<String> lstavail=new ArrayList<String>();
//    Spinner spn_avail;
//    EditText totalroomsedit;
//    Button edit_all,savettlroom,saveavail;
//    public CustomDialogEditClass(Context context) {
//        super(context);
//    }
//
//    public CustomDialogEditClass(Context context,String roomid,String address,String name,String contact,String age,String mstatus,String gender,String roomtype,String totalroom,String avail,String occupancy,String price,String security,String services,String extraservices)
//    {
//        super(context);
//        this.context=context;
//        this.address=address;
//        this.name=name;
//        this.contact=contact;
//        this.age=age;
//        this.mstatus=mstatus;
//        this.gender=gender;
//        this.roomtype=roomtype;
//        this.totalroom=totalroom;
//        this.avail=avail;
//        this.occupancy=occupancy;
//        this.price=price;
//        this.security=security;
//        this.services=services;
//        this.extraservices=extraservices;
//        this.roomid=roomid;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_editdia);
//        lstavail.add(0,"Yes");
//        lstavail.add(1,"No");
//        spn_avail=(Spinner)findViewById(R.id.spn_avail);
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,lstavail);
//        spn_avail.setAdapter(adapter);
//
//       Button addbtnclickedit=(Button) findViewById(R.id.addbtnclickedit);
//        Button subbtnclickedit=(Button) findViewById(R.id.subbtnclickedit);
//        edit_all=(Button) findViewById(R.id.edit_all);
//        savettlroom=(Button) findViewById(R.id.save_ttlroom);
//        saveavail=(Button) findViewById(R.id.save_avail);
//        totalroomsedit=(EditText) findViewById(R.id.ttlroomsedit);
//        totalroomsedit.setText(totalroom);
//
//        if(avail.equals("yes"))
//        {
//            spn_avail.setSelection(0);
//        }
//        else
//        {
//            spn_avail.setSelection(1);
//        }
//
//        addbtnclickedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String roomNumber=totalroomsedit.getText().toString();
//                int a=Integer.parseInt(roomNumber);
//                a++;
//                totalroomsedit.setText(Integer.toString(a));
//            }
//        });
//        subbtnclickedit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               String roomNumber=totalroomsedit.getText().toString();
//                int a=Integer.parseInt(roomNumber);
//                if(a>1)
//                {
//                    a--;
//                    totalroomsedit.setText(Integer.toString(a));
//                }
//            }
//        });
//
//        savettlroom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String updatadata=totalroomsedit.getText().toString();
//                try {
//                    creatCon();
//                    response="";
//                    line="";
//                    sb.setLength(0);
//                    data="";
//                    outputStream = urlConnection.getOutputStream();
//                    streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//                    bufferedWriter = new BufferedWriter(streamWriter);
//                    String data = URLEncoder.encode("roomidtxt", "UTF-8") + "=" + URLEncoder.encode(roomid, "UTF-8") + "&" +
//                            URLEncoder.encode("ttlroomtxt", "UTF-8") + "=" + URLEncoder.encode(updatadata, "UTF-8") + "&" +
//                            URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
//                    bufferedWriter.write(data);
//                    bufferedWriter.flush();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    if (reader != null) {
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                            data = sb.toString();
//                        }
//                        response = data.toString();
//                    }
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.getString("code");
//                    if (code.equals("1")) {
//                        Toast.makeText(getContext(), "Update Successfull!!", Toast.LENGTH_SHORT).show();
//                    } else{
//                        Toast.makeText(getContext(), "Update Failed!!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch (Exception e)
//                {
//                    Log.e("Exception",e.toString());
//                }
//            }
//        });
//
//        saveavail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String updatadata=spn_avail.getSelectedItem().toString();
//                try {
//                    creatCon();
//                    response="";
//                    line="";
//                    sb.setLength(0);
//                    data="";
//                    outputStream = urlConnection.getOutputStream();
//                    streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
//                    bufferedWriter = new BufferedWriter(streamWriter);
//                    String data = URLEncoder.encode("roomidtxt", "UTF-8") + "=" + URLEncoder.encode(roomid, "UTF-8") + "&" +
//                            URLEncoder.encode("updatedata", "UTF-8") + "=" + URLEncoder.encode(updatadata, "UTF-8") + "&" +
//                            URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode("2", "UTF-8");
//                    bufferedWriter.write(data);
//                    bufferedWriter.flush();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    if (reader != null) {
//                        while ((line = reader.readLine()) != null) {
//                            sb.append(line + "\n");
//                            data = sb.toString();
//                        }
//                    }
//                    response = data.toString();
//                    JSONObject jsonObject = new JSONObject(response);
//                    String code = jsonObject.getString("code");
//                    if (code.equals("1")) {
//                        Toast.makeText(getContext(), "Update Successfull!!", Toast.LENGTH_SHORT).show();
//                    } else{
//                        Toast.makeText(getContext(), "Update Failed!!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                catch (Exception e)
//                {
//                    Log.e("Exception",e.toString());
//                }
//            }
//        });
//    }
//
//    public void creatCon() throws Exception
//    {
//        URL url=new URL("http://10.0.2.2/PGdatabase/savedata.php");
//        urlConnection=(HttpURLConnection) url.openConnection();
//        urlConnection.setRequestMethod("POST");
//        urlConnection.setDoOutput(true);
//        urlConnection.connect();
//    }
//}
