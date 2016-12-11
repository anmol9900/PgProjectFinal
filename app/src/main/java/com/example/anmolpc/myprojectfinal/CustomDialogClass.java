package com.example.anmolpc.myprojectfinal;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anmol Pc on 11/14/2016.
 */

public class CustomDialogClass extends Dialog {
    String address,name,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
    Context context;
    public CustomDialogClass(Context context) {
        super(context);
    }

    public CustomDialogClass(Context context,String address,String name,String contact,String age,String mstatus,String gender,String roomtype,String totalroom,String avail,String occupancy,String price,String security,String services,String extraservices)
    {
        super(context);
        this.context=context;
        this.address=address;
        this.name=name;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        TextView tvname=(TextView)findViewById(R.id.name_dia);
        TextView tvaddress=(TextView)findViewById(R.id.address_dia);
        TextView tvpgcontact=(TextView)findViewById(R.id.pgcontact_dia);
        TextView tvage=(TextView)findViewById(R.id.age_dia);
        TextView tvmstatus=(TextView)findViewById(R.id.mstatus_dia);
        TextView tvgender=(TextView)findViewById(R.id.gender_dia);
        TextView tvroomtype=(TextView)findViewById(R.id.rmtype_dia);
        TextView tvtotalroom=(TextView)findViewById(R.id.troom_dia);
        TextView tvavail=(TextView)findViewById(R.id.avail_dia);
        TextView tvoccupancy=(TextView)findViewById(R.id.occu_dia);
        TextView tvprice=(TextView)findViewById(R.id.price_dia);
        TextView tvsecurity=(TextView)findViewById(R.id.security_dia);
        TextView tvservices=(TextView)findViewById(R.id.services_dia);
        TextView tvextservices=(TextView)findViewById(R.id.extservices_dia);
        Button locbtn=(Button)findViewById(R.id.location_btn);
        Button routebtn=(Button)findViewById(R.id.Route_btn);

        tvname.setText(name);
        tvaddress.setText(address);
        tvpgcontact.setText(contact);
        tvage.setText(age);
        tvmstatus.setText(mstatus);
        tvgender.setText(gender);
        tvroomtype.setText(roomtype);
        tvtotalroom.setText(totalroom);
        tvavail.setText(avail);
        tvoccupancy.setText(occupancy);
        tvprice.setText(price);
        tvsecurity.setText(security);
        tvservices.setText(services);
        tvextservices.setText(extraservices);

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MapsActivity.class);
                intent.putExtra("type","location");
                intent.putExtra("address",address);
                context.startActivity(intent);
            }
        });
        routebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MapsActivity.class);
                intent.putExtra("type","route");
                intent.putExtra("address",address);
                context.startActivity(intent);
            }
        });
    }
}
