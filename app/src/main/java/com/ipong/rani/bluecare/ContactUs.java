package com.ipong.rani.bluecare;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_contact_us );

        navigationView = (NavigationView) findViewById( R.id.navigationMenu2 );
        drawerLayout = (DrawerLayout) findViewById( R.id.drawable_layout2 );


        /* Action bar*/
        mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar2 );
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle( "Contact Us" );


        actionBarDrawerToggle = new ActionBarDrawerToggle( ContactUs.this, drawerLayout, R.string.drawer_open, R.string.drawer_close );

        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        View navView = navigationView.inflateHeaderView( R.layout.navigation_header );

        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector( item );

                return false;
            }
        } );

        /* END */




    }


    /*Menu bar*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected( item )){
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void UserMenuSelector(MenuItem item) {
        int selectedId = item.getItemId();
        RelativeLayout mainLayout = (RelativeLayout) findViewById( R.id.menuRight );
        switch (selectedId){

            case R.id.nav_home:
                Toast.makeText( this, "Patient Records", Toast.LENGTH_SHORT ).show();
                mainLayout.setBackgroundColor(Color.rgb( 111, 255, 33 ) );
                break;

            case R.id.contactUs:
                Toast.makeText( ContactUs.this, "Contact Us Activity", Toast.LENGTH_SHORT ).show();
                mainLayout.setBackgroundColor(Color.rgb( 66, 22, 22 ) );
                startActivity(new Intent(ContactUs.this, AboutUs.class));

                break;

            case R.id.aboutUs:
                Toast.makeText( this, "About Us Activity", Toast.LENGTH_SHORT ).show();
            //    mainLayout.setBackgroundColor(Color.rgb( 255, 11, 44 ) );
                startActivity(new Intent(ContactUs.this, AboutUs.class));

                break;

            case R.id.btnLogout:
                Toast.makeText( this, "Log out", Toast.LENGTH_SHORT ).show();

                break;

        }
    }

    /*END*/







}
