package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;

import cz.msebera.android.httpclient.Header;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView NVmenu;
    private TextView Phonenumber;
    private TextView firmname;
    private TextView username;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    private String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/totalcount/totalcount/";

    private TextView progress_order;
    private TextView progress_product;
    private TextView progress_supplier;
    private TextView progress_client;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ObjectAnimator anim;

    private Intent intent_client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000000);
        anim.setDuration(500000000);
        anim.setInterpolator(new DecelerateInterpolator());

        progressBar.setVisibility(View.VISIBLE);
        anim.start();

        progress_order = (TextView) findViewById(R.id.info_text31);
        progress_product = (TextView) findViewById(R.id.info_text41);
        progress_supplier = (TextView) findViewById(R.id.info_text21);
        progress_client = (TextView) findViewById(R.id.info_text12);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.slidingmenu);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NVmenu = (NavigationView) findViewById(R.id.NVMenu);
        View header = NVmenu.getHeaderView(0);
        Phonenumber = (TextView) header.findViewById(R.id.phonenumber);
        firmname = (TextView) header.findViewById(R.id.firmane);
        username = (TextView) header.findViewById(R.id.username);
        Phonenumber.setText(globalVariable.getMobileNumber());
        firmname.setText(globalVariable.getFirmName());
        username.setText(globalVariable.getOwnerName());


        NVmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                Log.i("vinit>>>",item.toString());
                switch (item.getItemId()){
                    /*case R.id.dashboard:
                        mDrawerLayout.closeDrawers();
                        Log.i("22222?????", "?>>>>>>>>>>");
                        Intent myIntent = new Intent(DashboardActivity.this, dashboard2.class);
                        startActivity(myIntent);
                        return true;*/

                    case R.id.client:
                        Log.i("vinit>>>","s1111111");
                        //mDrawerLayout.closeDrawers();
                        intent_client = new Intent(DashboardActivity.this, client.class);
                        startActivity(intent_client);
                        return true;
                    case R.id.supplier:
                        Log.i("vinit>>>","s1111111");
                        //mDrawerLayout.closeDrawers();
                        intent_client = new Intent(DashboardActivity.this, supplierActivity.class);
                        startActivity(intent_client);
                        return true;
                    case R.id.product:
                        mDrawerLayout.closeDrawers();
                        Intent IntentProduct = new Intent(DashboardActivity.this, productcategoryActivity.class);
                        startActivity(IntentProduct);
                        return true;

                }
                return false;
            }
        });


        if (!isInternetAvailable()) {
            progress_order.setText("0");
            progress_product.setText("0");
            progress_supplier.setText("0");
            progress_client.setText("0");
        }

        executeLoopjCall();

        /* to populate from global variables
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();
        textView2.setText(globalVariable.getMobileNumber());
        textView3.setText(globalVariable.getFirmName());*/


//        progress_client.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent_client = new Intent(DashboardActivity.this, client.class);
//                startActivity(intent_client);
//            }
//        });



    }




    public  void executeLoopjCall() {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();


        asyncHttpClient.get(BASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try{
                    JSONArray array = response.getJSONArray("items");

                    if (array.length()==0)
                    {
                        progress_order.setText("0");
                        progress_product.setText("0");
                        progress_supplier.setText("0");
                        progress_client.setText("0");
                        return;
                    }


                    for(int i=0; i<array.length();i++)
                    {

                        JSONObject object = array.getJSONObject(i);

                        progress_order.setText(object.getString("totalclient"));
                        progress_product.setText(object.getString("totalsupplier"));
                        progress_supplier.setText(object.getString("totalorder"));
                        progress_client.setText(object.getString("totalproduct"));


                        progressBar.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progress_order.setText("0");
                    progress_product.setText("0");
                    progress_supplier.setText("0");
                    progress_client.setText("0");
                    progressBar.setVisibility(View.GONE);
                    return;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progress_order.setText("0");
                progress_product.setText("0");
                progress_supplier.setText("0");
                progress_client.setText("0");
                progressBar.setVisibility(View.GONE);
                return;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress_order.setText("0");
                progress_product.setText("0");
                progress_supplier.setText("0");
                progress_client.setText("0");
                progressBar.setVisibility(View.GONE);
                return;
            }


        });
    }







    public boolean isInternetAvailable() {
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            // Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            //Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }




}
