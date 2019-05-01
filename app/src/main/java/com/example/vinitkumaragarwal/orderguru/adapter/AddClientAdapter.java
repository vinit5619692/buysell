package com.example.vinitkumaragarwal.orderguru.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vinitkumaragarwal.orderguru.R;
import com.example.vinitkumaragarwal.orderguru.dao.addclientdata;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class AddClientAdapter extends RecyclerView.Adapter<AddClientAdapter.ViewHolder>{

    private Context context;
    private List<addclientdata> my_data;


    public AddClientAdapter(Context context, List<addclientdata> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_client_card_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.textfirmname.setText((my_data.get(position).getFirm_name()));
        holder.textownername.setText((my_data.get(position).getOwner_name()));
        holder.textphonenumber.setText((my_data.get(position).getMobile_number()));

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textfirmname;
        public TextView textownername;
        public TextView textphonenumber;
        public ImageView imageadd;

        AsyncHttpClient asyncHttpClient;
        RequestParams requestParams;

        //String issuccess="0";

        String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/client/createclient/";


        public ViewHolder(View itemView)
        {
            super(itemView);
            textfirmname = (TextView) itemView.findViewById(R.id.productname);
            textownername = (TextView) itemView.findViewById(R.id.productmake);
            textphonenumber = (TextView) itemView.findViewById(R.id.textphonenumber);
            imageadd = (ImageView) itemView.findViewById(R.id.imageadd);



            imageadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                      executeLoopjCall();//intent.setData(Uri.parse("tel:"+my_data.get(getAdapterPosition()).getMobile_number()));

                }
            });

        }

        public  void executeLoopjCall() {
            asyncHttpClient = new AsyncHttpClient();
            requestParams = new RequestParams();


            requestParams.put("buyeridloginuser", my_data.get(getAdapterPosition()).getId_login_user());
            requestParams.put("isactive", "Y");
            requestParams.put("isblocked", "N");
            requestParams.put("selleridloginuser",my_data.get(getAdapterPosition()).getId_seller());
            //requestParams.put("issuccess", issuccess);
            asyncHttpClient.post(BASE_URL,requestParams,new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    Toast toast = Toast.makeText(context, "User Added", Toast.LENGTH_LONG);
                    toast.show();
                    try{
                        String str = new String(response, "UTF-8");
                        Log.i("vinit>>>>>>>>",str);
                    }catch(Exception e)
                    {
                        Log.i("vinit>>>>>>>>","");
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Toast toast = Toast.makeText(context, "Someting Went Wrong", Toast.LENGTH_LONG);
                    toast.show();
                }



            });

        }


    }

    public void filerList(ArrayList<addclientdata> filteredList)
    {
        my_data = filteredList;
        notifyDataSetChanged();
    }




}
