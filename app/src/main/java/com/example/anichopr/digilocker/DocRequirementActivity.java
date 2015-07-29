package com.example.anichopr.digilocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;


public class DocRequirementActivity extends AppCompatActivity {
Map<String,String> map= new HashMap<String,String>();

    String[] itemname ={
            "Birth certificate",
            "Residence Proof",
            "Income Certificate",
            "Degree Certificate",
    };
    private String loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        return sharedPreferences.getString("name", "YourName");
    }

    public void HideActionBarLogo() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("How-to-");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.blue));
        getSupportActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        map.put("Passport","http://passportindia.gov.in");
        map.put("Pan Card","https://tin.tin.nsdl.com/pan/");
        map.put("Voter Id","http://www.elections.tn.gov.in/eregistration/");
        map.put("Driving License","http://www.aptransport.org/html/driving-licencel-learners.html");
        map.put("Internet Connection","http://www.actcorp.in/#lead/0/");
        map.put("Bank Account","https://retail.onlinesbi.com/personal/newuser_reg.html");
        map.put("Telephone Connection","http://portal.bsnl.in/aspxfiles/default.aspx");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_requirement);

        HideActionBarLogo();
        ListView listview = (ListView) findViewById(R.id.checklist_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.how_to_item_layout, R.id.textView, itemname);
        listview.setAdapter(adapter);

        Button button = (Button)findViewById(R.id.apply_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(map.get( loadSavedPreferences())));
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doc_requirement, menu);
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
}
