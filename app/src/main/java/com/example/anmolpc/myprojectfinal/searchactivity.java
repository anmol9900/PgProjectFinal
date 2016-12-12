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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.ArrayList;
import java.util.List;

public class searchactivity extends Fragment {

    Spinner spn_roomtype;
    List<String> lst_roomtype=new ArrayList<String>();
    EditText searchpg,search_price;
    CheckBox food_chk,ac_chk,cooler_chk,fridge_chk;
    RecyclerView rcy_view_lst;
    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    String[] address,name,id,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
    int loop,oid_val;
    Button btnsearch;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_searchactivity, container, false);
        spn_roomtype=(Spinner)view.findViewById(R.id.search_roomtype);
        lst_roomtype.add(0,"Normal");
        lst_roomtype.add(1,"Deluxe");
        lst_roomtype.add(2,"None");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,lst_roomtype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_roomtype.setAdapter(adapter);
        spn_roomtype.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        spn_roomtype.setSelection(2);
        searchpg=(EditText)view.findViewById(R.id.searchpg);
        search_price=(EditText)view.findViewById(R.id.search_price);
        food_chk=(CheckBox)view.findViewById(R.id.checkbox_food);
        ac_chk=(CheckBox)view.findViewById(R.id.checkbox_ac);
        cooler_chk=(CheckBox)view.findViewById(R.id.checkbox_colr);
        fridge_chk=(CheckBox)view.findViewById(R.id.checkbox_frdg);
        rcy_view_lst=(RecyclerView)view.findViewById(R.id.id_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rcy_view_lst.setLayoutManager(llm);
        btnsearch=(Button)view.findViewById(R.id.btnsearch);
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
        String check=sharedPreferences.getString("LoggedIn","null");
        if(check.equals("Yes")) {
            oid_val=sharedPreferences.getInt("Id",1);
        }
        rcy_view_lst.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        CustomDialogClass cdd=new CustomDialogClass(getContext(),address[position],name[position],contact[position],age[position],mstatus[position],gender[position],roomtype[position],totalroom[position],avail[position],occupancy[position],price[position],security[position],services[position],extraservices[position]);
                        cdd.show();
                        }
                    }
                })
        );


        food_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    ac_chk.setChecked(false);
                    cooler_chk.setChecked(false);
                    fridge_chk.setChecked(false);
                }
            }
        });

        ac_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    food_chk.setChecked(false);
                    cooler_chk.setChecked(false);
                    fridge_chk.setChecked(false);
                }
            }
        });

        cooler_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    food_chk.setChecked(false);
                    ac_chk.setChecked(false);
                    fridge_chk.setChecked(false);
                }
            }
        });

        fridge_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    food_chk.setChecked(false);
                    ac_chk.setChecked(false);
                    cooler_chk.setChecked(false);
                }
            }
        });

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String str_foodchk="0",str_colrchk="0",str_acchk="0",str_frdgchk="0",strprice="0",chk_ischecked="0";
                    if(food_chk.isChecked())
                    {
                        str_foodchk="1";
                        chk_ischecked="1";
                    }
                    if(ac_chk.isChecked())
                    {
                        str_acchk="1";
                        chk_ischecked="1";
                    }
                    if(cooler_chk.isChecked())
                    {
                        str_colrchk="1";
                        chk_ischecked="1";
                    }
                    if(fridge_chk.isChecked())
                    {
                        str_frdgchk="1";
                        chk_ischecked="1";
                    }
                    if(!search_price.getText().toString().equals(""))
                    {
                        strprice=search_price.getText().toString();
                    }
                    response="";
                    line="";
                    sb.setLength(0);
                    data="";
                    creatCon();
                    outputStream = urlConnection.getOutputStream();
                    streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    bufferedWriter = new BufferedWriter(streamWriter);
                    String data = URLEncoder.encode("roomtypetxt", "UTF-8") + "=" + URLEncoder.encode(spn_roomtype.getSelectedItem().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("searchpgtxt", "UTF-8") + "=" + URLEncoder.encode(searchpg.getText().toString(), "UTF-8") + "&" +
                            URLEncoder.encode("searchpricetxt", "UTF-8") + "=" + URLEncoder.encode(strprice, "UTF-8") + "&" +
                            URLEncoder.encode("strfoodtxt", "UTF-8") + "=" + URLEncoder.encode(str_foodchk, "UTF-8") + "&" +
                            URLEncoder.encode("stractxt", "UTF-8") + "=" + URLEncoder.encode(str_acchk, "UTF-8") + "&" +
                            URLEncoder.encode("strcoolertxt", "UTF-8") + "=" + URLEncoder.encode(str_colrchk, "UTF-8") + "&" +
                            URLEncoder.encode("chkischecked", "UTF-8") + "=" + URLEncoder.encode(chk_ischecked, "UTF-8") + "&" +
                            URLEncoder.encode("strfrdgtxt", "UTF-8") + "=" + URLEncoder.encode(str_frdgchk, "UTF-8");

                    Log.e("Data",data);
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
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
                        if(jo.getString("food").equals("1"))
                        {
                                services[i] = "Food";
                        }
                        if(jo.getString("ac").equals("1"))
                        {
                            if(services[i]!=null) {
                                services[i] += ", Air Conditioner";
                            }
                            else
                            {
                                services[i] += "Air Conditioner";
                            }
                        }
                        if(jo.getString("fridg").equals("1"))
                        {
                            if(services[i]!=null) {
                                services[i] += ", Fridge";
                            }
                            else
                            {
                                services[i] += "Fridge";
                            }
                        }
                        if(jo.getString("cooler").equals("1"))
                        {
                            if(services[i]!=null) {
                                services[i] += ", Cooler";
                            }
                            else
                            {
                                services[i] += "Cooler";
                            }
                        }
                    }
                    CustomAdapter adapter1=new CustomAdapter(Integer.toString(oid_val),"searchlist",getContext(),address,name,id);
                    rcy_view_lst.setAdapter(adapter1);
                }
                catch (Exception e)
                {
                    Log.e("Exception",e.toString());
                }
            }
        });


        return view;
    }

    public void creatCon() throws Exception
    {
        URL url=new URL("http://10.0.2.2/PGdatabase/pgsearch.php");
        urlConnection=(HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
    }
}
