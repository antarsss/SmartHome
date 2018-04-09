package com.n8plus.smarthome.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;
import com.n8plus.smarthome.Interface.RecyclerViewClickListener;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.ControlDoor.ControlMainDoor;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class DoorAdapter extends RecyclerView.Adapter<DoorAdapter.ViewHolder> {

    ArrayList<Device> arrayList;
    DetectionDoor context;
    private RecyclerViewClickListener clickListener;
    int position;


    public DoorAdapter(ArrayList<Device> arrayList, DetectionDoor context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void updateData(ArrayList<Device> data){
        arrayList.clear();
        arrayList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public DoorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_door, parent, false);
        return new ViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(DoorAdapter.ViewHolder holder, int position) {
        holder.txtNameDoor.setText(arrayList.get(position).getDeviceName());
        boolean state = arrayList.get(position).isState();
        if (state) {
            holder.txtStateDoor.setText("Opened");
            holder.txtStateDoor.setTextColor(Color.parseColor("#ffff4444"));
            if (arrayList.get(position).getDescription().contains("MAIN DOOR")){
                holder.imgDoor.setImageResource(R.drawable.door);
            }
            else {
                holder.imgDoor.setImageResource(R.drawable.open_window);
            }
        }
        else {
            holder.txtStateDoor.setTextColor(Color.parseColor("#00a0dc"));
            holder.txtStateDoor.setText("Closed");
            if (arrayList.get(position).getDescription().contains("MAIN DOOR")){
                holder.imgDoor.setImageResource(R.drawable.close_door);
            }
            else {
                holder.imgDoor.setImageResource(R.drawable.window);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imgDoor;
        TextView txtNameDoor, txtStateDoor;

        private RecyclerViewClickListener clickListener;

        public ViewHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);

            this.clickListener = clickListener;

            itemView.setOnClickListener(this);

            imgDoor = (ImageView) itemView.findViewById(R.id.imgDoor);
            txtNameDoor = (TextView) itemView.findViewById(R.id.txtNameDoor);
            txtStateDoor = (TextView) itemView.findViewById(R.id.txtStateDoor);
        }

        @Override
        public void onClick(View view) {
            Device mainDoor = arrayList.get(getAdapterPosition());
            if (mainDoor.getDescription().equals("MAIN DOOR")){
                Intent intent = new Intent(view.getContext(), ControlMainDoor.class);
                intent.putExtra("door", mainDoor);
                context.startActivityForResult(intent, 1);
            }
            else {
                Toast.makeText(view.getContext(), "Windows can't be control !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getItemPosition(){
        return position;
    }
}
