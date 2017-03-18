package com.fasylgh.fasylgse;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fasylgh.fasylgse.adapters.StorksRecyclerAdapter;
import com.fasylgh.fasylgse.controller.StorksApiClient;
import com.fasylgh.fasylgse.controller.StorksApiInterface;
import com.fasylgh.fasylgse.model.SQLiteDataBase;
import com.fasylgh.fasylgse.model.Stork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fasylgh.fasylgse.model.DBEntries.CHANGE;
import static com.fasylgh.fasylgse.model.DBEntries.NAME;
import static com.fasylgh.fasylgse.model.DBEntries.PRICE;
import static com.fasylgh.fasylgse.model.DBEntries.TABLE_NAME;
import static com.fasylgh.fasylgse.model.DBEntries.VOLUME;

public class StocksActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    StorksRecyclerAdapter adapter;
    FloatingActionButton fab;
    SQLiteDataBase databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new SQLiteDataBase(this);

        recyclerView = (RecyclerView) findViewById(R.id.storks_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        StorksApiInterface storksApiInterface = StorksApiClient.getClient().create(StorksApiInterface.class);
        Call<List<Stork>> call = storksApiInterface.getStorks();
        call.enqueue(new Callback<List<Stork>>() {
            @Override
            public void onResponse(Call<List<Stork>> call, Response<List<Stork>> response) {
                adapter = new StorksRecyclerAdapter(response.body());
                recyclerView.setAdapter(adapter);
                insertToDB(response.body());
            }

            @Override
            public void onFailure(Call<List<Stork>> call, Throwable t) {

            }
        });


//        adapter = new StorksRecyclerAdapter(list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);


        getDataFromDB();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_search_black_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.stocks, menu);
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

        if (id == R.id.nav_storks) {
            // Handle the camera action
            Intent intent = new Intent(this,StocksActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_charts) {
            Intent intent = new Intent(this,ChartsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void insertToDB(List<Stork> list) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        for (Stork stork:list) {
            ContentValues values = new ContentValues();
            values.put(NAME, stork.getName());
            values.put(CHANGE, stork.getChange());
            values.put(PRICE, stork.getPrice());
            values.put(VOLUME , stork.getVolume());

            long newRowId = db.insert(TABLE_NAME, null, values);

        }

    }



    private void getDataFromDB() {
        SQLiteDatabase db = null;

        db = databaseHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Stork dbrow = new Stork();
                dbrow.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                dbrow.setChange(Double.valueOf(cursor.getString(cursor.getColumnIndex(CHANGE))));
                dbrow.setPrice("GHS " + cursor.getString(cursor.getColumnIndex(PRICE)));

                dbrow.setVolume(cursor.getString(cursor.getColumnIndex(VOLUME)));




                cursor.moveToNext();

                Log.i("test",dbrow.toString());


            }
        }
        cursor.close();

    }


}
