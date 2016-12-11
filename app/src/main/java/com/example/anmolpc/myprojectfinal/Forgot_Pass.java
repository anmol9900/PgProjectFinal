package com.example.anmolpc.myprojectfinal;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.R.attr.exitFadeDuration;
import static android.R.attr.type;

public class Forgot_Pass extends Dialog {

    EditText email;
    Button frgtpass;
    String registeredEmail,line,response;
    String id;
    HttpURLConnection urlConnection;
    OutputStream outputStream;
    OutputStreamWriter outputStreamWriter;
    BufferedWriter bufferedWriter;
    Context context;
    String emailid;

    public Forgot_Pass(Context context,String email)
    {
        super(context);
        this.context=context;
        this.emailid=email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpass_box);
        email = (EditText)findViewById(R.id.fp_email);
        frgtpass = (Button)findViewById(R.id.fp_button);
        email.setText(emailid);
        frgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailPass();
            }
        });
    }
    public void CreatCon(){
        try{
//            URL url = new URL("http://10.0.2.2/PGdatabase/retrievePass.php");
            URL url = new URL("http://dipansh.n-kumar.in/PGdatabase/retrievePass.php");
            urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
        }catch (Exception e){

        }
    }
    public void EmailPass() {
        registeredEmail = email.getText().toString();
        if (registeredEmail.equals(null)) {
            Toast.makeText(context, "Enter Email Address", Toast.LENGTH_SHORT).show();
        } else {

            try {
                CreatCon();
                outputStream = urlConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                String data = URLEncoder.encode("emailtxt", "UTF-8") + "=" + URLEncoder.encode(registeredEmail, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                if (reader != null) {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                        data = sb.toString();
                    }
                    response = new String(data);

                }
                Log.e("Response:", response);

                Toast.makeText(context,response, Toast.LENGTH_LONG).show();
                System.exit(0);
            } catch (Exception e) {
                Log.e("Exception: ", e.toString());
            }
        }
    }
}