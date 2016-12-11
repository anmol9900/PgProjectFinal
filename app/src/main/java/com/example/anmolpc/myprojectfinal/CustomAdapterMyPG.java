package com.example.anmolpc.myprojectfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anmol Pc on 11/26/2016.
 */

public class CustomAdapterMyPG extends RecyclerView.Adapter<CustomAdapterMyPG.ViewHolder> {

        String[] address;
        String[] name;
        String[] id,roomid,contact,age,mstatus,gender,roomtype,totalroom,avail,occupancy,price,security,services,extraservices;
        Context context;

public CustomAdapterMyPG(Context context,String address[],String name[],String contact[],String age[],String mstatus[],String gender[],String roomtype[],String totalroom[],String avail[],String occupancy[],String price[],String security[],String services[],String extraservices[],String roomid[],String id[])
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

public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView pgname,pgid,pgaddress;
    public ImageView imgv;
    public CardView cdv;
    public Button edit_btn;
    public ImageButton img_btn;
    public String check="1";
    public ViewHolder(View itemView) {
        super(itemView);
        pgname=(TextView)itemView.findViewById(R.id.npg_name);
        pgid=(TextView)itemView.findViewById(R.id.npg_id);
        pgaddress=(TextView)itemView.findViewById(R.id.npg_address);
        imgv=(ImageView)itemView.findViewById(R.id.person_photo);
        cdv=(CardView)itemView.findViewById(R.id.cv);
        img_btn=(ImageButton)itemView.findViewById(R.id.img_button);
        edit_btn=(Button)itemView.findViewById(R.id.edit_button);
    }
}

    @Override
    public CustomAdapterMyPG.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent,false);
        CustomAdapterMyPG.ViewHolder vh=new CustomAdapterMyPG.ViewHolder(v);
        return vh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final CustomAdapterMyPG.ViewHolder holder, final int position) {
        holder.pgid.setText(id[position]+": ");
        holder.pgname.setText(name[position]);
        holder.pgaddress.setText(address[position]);

        if(avail[position].equals("yes"))
        {
            holder.img_btn.setBackgroundColor(Color.GREEN);
        }
        else
        {
            holder.img_btn.setBackgroundColor(Color.RED);
        }

        //Update All Data
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MyPgEditActivity.class);
                Bundle bd=new Bundle();
                bd.putString("name",name[position]);
                bd.putString("address",address[position]);
                bd.putString("contact",contact[position]);
                bd.putString("age",age[position]);
                bd.putString("mstatus",mstatus[position]);
                bd.putString("gender",gender[position]);
                bd.putString("roomtype",roomtype[position]);
                bd.putString("totalroom",totalroom[position]);
                bd.putString("avail",avail[position]);
                bd.putString("occupancy",occupancy[position]);
                bd.putString("price",price[position]);
                bd.putString("security",security[position]);
                bd.putString("services",services[position]);
                bd.putString("extraservices",extraservices[position]);
                bd.putString("roomid",roomid[position]);
                bd.putString("id",id[position]);
                i.putExtras(bd);
                context.startActivity(i);
            }
        });


        //Update Available
        holder.img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.check.equals("0"))
                {
                    holder.img_btn.setBackgroundColor(Color.RED);
                    holder.check="1";
                    UpdateAvail ua=new UpdateAvail(context,address[position],name[position],contact[position],age[position],mstatus[position],gender[position],roomtype[position],totalroom[position],"no",occupancy[position],price[position],security[position],services[position],extraservices[position],roomid[position],id[position]);
                    ua.update();
                }
                else if(holder.check.equals("1"))
                {
                    holder.img_btn.setBackgroundColor(Color.GREEN);
                    holder.check="0";
                    UpdateAvail ua=new UpdateAvail(context,address[position],name[position],contact[position],age[position],mstatus[position],gender[position],roomtype[position],totalroom[position],"yes",occupancy[position],price[position],security[position],services[position],extraservices[position],roomid[position],id[position]);
                    ua.update();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return name.length;
    }
}
