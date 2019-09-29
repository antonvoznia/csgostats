package com.cti.statscsgo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cti.statscsgo.fragments.GunStats;
import com.cti.statscsgo.fragments.LastMatch;
import com.cti.statscsgo.fragments.MapStats;
import com.cti.statscsgo.fragments.TotalStats;
import com.cti.statscsgo.update.Update;

public class StatsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean dualPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, new TotalStats());
        ft.commit();

        Toolbar toolBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        /*NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolBar);*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolBar, R.string.drawer_open, R.string.drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
        } else {
            dualPane = true;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //update.startUpdate();
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Update update = new Update();
            update.startUpdate(this);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction();
        switch (id) {
            case R.id.menu_total_stats:
                fm.beginTransaction().replace(R.id.content_frame, new TotalStats()).
                        setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
                break;
            case R.id.menu_weapon_stats:
                fm.beginTransaction().replace(R.id.content_frame, new GunStats()).
                        setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
                break;
            case R.id.menu_maps_stats:
                fm.beginTransaction().replace(R.id.content_frame, new MapStats()).
                        setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
                break;
            case R.id.menu_last_match:
                fm.beginTransaction().replace(R.id.content_frame, new LastMatch()).
                setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).commit();
                break;
        }
        if (!dualPane) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
