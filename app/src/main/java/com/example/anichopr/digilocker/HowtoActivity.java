package com.example.anichopr.digilocker;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class HowtoActivity extends AppCompatActivity {
    String[] itemname ={
            "Passport",
            "Driving License",
            "Pan Card",
            "Voter Id",
    };
    String[] serviceName={
            "Internet Connection",
            "Telephone Connection",
            "Bank Account"
    };
    public void HideActionBarLogo() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Apply for");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.blue));
        getSupportActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        HideActionBarLogo();
        final ListView listview = (ListView) findViewById(R.id.howto_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.how_to_item_layout, R.id.textView, itemname);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                 Intent searchIntent = new Intent(HowtoActivity.this, ApplyActivity.class)
                         .putExtra("name",itemname[position]);
                startActivity(searchIntent);
            }
        });

        ListView serviceListview = (ListView) findViewById(R.id.howto_services_listview);
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this,
                R.layout.how_to_item_layout, R.id.textView, serviceName);
        serviceListview.setAdapter(serviceAdapter);

        serviceListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent searchIntent = new Intent(HowtoActivity.this, ApplyActivity.class)
                        .putExtra("name",serviceName[position]);
                startActivity(searchIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
