package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.vinitkumaragarwal.orderguru.dao.clientdata;
import com.example.vinitkumaragarwal.orderguru.adapter.ClientAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class client extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private ClientAdapter adapter;
    private List<clientdata> data_list;

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/client/validateclient/";
    String jsonResponse;
    Intent dashboardIntent;
    EditText EditText;


    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ObjectAnimator anim;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView NVmenu;
    private TextView Phonenumber;
    private TextView firmname;
    private TextView username;

    private ImageView imageaddclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        getSupportActionBar().setTitle("Client");
        final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000000);
        anim.setDuration(500000000);
        anim.setInterpolator(new DecelerateInterpolator());

        progressBar.setVisibility(View.VISIBLE);
        anim.start();


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

                //Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()){
                    /*case R.id.dashboard:
                        mDrawerLayout.closeDrawers();
                        Log.i("22222?????", "?>>>>>>>>>>");
                        Intent myIntent = new Intent(DashboardActivity.this, dashboard2.class);
                        startActivity(myIntent);
                        return true;*/
                    case R.id.dashboard:
                        mDrawerLayout.closeDrawers();
                        Intent myIntent = new Intent(client.this, DashboardActivity.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.supplier:
                        Log.i("vinit>>>","s1111111");
                        mDrawerLayout.closeDrawers();
                        Intent IntentSupplier = new Intent(client.this, supplierActivity.class);
                        startActivity(IntentSupplier);
                        return true;
                    case R.id.product:
                        mDrawerLayout.closeDrawers();
                        Intent IntentProduct = new Intent(client.this, productcategoryActivity.class);
                        startActivity(IntentProduct);
                        return true;

                }
                return false;
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        data_list = new ArrayList<>();
        executeLoopjCall(globalVariable.getMobileNumber().toString());


       /* recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        //above for full matchparent and down to more the one in a row
        gridLayoutManager = new GridLayoutManager(this,1);//to show two card in single row enter 2 instead of 1
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter =  new ClientAdapter(this,data_list);
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
                filter(editable.toString());
            }
        });

        imageaddclient = (ImageView)findViewById(R.id.imageaddclient);
        imageaddclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intentaddclient = new Intent(client.this, addClientActivity.class);
                startActivity(Intentaddclient);
            }
        });

    }




    public  void executeLoopjCall(String queryTerm) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();


        asyncHttpClient.get(BASE_URL+queryTerm, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try{
                    data_list.clear();
                    JSONArray array = response.getJSONArray("items");

                    for(int i=0; i<array.length();i++)
                    {
                        Log.i("vinit>>",array.getJSONObject(i).toString());

                        JSONObject object = array.getJSONObject(i);

                        //clientdata data = new clientdata(object.getInt("id_client"), object.getString("mobile_number"), object.getString("firm_name"), object.getString("owner_name"), object.getString("address_line1"), object.getString("pincode"), object.getString("is_blocked"));
                        clientdata data = new clientdata(getMessageFromServerInt(object,"id_client"),
                                getMessageFromServerString(object,"mobile_number"),
                                getMessageFromServerString(object,"firm_name"),
                                getMessageFromServerString(object,"owner_name"),
                                getMessageFromServerString(object,"address_line1"),
                                getMessageFromServerString(object,"pincode"),
                                getMessageFromServerString(object,"is_blocked"),
                                getMessageFromServerInt(object,"buyer_id_login_user"));
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

    private void filter(String text){
        ArrayList<clientdata> filteredList = new ArrayList<>();
        for(clientdata item :data_list)
        {
            if(item.getFirm_name().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filerList(filteredList);
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

    /*@Override
    public void itemClicked(View view, int position) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:8800682039"));
        startActivity(intent);
    }
*/

    }
