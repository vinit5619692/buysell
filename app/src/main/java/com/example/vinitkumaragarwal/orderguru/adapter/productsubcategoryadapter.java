package com.example.vinitkumaragarwal.orderguru.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinitkumaragarwal.orderguru.ProductsActivity;
import com.example.vinitkumaragarwal.orderguru.R;
import com.example.vinitkumaragarwal.orderguru.dao.productcategorydao;
import com.example.vinitkumaragarwal.orderguru.dao.productsubcategorydao;
import com.example.vinitkumaragarwal.orderguru.productsubcategoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class productsubcategoryadapter extends RecyclerView.Adapter<productsubcategoryadapter.ViewHolder>{

    private Context context;
    private List<productsubcategorydao> my_data;

    public productsubcategoryadapter(Context context, List<productsubcategorydao> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsubcategory_card_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.textproductsubcategory.setText((my_data.get(position).getProductSubCategory()));

        Glide.with(context)
                .load(my_data.get(position).getProductSubCategoryLink())
                //.apply(new RequestOptions()
                //        .placeholder(R.drawable.loading))
                .thumbnail(Glide.with(context).load(R.drawable.loading))
                 .into(holder.imageproductsubcategory);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textproductsubcategory;
        public ImageView imageproductsubcategory;



        public ViewHolder(View itemView)
        {
            super(itemView);
            textproductsubcategory = (TextView) itemView.findViewById(R.id.textproductsubcategory);
            imageproductsubcategory = (ImageView) itemView.findViewById(R.id.imageproductsubcategory);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, ProductsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idproductcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductCategory()));
                    intent.putExtra("idproductsubcategory",String.valueOf(my_data.get(getAdapterPosition()).getIdProductSubCategory()));
                    context.startActivity(intent);
                  //  executeLoopjCall();//intent.setData(Uri.parse("tel:"+my_data.get(getAdapterPosition()).getMobile_number()));

                }
            });
        }
}

    public void filerList(ArrayList<productsubcategorydao> filteredList)
    {
        my_data = filteredList;
        notifyDataSetChanged();
    }
}
