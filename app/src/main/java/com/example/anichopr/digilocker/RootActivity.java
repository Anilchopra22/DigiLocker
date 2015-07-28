package com.example.anichopr.digilocker;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class RootActivity extends AppCompatActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    TabsPagerAdapter mAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private String[] tabs = { "Essentials", "Others"};

    private  void  initActionBarStyle(){
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.blue));
        getSupportActionBar().setTitle("Documents");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        initActionBarStyle();
        // Initilization
        mViewPager = (ViewPager) findViewById(R.id.pager);
        ActionBar actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                getSupportActionBar().setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notify) {
            Intent notificationIntent = new Intent(RootActivity.this, NotificationActivity.class);
            startActivity(notificationIntent);
            return true;
        } else if (id == R.id.apply) {
            Intent searchIntent = new Intent(RootActivity.this, HowtoActivity.class);
            startActivity(searchIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        int tag=tab.getPosition();
        mViewPager.setCurrentItem(tag);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class TabsPagerAdapter extends FragmentPagerAdapter {
        private Context mContext = null;
        public TabsPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0)
                return EssentialDocFragment.newInstance(position + 1, mContext);
            else
                return OthersDocFragment.newInstance(position + 1, mContext);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class EssentialDocFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static Context mContext = null;
        private static final int CAMERA_REQUEST = 1888;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static EssentialDocFragment newInstance(int sectionNumber, Context context) {
            EssentialDocFragment fragment = new EssentialDocFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            mContext = context;
            return fragment;
        }

        public EssentialDocFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.essential_doc_layout, container, false);
            GridView gridview = (GridView) relativeLayout.findViewById(R.id.essential_doc_gridview);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.add_document_btn);
            DigiDoc[] digidocs = DocLibrary.essentialDocs;

            gridview.setAdapter(new DocImageAdapter(mContext, digidocs));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    DigiDoc[] digidocs = DocLibrary.essentialDocs;

                    if (digidocs[position].documentURL == null ||
                            digidocs[position].documentURL.isEmpty()) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } else {
                        Intent browserIntent = new Intent("android.intent.action.VIEW",
                                Uri.parse("http://docs.google.com/gview?embedded=true&url=" + "https://digilocker.gov.in/CandidateLocker/" + digidocs[position].documentURL));
                        startActivity(browserIntent);
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            });
            return relativeLayout;
        }
    }


    public static class OthersDocFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final int CAMERA_REQUEST = 1888;
        private static Context mContext = null;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static OthersDocFragment newInstance(int sectionNumber, Context context) {
            OthersDocFragment fragment = new OthersDocFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            mContext = context;
            return fragment;
        }

        public OthersDocFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.other_doc_layout, container, false);
            GridView gridview = (GridView) relativeLayout.findViewById(R.id.self_uploaded_doc_gridview);
            DigiDoc[] digidocs = DocLibrary.otherDocs;
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.add_document_btn);

            gridview.setAdapter(new DocImageAdapter(mContext, digidocs));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    DigiDoc[] digidocs = DocLibrary.otherDocs;

                    if (digidocs[position].documentURL == null ||
                            digidocs[position].documentURL.isEmpty()) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } else {
                        Intent browserIntent = new Intent("android.intent.action.VIEW",
                                Uri.parse("http://docs.google.com/gview?embedded=true&url=" + "https://digilocker.gov.in/CandidateLocker/" + digidocs[position].documentURL));
                        startActivity(browserIntent);
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, CAMERA_REQUEST);
                }
            });

            return relativeLayout;
        }
    }

}
