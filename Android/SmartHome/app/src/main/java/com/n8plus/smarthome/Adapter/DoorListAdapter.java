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

import com.n8plus.smarthome.Interface.RecyclerViewClickListener;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.Model.Module;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.DoorPage.DoorDetails.DoorDetailsView;
import com.n8plus.smarthome.View.HomePage.DoorPage.DoorList.DoorListView;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/1/2018.
 */

public class DoorListAdapter extends RecyclerView.Adapter<DoorListAdapter.ViewHolder> {

    ArrayList<Device> devices;
    DoorListView context;
    int position;
    private RecyclerViewClickListener clickListener;


    public DoorListAdapter(ArrayList<Device> devices, DoorListView context) {
        this.devices = devices;
        this.context = context;
    }

    public void updateData(ArrayList<Device> data) {
        devices.clear();
        devices.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public DoorListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.row_door, parent, false);
        return new ViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(DoorListAdapter.ViewHolder holder, int position) {
        holder.txtNameDoor.setText(devices.get(position).getDeviceName());
        ArrayList<Module> modules = devices.get(position).getModules();
        boolean state = false;
        for (Module module : modules) {
            if (module.getModuleType() == ModuleType.SENSOR) {
                state = module.isState();
            }
        }
        if (state) {
            holder.txtStateDoor.setText("Opened");
            holder.txtStateDoor.setTextColor(Color.parseColor("#ffff4444"));
            if (devices.get(position).getDescription().contains("Door")) {
                holder.imgDoor.setImageResource(R.drawable.door);
            } else {
                holder.imgDoor.setImageResource(R.drawable.open_window);
            }
        } else {
            holder.txtStateDoor.setTextColor(Color.parseColor("#00a0dc"));
            holder.txtStateDoor.setText("Closed");
            if (devices.get(position).getDescription().contains("Door")) {
                holder.imgDoor.setImageResource(R.drawable.close_door);
            } else {
                holder.imgDoor.setImageResource(R.drawable.window);
            }
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public int getItemPosition() {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            Device mainDoor = devices.get(getAdapterPosition());
            if (mainDoor.getDescription().contains("Door")) {
                Intent intent = new Intent(view.getContext(), DoorDetailsView.class);
                intent.putExtra("door", mainDoor);
                context.startActivityForResult(intent, 1);
            } else {
                Toast.makeText(view.getContext(), "Windows can't be control !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
