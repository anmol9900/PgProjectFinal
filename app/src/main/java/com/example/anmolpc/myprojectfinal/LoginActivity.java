package com.example.anmolpc.myprojectfinal;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    Button login,reset,forgotPass,register;
    String line,Email,pass,response;
    HttpURLConnection urlConnection;
    OutputStream outputStream;
    OutputStreamWriter outputStreamWriter;
    BufferedWriter bufferedWriter;
    int id,code;
    String type;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
        String check=sharedPreferences.getString("LoggedIn","null");
        if(check.equals("Yes"))
        {
            Log.e("Pointer","inCheck");
            String sendtype=sharedPreferences.getString("type","user");
            String senuser=sharedPreferences.getString("User",null);
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            Bundle bd=new Bundle();
            bd.putString("email",senuser);
            bd.putString("type",sendtype);
            i.putExtras(bd);
            startActivity(i);
        }

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission_group.STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission_group.STORAGE}, 99);
        }
        email = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.pass);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        reset = (Button)findViewById(R.id.reset);
        forgotPass = (Button)findViewById(R.id.forgot);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Login(View v){
        try{
            Email = email.getText().toString();
            pass = password.getText().toString();

            if(Email.equals(null) || pass.equals(null)){
                Toast.makeText(getApplicationContext(),"Fill Both Fields",Toast.LENGTH_SHORT).show();
            }else{
                Login();

                if(code==1) {
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("User", Email);
                    editor.putString("LoggedIn","Yes");
                    editor.putInt("Id",id);
                    editor.putString("type",type);
                    editor.commit();
                    File myPath = new File(Environment.getExternalStorageDirectory().toString());
                    File myFile = new File(myPath, "MySharedPreferences");

                    try
                    {
                        FileWriter fw = new FileWriter(myFile);
                        PrintWriter pw = new PrintWriter(fw);

                        Map<String,?> prefsMap = sharedPreferences.getAll();

                        for(Map.Entry<String,?> entry : prefsMap.entrySet())
                        {
                            pw.println(entry.getKey() + ": " + entry.getValue().toString());
                        }

                        pw.close();
                        fw.close();
                    }
                    catch (Exception e)
                    {
                        // what a terrible failure...
                        Log.wtf(getClass().getName(), e.toString());
                    }

                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bd=new Bundle();
                    bd.putString("email",Email);
                    bd.putString("type",type);
                    i.putExtras(bd);
                    startActivity(i);
                }
                else if(code==0)
                {
                    Toast.makeText(this,"Wrong Username and Password!!",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){

        }

    }

    public void Reset(View v){
        email.setText("");
        password.setText("");
        email.requestFocus();
    }

    public void ForgotPass (View v){
        String Email = email.getText().toString();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Forgot_Pass fp = new Forgot_Pass(this, Email);
            fp.show();
        }
    }

    public void CreatCon(){
        try{
//            URL url = new URL("http://10.0.2.2/PGdatabase/login.php");
            URL url = new URL("http://10.0.2.2/PGdatabase/login.php");
            urlConnection =(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
        }catch (Exception e){

        }
    }

    public void Register(View v){
//        Intent i = new Intent(MainActivity.this, Forgot_Pass.class);
//        startActivity(i);
    }

    public void Login(){
        CreatCon();
        try{
            outputStream = urlConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            String data = URLEncoder.encode("emailtxt","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"+
                    URLEncoder.encode("passtxt","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            if(reader!=null)
            {
                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                    data=sb.toString();
                }
                response=new String(data);

            }
            Log.e("Response:",response);

            JSONObject jsonObject = new JSONObject(response);

            id=jsonObject.getInt("id");
            code=jsonObject.getInt("code");
            type=jsonObject.getString("type");
            Toast.makeText(getApplicationContext(),id+" "+code+" "+type,Toast.LENGTH_LONG).show();

            bufferedWriter.flush();
            bufferedWriter.close();
            Reset(v);

        }catch (Exception e){
            Log.e("Exception: ",e.toString());
        }
    }

}
