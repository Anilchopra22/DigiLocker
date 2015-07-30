package com.example.anichopr.digilocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class DocRequirementActivity extends AppCompatActivity {
    static Map<String,String> map= new HashMap<String,String>();
    static {
        map.put("Passport","http://passportindia.gov.in");
        map.put("Pan Card","https://tin.tin.nsdl.com/pan/");
        map.put("Voter Id","http://www.elections.tn.gov.in/eregistration/");
        map.put("Driving License","http://www.aptransport.org/html/driving-licencel-learners.html");
        map.put("Internet Connection","http://www.actcorp.in/#lead/0/");
        map.put("Bank Account", "https://retail.onlinesbi.com/personal/newuser_reg.html");
        map.put("Telephone Connection", "http://portal.bsnl.in/aspxfiles/default.aspx");
    }

    String[] passportitems ={
            "Birth Certificate",
            "Telephone Bill",
            "Income Certificate",
            "Degree Certificate",
    };

    String[] netConnectionItems ={
            "Birth Certificate",
            "Telephone Bill",
    };

    String[] defaultItems = {
            "Birth Certificate",
            "Income Certificate",
            "Degree Certificate",
    };
    String[] itemname = defaultItems;

    private String loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        return sharedPreferences.getString("name", "YourName");
    }

    public void HideActionBarLogo() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.blue));
        getSupportActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_requirement);

        Intent in=getIntent();
        String name=in.getStringExtra("name");
        getSupportActionBar().setTitle("Apply for " + name);
        if (name.equals("Passport")) {
            itemname = passportitems;
        } else if (name.equals("Internet Connection")) {
            itemname = netConnectionItems;
        }


        HideActionBarLogo();
        ListView listview = (ListView) findViewById(R.id.checklist_listview);
        RecentItemListAdapter adapter = new RecentItemListAdapter(this, R.layout.how_to_item_layout, itemname);
        listview.setAdapter(adapter);

        int availableItems = 0;
        int percentage = 0;
        for (String item : itemname) {
            if (DocLibrary.IsDocAvailable(item)) {
                availableItems++;
            }
        }

        if (itemname != null && itemname.length != 0) {
            percentage = (availableItems*100) / itemname.length;
        }
        ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setProgress(percentage);

        Button button = (Button)findViewById(R.id.apply_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(map.get(loadSavedPreferences())));
                startActivity(i);

            }
        });

        TextView requirementTitle = (TextView) findViewById(R.id.document_progress_title);
        if (percentage < 100) {
            button.setEnabled(false);
            button.setVisibility(View.GONE);
            requirementTitle.setText(getResources().getString(R.string.two_short) + " " + name + ".");
        } else {
            requirementTitle.setText(getResources().getString(R.string.can_apply));
        }
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

    public static class RecentItemListAdapter extends ArrayAdapter<String> {
        private Context mContext;
        private String[] items;

        public RecentItemListAdapter(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
            mContext = ctx;
            items = objects;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);

            View mySpinner = inflater.inflate(R.layout.how_to_item_layout, parent, false);
            TextView textView = (TextView) mySpinner.findViewById(R.id.textView);
            textView.setText(items[position]);

            ImageView imageView = (ImageView) mySpinner.findViewById(R.id.imageView);
            if (DocLibrary.IsDocAvailable(items[position])) {
                imageView.setImageResource(R.mipmap.available);
            } else {
                imageView.setImageResource(R.mipmap.notavailable);

            }
            return mySpinner;
        }

    }

}
