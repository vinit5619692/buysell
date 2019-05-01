package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vinitkumaragarwal.orderguru.adapter.productadapter;
import com.example.vinitkumaragarwal.orderguru.adapter.productsubcategoryadapter;
import com.example.vinitkumaragarwal.orderguru.dao.productdao;
import com.example.vinitkumaragarwal.orderguru.dao.productsubcategorydao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private productadapter adapter;
    private List<productdao> data_list;
    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;
    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/productmaster/validateproductmaster/";
    String jsonResponse;
    Intent dashboardIntent;
    EditText editText;


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
        setContentView(R.layout.activity_products);
        getSupportActionBar().setTitle("Product");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();
        //load_data_from_server(0);


        gridLayoutManager = new GridLayoutManager(this,1);//to show two card in single row enter 2 instead of 1
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter =  new productadapter(this,data_list);
        recyclerView.setAdapter(adapter);

        // for lazy load next data set
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
                        Intent myIntent = new Intent(ProductsActivity.this, DashboardActivity.class);
                        startActivity(myIntent);
                        return true;
                    case R.id.client:
                        mDrawerLayout.closeDrawers();
                        Intent IntentClient = new Intent(ProductsActivity.this, client.class);
                        startActivity(IntentClient);
                        return true;
                    case R.id.supplier:
                        Log.i("vinit>>>","s1111111");
                        //mDrawerLayout.closeDrawers();
                        Intent Intentsupplier = new Intent(ProductsActivity.this, supplierActivity.class);
                        startActivity(Intentsupplier);
                        return true;
                    case R.id.product:
                        Log.i("vinit>>>","s1111111");
                        //mDrawerLayout.closeDrawers();
                        Intent Intentproductcate = new Intent(ProductsActivity.this, productcategoryActivity.class);
                        startActivity(Intentproductcate);
                        return true;
                }
                return false;
            }
        });

        executeLoopjCall(getIntent().getStringExtra("idproductcategory"),getIntent().getStringExtra("idproductsubcategory"),"0",globalVariable.getIdLoginUser());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public  void executeLoopjCall(String idproductcategory,String idproductsubcategory,String queryTerm,int vIdUserLogin) {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        final int idUserLogin = vIdUserLogin;

        requestParams.put("items", queryTerm);
        Log.i("vinit", "onSuccess: " + BASE_URL+queryTerm);
        //asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
        asyncHttpClient.get(BASE_URL+idproductcategory+"/"+idproductsubcategory+"/"+queryTerm, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                //jsonResponse = response.toString();
                try{
                    Log.i("vinit", "onParseFailure: " + response.toString());
                    //jsonResponse = String.valueOf(response.get("items"));
                    JSONArray array = response.getJSONArray("items");
                    //jsonResponse = String.valueOf(response.get .get("items"));
                    Log.i("vinit>>>>>>>>>>>>", "onParseFailure: " + jsonResponse);


                    for(int i=0; i<array.length();i++)
                    {

                        JSONObject object = array.getJSONObject(i);
                        Log.i("vinit>>>>>>>>>>>>", "ID_APP_PRODUCT");
                        //Mydata data = new Mydata(object.getInt("id_app_product"),object.getString("prodcut_name"));



                        productdao data = new productdao

                                (object.getInt("id_product_master"),object.getInt("id_product_sub_category"),object.getInt("id_product_category"),
                                        object.getString("product_name"), object.getString("product_descripton")
                                        , object.getString("product_maker")
                                        , object.getString("product_image_link")
                                ,idUserLogin);
                        data_list.add(data);



                    }
                    //dashboardIntent.putExtra("jsonResponsephonenumber", jsonResponse);
                    //startActivity(dashboardIntent);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("vinit", "onParseFailure: " + e.toString());
                }
                //parseJSON(jsonResponse);
                Log.i("vinit", "onSuccess: " + jsonResponse);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("vinit", "onFailure: " + errorResponse);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("vinit>>Failed: ", ""+statusCode);
                Log.d("vinit>>Error : ", "" + throwable);
            }


        });
    }

    private void filter(String text){
        ArrayList<productdao> filteredList = new ArrayList<>();
        for(productdao item :data_list)
        {
            /*if(item.getProduct_detail().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }*/
        }
        adapter.filerList(filteredList);
    }

}


