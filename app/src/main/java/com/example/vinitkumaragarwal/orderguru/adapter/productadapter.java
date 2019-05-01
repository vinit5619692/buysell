package com.example.vinitkumaragarwal.orderguru.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vinitkumaragarwal.orderguru.ProductsActivity;
import com.example.vinitkumaragarwal.orderguru.R;
import com.example.vinitkumaragarwal.orderguru.dao.productdao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productadapter extends RecyclerView.Adapter<productadapter.ViewHolder>{

    private Context context;
    private List<productdao> my_data;

    public productadapter(Context context, List<productdao> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.textproduct.setText((my_data.get(position).getProductName()));
        holder.textmake.setText((my_data.get(position).getProductMaker()));

        Glide.with(context)
                .load(my_data.get(position).getProductImageLink())
                //.apply(new RequestOptions()
                //        .placeholder(R.drawable.loading))
                .thumbnail(Glide.with(context).load(R.drawable.loading))
                 .into(holder.imageproduct);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textproduct;
        public TextView textmake;
        public ImageView imageproduct;
        public ImageView imageadd;

        public String productprice;
        public String productsellprice;

        AsyncHttpClient asyncHttpClient;
        RequestParams requestParams;

        String BASE_URL = "https://apex.oracle.com/pls/apex/buysell/clientproduct/createclientproduct/";


        public ViewHolder(View itemView)
        {
            super(itemView);
            textproduct = (TextView) itemView.findViewById(R.id.textproduct);
            textmake = (TextView) itemView.findViewById(R.id.textmake);
            imageproduct = (ImageView) itemView.findViewById(R.id.imageproduct);
            imageadd = (ImageView) itemView.findViewById(R.id.imageadd);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                  //  executeLoopjCall();//intent.setData(Uri.parse("tel:"+my_data.get(getAdapterPosition()).getMobile_number()));

                }
            });

            imageadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // DialogFragment newFragment = pricedialogFragment.newInstance();
                   // newFragment.show(getFragmentManager(), "dialog");
                   /* Intent intent = new Intent(context, ProductsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idproductcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductCategory()));
                    intent.putExtra("idproductsubcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductSubCategory()));
                    context.startActivity(intent);*/
                    //  executeLoopjCall();//intent.setData(Uri.parse("tel:"+my_data.get(getAdapterPosition()).getMobile_number()));


                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.fragment_pricedialog, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText editTextproductprice = (EditText) promptsView
                            .findViewById(R.id.editTextproductprice);

                    final EditText editTextproductsellprice = (EditText) promptsView
                            .findViewById(R.id.editTextproductsellprice);
                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            // get user input and set it to result
                                            // edit text
                                            productprice = editTextproductprice.getText().toString();
                                            productsellprice = editTextproductsellprice.getText().toString();
                                            executeLoopjCall(String.valueOf(my_data.get(getAdapterPosition()).getIdLoginUser()),String.valueOf(my_data.get(getAdapterPosition()).getIdProductCategory()),productprice,productsellprice);
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                }
            });
        }



        public  void executeLoopjCall(String selleridloginuser,String idproductmaster,String productprice,String productsellingprice) {
            asyncHttpClient = new AsyncHttpClient();
            requestParams = new RequestParams();


            requestParams.put("idproductmaster",idproductmaster);
            requestParams.put("productprice", productprice);
            requestParams.put("productsellingprice",productsellingprice);
            requestParams.put("selleridloginuser",selleridloginuser);
            //requestParams.put("issuccess", issuccess);
            asyncHttpClient.post(BASE_URL,requestParams,new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    Toast toast = Toast.makeText(context, "Product Added To Your Account", Toast.LENGTH_LONG);
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

    public void filerList(ArrayList<productdao> filteredList)
    {
        my_data = filteredList;
        notifyDataSetChanged();
    }
}
