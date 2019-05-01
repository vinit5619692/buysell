package com.example.vinitkumaragarwal.orderguru;

import com.example.vinitkumaragarwal.orderguru.dao.addclientdata;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vinitkumaragarwal.orderguru.adapter.AddClientAdapter;
import com.example.vinitkumaragarwal.orderguru.adapter.ClientAdapter;
import com.example.vinitkumaragarwal.orderguru.dao.addclientdata;
import com.example.vinitkumaragarwal.orderguru.dao.clientdata;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class addClientActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private AddClientAdapter adapter;
    private List<addclientdata> data_list;
    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/loginuser/searchuser/";
    String jsonResponse;
    Intent dashboardIntent;
    android.widget.EditText EditText;

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
        setContentView(R.layout.activity_add_client);

        getSupportActionBar().setTitle("Add Client");
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000000);
        anim.setDuration(500000000);
        anim.setInterpolator(new DecelerateInterpolator());


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


        NVmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                      case R.id.dashboard:
                        mDrawerLayout.closeDrawers();
                        Intent Intentdash = new Intent(addClientActivity.this, DashboardActivity.class);
                        startActivity(Intentdash);
                        return true;
                    case R.id.client:
                        mDrawerLayout.closeDrawers();
                        Intent IntentClient = new Intent(addClientActivity.this, client.class);
                        startActivity(IntentClient);
                        return true;
                    case R.id.product:
                        mDrawerLayout.closeDrawers();
                        Intent IntentProduct = new Intent(addClientActivity.this, productcategoryActivity.class);
                        startActivity(IntentProduct);
                        return true;

                }
                return false;
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        //executeLoopjCall(null,null);

        gridLayoutManager = new GridLayoutManager(this,1);//to show two card in single row enter 2 instead of 1
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter =  new AddClientAdapter(this,data_list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size()-1)
                {
                    //load_data_from_server(data_list.get(data_list.size()-1).getId_product());//with new rownum
                    //executeLoopjCall("col");
                }
            }
        });

        EditText = (EditText) findViewById(R.id.EditText);
        EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                progressBar.setVisibility(View.VISIBLE);
                anim.start();
                executeLoopjCall(editable.toString(),editable.toString(),globalVariable.getIdLoginUser());
            }
        });

    }



    public  void executeLoopjCall(String firmname,String mobilenumber,int id_seller) {
        final int v_id_seller = id_seller;
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        Log.i("vinit>>121132",BASE_URL+firmname+"/"+mobilenumber);
        asyncHttpClient.get(BASE_URL+firmname+"/"+mobilenumber, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try{

                    JSONArray array = response.getJSONArray("items");
                    data_list.clear();
                    for(int i=0; i<array.length();i++)
                    {
                        Log.i("vinit>>",array.getJSONObject(i).toString());

                        JSONObject object = array.getJSONObject(i);

                         addclientdata data = new addclientdata(getMessageFromServerInt(object,"id_login_user"),
                                 v_id_seller,
                                 getMessageFromServerString(object,"mobile_number"),
                                getMessageFromServerString(object,"firm_name"),
                                getMessageFromServerString(object,"owner_name")
                                  );

                         data_list.add(data);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
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

    public String getMessageFromServerString(JSONObject response,String message_header) {
        if (((response.has(message_header) && !response.isNull(message_header))))
            try {
                return response.getString(message_header);
            } catch (JSONException e) {
                return null;
            }
        return null;
    }

    public int getMessageFromServerInt(JSONObject response,String message_header) {
        if (((response.has(message_header) && !response.isNull(message_header))))
            try {
                return response.getInt(message_header);
            } catch (JSONException e) {
                return 0;
            }
        return 0;
    }






}
