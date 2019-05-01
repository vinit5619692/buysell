package com.example.vinitkumaragarwal.orderguru.adapter;

        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;

        import com.example.vinitkumaragarwal.orderguru.R;
        import com.example.vinitkumaragarwal.orderguru.dao.clientdata;
        import com.example.vinitkumaragarwal.orderguru.user_info_Activity;


/**
 * Created by Vinit Kumar Agarwal on 28/11/2017.
 */

public class supplierAdapter extends RecyclerView.Adapter<supplierAdapter.ViewHolder>{

    private Context context;
    private List<clientdata> my_data;


    public supplierAdapter(Context context, List<clientdata> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_activity_card_view,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textfirmname.setText((my_data.get(position).getFirm_name()));
        holder.textownername.setText((my_data.get(position).getOwner_name()));
        if(("Y").equals(my_data.get(position).getIs_blocked()))
        {
            holder.imageblocked.setVisibility(View.VISIBLE);
        }
        //holder.textmobilenumber.setText((my_data.get(position).getMobile_number()));
        //holder.textaddress.setText((my_data.get(position).getAddress_line1()));
        // holder.textpin.setText((my_data.get(position).getPincode()));
        // to load image from url add a colpiler 'com.github.bumptect.glider:glider:3.7.0' and then add glider.with(context) and so one
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textfirmname;
        public TextView textownername;
        public ImageView imagecall;
        public ImageView imageblocked;
        //public TextView textmobilenumber;
        //public TextView textaddress;
        //public TextView textpin;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textfirmname = (TextView) itemView.findViewById(R.id.productname);
            textownername = (TextView) itemView.findViewById(R.id.productmake);
            imagecall = (ImageView) itemView.findViewById(R.id.imagecall);
            imageblocked = (ImageView) itemView.findViewById(R.id.imageblocked);
            //textmobilenumber = (TextView) itemView.findViewById(R.id.textmobilenumber);
            //textaddress = (TextView) itemView.findViewById(R.id.textaddress);
            //textpin = (TextView) itemView.findViewById(R.id.textpin);
            imagecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+my_data.get(getAdapterPosition()).getMobile_number()));
                    view.getContext().startActivity(intent);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, user_info_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("textphonenumber",my_data.get(getAdapterPosition()).getMobile_number());
                    intent.putExtra("textfirmname",my_data.get(getAdapterPosition()).getFirm_name());
                    intent.putExtra("textownername",my_data.get(getAdapterPosition()).getOwner_name());
                    intent.putExtra("textaddress",my_data.get(getAdapterPosition()).getAddress_line1());
                    intent.putExtra("textpincode",my_data.get(getAdapterPosition()).getPincode());
                    intent.putExtra("btnblockunblock",my_data.get(getAdapterPosition()).getIs_blocked());
                    intent.putExtra("idclient",String.valueOf(my_data.get(getAdapterPosition()).getId_client()));
                    intent.putExtra("iscallingfromsupplier","Y");
                    //intent.putExtra("idclient",my_data.get(getAdapterPosition()).getId_client());
                    context.startActivity(intent);
                }
            });

        }
    }

    public void filerList(ArrayList<clientdata> filteredList)
    {
        my_data = filteredList;
        notifyDataSetChanged();
    }


}
