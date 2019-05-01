package com.example.vinitkumaragarwal.orderguru;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class loginActivity extends AppCompatActivity {


    AsyncHttpClient asyncHttpClient;
    RequestParams requestParams;

    String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/loginuser/validateloginuser/";
    String jsonResponse;

    Intent dashboardIntent;
    Intent welcomeIntent;

    private EditText editphone;
    private EditText editpass;

    private Button btnsignin;
    //private Button btncancel;

    private TextView signUp;
    Intent signUpIntent;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ObjectAnimator anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        editphone = (EditText) findViewById(R.id.editphone);
        editpass = (EditText) findViewById(R.id.editpass);
        signUp = (TextView)findViewById(R.id.txtSignUp);

        btnsignin = (Button) findViewById(R.id.btnsignin);
        //btncancel = (Button) findViewById(R.id.btncancel);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000000);
        anim.setDuration(500000000);
        anim.setInterpolator(new DecelerateInterpolator());

        dashboardIntent = new Intent(loginActivity.this,DashboardActivity.class);
        welcomeIntent = new Intent(loginActivity.this,WelcomeActivity.class);

        if(!isInternetAvailable())
        {
            btnsignin.setEnabled(false);
            btnsignin.setText("No Internet");
        }



        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( editphone.getText().toString().trim().equals("") ) {

                    /**
                     *   You can Toast a message here that the Username is Empty
                     **/

                    editphone.setError("Phone Number is required!");
                }
                if( editpass.getText().toString().trim().equals("")){
                    editpass.setError("Password is required!");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    anim.start();
                    executeLoopjCall();
                    }
            }
        });

       /* btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(welcomeIntent);

            }
        });*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpIntent = new Intent(loginActivity.this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });


    }


    public  void executeLoopjCall() {
        asyncHttpClient = new AsyncHttpClient();
        requestParams = new RequestParams();


        asyncHttpClient.get(BASE_URL+editphone.getText().toString().trim().toLowerCase()+"/"+editpass.getText().toString().trim().toLowerCase(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                anim.end();
                progressBar.setVisibility(View.GONE);
                super.onSuccess(statusCode, headers, response);
                try{
                    //Log.i("vinit", "onParseFailure: " + response.toString());
                    //jsonResponse = String.valueOf(response.get("items"));
                    JSONArray array = response.getJSONArray("items");
                    //jsonResponse = String.valueOf(response.get .get("items"));
                   // Log.i("vinit>>>>>>>>>>>>", "onParseFailure: " + jsonResponse);

                    if (array.length()==0)
                    {
                        Toast.makeText(loginActivity.this, "Sorry User Not Found " , Toast.LENGTH_LONG).show();
                        return;
                    }
                    final GlobalVariable globalVariable = (GlobalVariable) getApplicationContext();

                    for(int i=0; i<array.length();i++)
                    {

                        JSONObject object = array.getJSONObject(i);

                        globalVariable.setIdLoginUser(object.getInt("id_login_user"));
                        globalVariable.setPincode(object.getInt("pincode"));
                        globalVariable.setMobileNumber(object.getString("mobile_number"));
                        globalVariable.setPinNumber(object.getString("pin_number"));
                        globalVariable.setFirmName(object.getString("firm_name"));
                        globalVariable.setOwnerName(object.getString("owner_name"));
                        globalVariable.setAddressline1(object.getString("address_line1"));
                        //Log.i("vinit>>>>>>>>>>>>", "ID_APP_PRODUCT");


                    }
                    //dashboardIntent.putExtra("jsonResponsephonenumber", jsonResponse);
                    //startActivity(dashboardIntent);
                    startActivity(dashboardIntent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Log.i("vinit", "onParseFailure: " + e.toString());
                    Toast.makeText(loginActivity.this, "Sorry User Not Found " , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                anim.end();
                progressBar.setVisibility(View.GONE);
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("vinit", "onFailure: " + errorResponse);
                Toast.makeText(loginActivity.this, "Sorry Someting Went Wrong1 ! " , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                anim.end();
                progressBar.setVisibility(View.GONE);
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("vinit>>Failed: ", ""+statusCode);
                Log.d("vinit>>Error : ", "" + throwable);
                Toast.makeText(loginActivity.this, "Sorry Someting Went Wrong2 ! " , Toast.LENGTH_LONG).show();
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


}
