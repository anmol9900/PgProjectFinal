package com.example.anmolpc.myprojectfinal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    List<String> chs= new ArrayList<String>();
    List<String> mstatusList=new ArrayList<String>();
    Spinner spn,mstatus;
    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="";
    StringBuilder sb=new StringBuilder();
    String response;
    EditText fname,lname,pass,cnfpass,dob,email,contact,altcontact,address1,address2,city,state;
    RadioGroup gender;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spn=(Spinner)findViewById(R.id.selecttype);
        mstatus=(Spinner)findViewById(R.id.mstatus);
        gender=(RadioGroup)findViewById(R.id.gender);
        dob=(EditText)findViewById(R.id.dob);
        fname=(EditText)findViewById(R.id.fnametxt);
        lname=(EditText)findViewById(R.id.lnametxt);
        pass=(EditText)findViewById(R.id.passtxt);
        cnfpass=(EditText)findViewById(R.id.cnfpasstxt);
        email=(EditText)findViewById(R.id.emailtxt);
        contact=(EditText)findViewById(R.id.contacttxt);
        altcontact=(EditText)findViewById(R.id.altcontacttxt);
        address1=(EditText)findViewById(R.id.address1txt);
        address2=(EditText)findViewById(R.id.address2txt);
        city=(EditText)findViewById(R.id.citytxt);
        state=(EditText)findViewById(R.id.statetxt);
        chs.add(0,"User");
        chs.add(1,"Owner");
        mstatusList.add(0,"Yes");
        mstatusList.add(1,"No");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,chs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mstatusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mstatus.setAdapter(adapter1);
        StrictMode.ThreadPolicy threadPolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this,date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    public void creatCon(String phpfile) throws Exception
    {
        URL url=new URL(phpfile);
        urlConnection=(HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.connect();
    }

    public void onSubmitClick(View v)
    {
        if(pass.getText().toString().equals(cnfpass.getText().toString())) {
            String phpfile="";
            String fullname=fname.getText().toString()+" "+lname.getText().toString();
            int id= gender.getCheckedRadioButtonId();
            View radioButton = gender.findViewById(id);
            int radioId = gender.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) gender.getChildAt(radioId);
            String selection = (String) btn.getText();
            String fulladdress=address1.getText().toString()+" "+address2.getText().toString()+" "+city.getText().toString()+" "+state.getText().toString();
            if (spn.getSelectedItem().toString().equals("User")) {
                phpfile = "http://10.0.2.2/PGdatabase/user.php";
            } else if (spn.getSelectedItem().toString().equals("Owner")) {
                phpfile = "http://10.0.2.2/PGdatabase/pgowner.php";
            }

            try {
                creatCon(phpfile);
                outputStream = urlConnection.getOutputStream();
                streamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                bufferedWriter = new BufferedWriter(streamWriter);
                String data = URLEncoder.encode("nametxt", "UTF-8") + "=" + URLEncoder.encode(fullname, "UTF-8") + "&" +
                        URLEncoder.encode("passtxt", "UTF-8") + "=" + URLEncoder.encode(pass.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("dobtxt", "UTF-8") + "=" + URLEncoder.encode(dob.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("gendertxt", "UTF-8") + "=" + URLEncoder.encode(selection, "UTF-8") + "&" +
                        URLEncoder.encode("contacttxt", "UTF-8") + "=" + URLEncoder.encode(contact.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("altcontacttxt", "UTF-8") + "=" + URLEncoder.encode(altcontact.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("emailtxt", "UTF-8") + "=" + URLEncoder.encode(email.getText().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("mstatustxt", "UTF-8") + "=" + URLEncoder.encode(mstatus.getSelectedItem().toString(), "UTF-8") + "&" +
                        URLEncoder.encode("addresstxt", "UTF-8") + "=" + URLEncoder.encode(fulladdress, "UTF-8") + "&" +
                        URLEncoder.encode("check", "UTF-8") + "=" + URLEncoder.encode("submit", "UTF-8");
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

                }


                JSONObject jsonObject = new JSONObject(response);
                String code = jsonObject.getString("code");
                //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
                if (code.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Insert Successfull!!", Toast.LENGTH_SHORT).show();
                } else if (code.equals("0")) {
                    Toast.makeText(getApplicationContext(), "Insert Failed!!", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e("Exception:", e.toString());
            }
        }
        else
        {
            Toast.makeText(RegisterActivity.this,"Password and Confirm Password does not match!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void onCancelClick(View v)
    {
        Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}
