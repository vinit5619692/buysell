package com.example.vinitkumaragarwal.orderguru.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vinitkumaragarwal.orderguru.R;
import com.example.vinitkumaragarwal.orderguru.dao.productcategorydao;
import com.example.vinitkumaragarwal.orderguru.dao.productmasterdao;
import com.example.vinitkumaragarwal.orderguru.productsubcategoryActivity;
import com.example.vinitkumaragarwal.orderguru.user_info_Activity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productcategoryadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    //private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private List<productcategorydao> my_data;

    public productcategoryadapter(Context context, List<productcategorydao> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcategory_card_view,parent,false);
        //return new ViewHolder(itemView);
         if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcategory_card_view, parent, false);
            return new ViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
             //Inflating header view
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.additemheader, parent, false);
             return new HeaderViewHolder(itemView);
         } else return null;
    }

    private productcategorydao getItem(int position) {
        return my_data.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        productcategorydao md = my_data.get(position);
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textproductcategory.setText((my_data.get(position).getProductCategory()));


            Glide.with(context)
                    .load(my_data.get(position).getProductCategoryLink())
                    //.apply(new RequestOptions()
                    //        .placeholder(R.drawable.loading))
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .into(viewHolder.imageproductcategory);
        }else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.headertext.setText("Did not found your product click here to add");
            headerViewHolder.headertext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(activity, "You clicked at item " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return my_data.size()>0?my_data.size():1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textproductcategory;
        public ImageView imageproductcategory;



        public ViewHolder(View itemView)
        {
            super(itemView);
            textproductcategory = (TextView) itemView.findViewById(R.id.textproductcategory);
            imageproductcategory = (ImageView) itemView.findViewById(R.id.imageproductcategory);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, productsubcategoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idproductcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductCategory()));
                    context.startActivity(intent);

                }
            });
        }
}

    public  class HeaderViewHolder extends RecyclerView.ViewHolder{

        public TextView headertext;




        public HeaderViewHolder(View itemView)
        {
            super(itemView);
            headertext = (TextView) itemView.findViewById(R.id.headertext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, productsubcategoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idproductcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductCategory()));
                    context.startActivity(intent);

                }
            });
        }
    }



    public void filerList(ArrayList<productcategorydao> filteredList)
    {
        my_data = filteredList;
        notifyDataSetChanged();
    }
}
