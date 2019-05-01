package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;



public class SignUpActivity extends AppCompatActivity {

    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/loginuser/createloginuser/";
    String jsonResponse;
    Intent welcomeIntent;
    Intent loginIntent;

    private EditText editphone;
    private EditText editpass;
    private EditText editfirm;
    private EditText edituser;
    private EditText editaddress;
    private EditText editpin;
    private Button btnsignup;
    private Button btncancel;
    private int issuccess = 0;


    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ObjectAnimator anim;


    private TextView signIn;
    Intent signInIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_signup);

        editphone = (EditText) findViewById(R.id.editphone);
        editpass = (EditText) findViewById(R.id.editpass);
        editfirm = (EditText) findViewById(R.id.editfirm);
        edituser = (EditText) findViewById(R.id.edituser);
        editaddress = (EditText) findViewById(R.id.editaddress);
        editpin = (EditText) findViewById(R.id.editpin);
        signIn = (TextView)findViewById(R.id.txtSignIn);

        btnsignup = (Button) findViewById(R.id.btnsignup);
        btncancel = (Button) findViewById(R.id.btncancel);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        //anim.setDuration(5000);
        //anim.setInterpolator(new DecelerateInterpolator());


        if(!isInternetAvailable())
        {
            btnsignup.setEnabled(false);
            btnsignup.setText("No Internet");
        }

        welcomeIntent = new Intent(SignUpActivity.this,WelcomeActivity.class);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(welcomeIntent);
            }
        });


        loginIntent = new Intent(SignUpActivity.this,loginActivity.class);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //anim.start();
                executeLoopjCall();


            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInIntent = new Intent(SignUpActivity.this,loginActivity.class);
                startActivity(signInIntent);
            }
        });



    }



    public  void executeLoopjCall() {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();

        requestParams.put("mobilenumber", editphone.getText().toString().trim().toLowerCase());
        requestParams.put("pinnumber", editpass.getText().toString().trim().toLowerCase());
        requestParams.put("firmname", editfirm.getText().toString().trim().toLowerCase());
        requestParams.put("ownername", edituser.getText().toString().trim().toLowerCase());
        requestParams.put("addressline1", editaddress.getText().toString().trim().toLowerCase());
        requestParams.put("pincode", editpin.getText().toString().trim().toLowerCase());
        requestParams.put("issuccess", issuccess);
        asyncHttpClient.post(BASE_URL,requestParams,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                //anim.end();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, "User Created " , Toast.LENGTH_LONG).show();
                startActivity(loginIntent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
               // super.onFailure(statusCode, headers, responseString, throwable);
                //Log.d("vinit>>Failed: ", ""+statusCode);
                //Log.d("vinit>>Error : ", "" + throwable);
                progressBar.setVisibility(View.GONE);
                //anim.end();
                Toast.makeText(SignUpActivity.this, "Sorry Someting Went Wrong ! " , Toast.LENGTH_LONG).show();
            }



            });
        //Log.i("vinit", "onSuccess: " + BASE_URL+queryTerm);
      /*  asyncHttpClient.get(BASE_URL, requestParams, new JsonHttpResponseHandler() {
        //asyncHttpClient.get(BASE_URL+queryTerm, new JsonHttpResponseHandler() {              without parameter
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                //jsonResponse = response.toString();
                try{
                    Log.i("vinit", "onParseFailure: " + response.toString());
                    jsonResponse = String.valueOf(response.get("mobile"));
                    dashboardIntent.putExtra("jsonResponsephonenumber", jsonResponse);
                    startActivity(dashboardIntent);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("vinit", "onParseFailure: " + e.toString());
                }
                //parseJSON(jsonResponse);
                Log.i("vinit", "onSuccess: " + jsonResponse);
                Toast.makeText(SignUpActivity.this, "Account Created " , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("vinit", "onFailure: " + errorResponse);
                Toast.makeText(SignUpActivity.this, "Sorry Someting Went Wrong ! " , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("vinit>>Failed: ", ""+statusCode);
                Log.d("vinit>>Error : ", "" + throwable);
                Toast.makeText(SignUpActivity.this, "Sorry Someting Went Wrong ! " , Toast.LENGTH_LONG).show();
            }


        });*/
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





}
