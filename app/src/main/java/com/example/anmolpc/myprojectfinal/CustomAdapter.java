package com.example.anmolpc.myprojectfinal;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

/**
 * Created by Anmol Pc on 9/23/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    String[] address;
    String[] name;
    String[] id;
    String check;
    Context context;

    BufferedWriter bufferedWriter;
    OutputStreamWriter streamWriter;
    OutputStream outputStream;
    HttpURLConnection urlConnection;
    String line="";
    String data="",uid;
    StringBuilder sb=new StringBuilder();
    String response;

    public CustomAdapter(String uid,String check,Context context, String address[], String name[], String id[])
    {
        this.context=context;
        this.address=address;
        this.name=name;
        this.id=id;
        this.check=check;
        this.uid=uid;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView pgname,pgid,pgaddress;
        public ImageView imgv;
        public CardView cdv;
        public ImageButton fav;
        public ViewHolder(View itemView) {
            super(itemView);
            pgname=(TextView)itemView.findViewById(R.id.npg_name);
            pgid=(TextView)itemView.findViewById(R.id.npg_id);
            pgaddress=(TextView)itemView.findViewById(R.id.npg_address);
            imgv=(ImageView)itemView.findViewById(R.id.person_photo);
            cdv=(CardView)itemView.findViewById(R.id.cv);
            fav=(ImageButton)itemView.findViewById(R.id.favorite);
        }
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_new_layout, parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.ViewHolder holder, final int position) {
        holder.pgid.setText(id[position]+": ");
        holder.pgname.setText(name[position]);
        holder.pgaddress.setText(address[position]);
        Log.e("Check",check);
        if(check.equals("favlist"))
        {
            holder.fav.setPressed(true);
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(check.equals("favlist"))
                    {
                        v.setPressed(false);
                        deleteVal(id[position],uid);
                    }
                    if(check.equals("searchlist"))
                    {
                        holder.fav.setPressed(true);
                        insertVal(id[position],uid);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public void deleteVal(String id,String uid)
    {
        try
        {
            URL url=new URL("http://10.0.2.2/PGdatabase/mypgdel.php");
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
            String data = URLEncoder.encode("pgid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                    URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8");
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
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.getString("code");
            if (code.equals("1")) {
                Toast.makeText(context, "Deleted From Favourite List", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }
    }

    public void insertVal(String id,String uid)
    {
        try
        {
            URL url=new URL("http://10.0.2.2/PGdatabase/mypgadd.php");
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
            String data = URLEncoder.encode("pgid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                    URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8");
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
            JSONObject jsonObject = new JSONObject(response);
            String code = jsonObject.getString("code");
            if (code.equals("1")) {
                Toast.makeText(context, "Inserted Into Favourite List", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Log.e("Exception",e.toString());
        }
    }
}
