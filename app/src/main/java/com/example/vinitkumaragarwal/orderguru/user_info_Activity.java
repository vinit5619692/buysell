package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class user_info_Activity extends AppCompatActivity {

    private TextView textphonenumber;
    private TextView textfirmname;
    private TextView textownername;
    private TextView textaddress;
    private TextView textpincode;

    private Button btnblockunblock;


    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/client/blockclient/";

    private ProgressBar progressBar;
    ObjectAnimator anim;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView NVmenu;
    private TextView Phonenumber;
    private TextView firmname;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        textphonenumber = (TextView)findViewById(R.id.textphonenumber);
        textfirmname = (TextView)findViewById(R.id.productname);
        textownername = (TextView)findViewById(R.id.productmake);
        textaddress = (TextView)findViewById(R.id.textaddress);
        textpincode = (TextView)findViewById(R.id.textpincode);

        btnblockunblock = (Button)findViewById(R.id.btnblockunblock);


        textphonenumber.setText(getIntent().getStringExtra("textphonenumber"));
        textfirmname.setText(getIntent().getStringExtra("textfirmname"));
        textownername.setText(getIntent().getStringExtra("textownername"));
        textaddress.setText(getIntent().getStringExtra("textaddress"));
        textpincode.setText(getIntent().getStringExtra("textpincode"));

         if (("Y").equals(getIntent().getStringExtra("iscallingfromsupplier"))) {
             btnblockunblock.setVisibility(View.GONE);
         }else{
                 if (("Y").equals(getIntent().getStringExtra("btnblockunblock"))) {
                     btnblockunblock.setText("UnBlock");
                 } else {
                     btnblockunblock.setText("Block");
                 }
             }



        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.slidingmenu);
        mActionBarDrawerToggle =  new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NVmenu = (NavigationView)findViewById(R.id.NVMenu);
        View header=NVmenu.getHeaderView(0);
        Phonenumber = (TextView) header.findViewById(R.id.phonenumber);
        firmname = (TextView) header.findViewById(R.id.firmane);
        username = (TextView) header.findViewById(R.id.username);
        Phonenumber.setText(globalVariable.getMobileNumber());
        firmname.setText(globalVariable.getFirmName());
        username.setText(globalVariable.getOwnerName());


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000000);
        anim.setDuration(500000000);
        anim.setInterpolator(new DecelerateInterpolator());


        NVmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){

                    case R.id.dashboard:
                        mDrawerLayout.closeDrawers();
                        Intent myIntent = new Intent(user_info_Activity.this, DashboardActivity.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.client:
                        mDrawerLayout.closeDrawers();
                        Intent IntentClient = new Intent(user_info_Activity.this, client.class);
                        startActivity(IntentClient);
                        return true;
                    case R.id.supplier:
                        Log.i("vinit>>>","s1111111");
                        mDrawerLayout.closeDrawers();
                        Intent IntentSupplier = new Intent(user_info_Activity.this, supplierActivity.class);
                        startActivity(IntentSupplier);
                        return true;
                    case R.id.product:
                    mDrawerLayout.closeDrawers();
                    Intent IntentProduct = new Intent(user_info_Activity.this, productcategoryActivity.class);
                    startActivity(IntentProduct);
                    return true;

                }
                return false;
            }
        });



        btnblockunblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progressBar.setVisibility(View.VISIBLE);
                anim.start();
                executeLoopjCall(getIntent().getStringExtra("idclient"));

            }
        });




    }








    public  void executeLoopjCall(String idClient) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();


        requestParams.put("idclient", idClient);
        asyncHttpClient.post(BASE_URL,requestParams,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Toast.makeText(user_info_Activity.this, "User Status Changed" , Toast.LENGTH_LONG).show();
                if (("Block").equals(btnblockunblock.getText()))
                {
                    btnblockunblock.setText("UnBlock");
                }else{
                    btnblockunblock.setText("Block");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(user_info_Activity.this, "Sorry Someting Went Wrong1 ! " , Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }



        });

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
