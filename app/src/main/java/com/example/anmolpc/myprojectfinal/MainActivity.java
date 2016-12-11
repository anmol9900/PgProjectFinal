package com.example.anmolpc.myprojectfinal;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navview=navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String type=bundle.getString("type");
        Toast.makeText(this,email+" "+type,Toast.LENGTH_LONG).show();
        final TextView emailtxt=(TextView)navview.findViewById(R.id.emailtxt);
        final TextView typetxt=(TextView)navview.findViewById(R.id.typetxt);
        emailtxt.setText(email);
        typetxt.setText(type);
        if(type.equals("user"))
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.usermenu);
            Fragment newFragment = new HomeUser();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(type.equals("owner"))
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.ownermenu);
            Fragment newFragment = new HomeOwner();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Fragment newFragment = new searchactivity();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_Logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginCheck", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("LoggedIn","No");
            editor.commit();
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_Pgadd) {
            Fragment newFragment = new AddPG();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_home) {
            Fragment newFragment = new HomeOwner();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_userhome) {
            Fragment newFragment = new HomeUser();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_Contactus) {
            Fragment newFragment = new ContactUs();
            ViewGroup mContainer = (ViewGroup)findViewById(R.id.fragment1);
            mContainer.removeAllViews();
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment1, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}