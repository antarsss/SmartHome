package com.n8plus.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.n8plus.smarthome.Interface.RecyclerViewClickListener;
import com.n8plus.smarthome.Model.Function;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraList.CameraListView;
import com.n8plus.smarthome.View.HomePage.LightPage.LightListView;
import com.n8plus.smarthome.View.HomePage.DoorPage.DoorList.DoorListView;

import java.util.ArrayList;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    ArrayList<Function> arrayList;
    Context context;
    private RecyclerViewClickListener clickListener;

    public HomePageAdapter(ArrayList<Function> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_function, parent, false);
        return new HomePageAdapter.ViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Function function = arrayList.get(position);
        holder.imgScreenFunction.setImageResource(function.getImageScreen());
        holder.txtFunction.setText(function.getName());
        holder.imgFunction.setImageResource(function.getImageType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imgScreenFunction, imgFunction;
        TextView txtFunction;
        private RecyclerViewClickListener clickListener;

        public ViewHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);

            imgScreenFunction = (ImageView) itemView.findViewById(R.id.imgScreenFunction);
            imgFunction = (ImageView) itemView.findViewById(R.id.imgFunction);
            txtFunction = (TextView) itemView.findViewById(R.id.txtFunction);
        }

        @Override
        public void onClick(View v) {
            Function function = arrayList.get(getAdapterPosition());
            switch (function.getImageType()){
                case R.drawable.idea:
                    context.startActivity(new Intent(context, LightListView.class));
                    break;
                case R.drawable.door:
                    context.startActivity(new Intent(context, DoorListView.class));
                    break;
                case R.drawable.cctv:
                    context.startActivity(new Intent(context, CameraListView.class));
                    break;
            }
        }
    }
}