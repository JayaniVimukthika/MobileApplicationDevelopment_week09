package com.jayani.lostandfoundapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> {

    private ArrayList<itemModel> itemsList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MMM yyyy", Locale.US);
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.US);
    Date date = null;
    String outputDateString = null;
    private  OnItemClickListner mListener;

    public interface OnItemClickListner{

        void onItemClick(int position);
    }

    public void setOnClickListner(OnItemClickListner listner){
        mListener=listner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_day;

        TextView item_month;
        TextView item_date;

        TextView item_Name;
        TextView item_discription;


        ViewHolder(View view, OnItemClickListner mListener) {
            super(view);
            item_day = view.findViewById(R.id.day);
            item_date = view.findViewById(R.id.date);
            item_month = view.findViewById(R.id.month);
            item_Name = view.findViewById(R.id.itemName);
            item_discription = view.findViewById(R.id.description);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mListener != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }

                }
            });


        }
    }

    public itemAdapter(ArrayList<itemModel> itemsList) {

//        this.context = context;
        this.itemsList = itemsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemModel item = itemsList.get(position);
        int postType = item.getPostType();
        String PostName;
        if (postType==1){
            PostName = "Found";
        }else{
            PostName = "Lost";
        }
        holder.item_Name.setText(PostName +" : "+ item.getName());
        holder.item_discription.setText(item.getDescription());

        try {
            date = inputDateFormat.parse(item.getDate());
            outputDateString = dateFormat.format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.item_day.setText(day);
            holder.item_date.setText(dd);
            holder.item_month.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAtPosition(int position) {
        itemsList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemsList.size());
    }

    public static void notifyChange(){
        notifyChange();
    }
    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
